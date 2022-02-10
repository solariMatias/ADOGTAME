package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Breed;
import com.example.demo.entity.Dog;
import com.example.demo.service.BreedService;
import com.example.demo.service.DogService;

@Controller
@RequestMapping("/views/dogs")
public class DogController {
	private final static String CREATED_SUCCESSFULLY = "| OBJECT CREATED SUCCESSFULLY| ";
	private final static String EDITED_SUCCESSFULLY = "| OBJECT WITH ID %d EDITED SUCCESSFULLY |";
	private final static String DELETED_SUCCESSFULLY = "| OBJECT WITH ID %d DELETED SUCCESSFULLY| ";
	private final static String OBJECT_ERROR_CREATION = "| ERROR WHILE TRYING TO CREATE A NEW OBJECT ||";
	private final static String INVALID_ID_DELETE = "| ERROR WHILE TRYING TO DELETE - INVALID ID %d |";
	private final static String INVALID_ID_EDIT = "| ERROR WHILE TRYING TO EDIT - INVALID ID %d |";
	private final static String RELATIVE_PATH = "C://Windows//Temp//uploads";
	@Autowired
	private DogService dogService;
	@Autowired
	private BreedService breedService;

	@GetMapping("/")
	public String listDogs(Model model) {
		List<Dog> listDogs = dogService.listAll();
		model.addAttribute("titulo", "Lista de perros");
		model.addAttribute("perros", listDogs);
		return "/views/dogs/list";
	}

	@GetMapping("/create")
	public String createDog(Model model) {

		List<Breed> listBreeds = this.breedService.listBreed();
		model.addAttribute("titulo", "Alta de perro");
		model.addAttribute("perro", new Dog());
		model.addAttribute("breed", listBreeds);
		System.out.println(CREATED_SUCCESSFULLY);
		return "/views/dogs/createForm";
	}

	@GetMapping("/edit/{id}")
	public String editDog(@PathVariable("id") Long idDog, Model model) {
		if (verifyID(idDog)) {
			Dog perro = dogService.searchDogById((idDog));
			List<Breed> listBreeds = this.breedService.listBreed();
			model.addAttribute("titulo", "Editar perro");
			model.addAttribute("perro", perro);
			model.addAttribute("breed", listBreeds);
			System.out.printf(EDITED_SUCCESSFULLY, idDog);
			return "/views/dogs/createForm";
		} else {
			System.out.printf(INVALID_ID_EDIT, idDog);
			return "redirect:/views/dogs/";
		}

	}

	@GetMapping("/delete/{id}")
	public String deleteDog(@PathVariable("id") Long idDog) {
		if (verifyID(idDog)) {
			dogService.delete(idDog);
			System.out.printf(DELETED_SUCCESSFULLY, idDog);
			return "redirect:/views/dogs/";
		} else {
			System.out.printf(INVALID_ID_DELETE, idDog);
			return "redirect:/views/dogs/";
		}

	}

	private boolean verifyID(Long ID) {
		boolean verified = ID > 0 && dogService.exist(ID);
		return verified;
	}

	@PostMapping("/save")
	public String save(
			@Valid @ModelAttribute("perro") 
			@RequestParam(name = "file", required = false) 
			MultipartFile photo, Dog dog,
			BindingResult result, Model model) {
		List<Breed> listBreeds = this.breedService.listBreed();
		if (result.hasErrors() || photo == null) {

			model.addAttribute("titulo", "Alta de perro");
			model.addAttribute("perro", dog);
			model.addAttribute("breed", listBreeds);
			System.out.println(OBJECT_ERROR_CREATION);
			return "/views/dogs/createForm";
		}
		System.out.println("saving image");
		savePhotoOnDogObj(photo, dog);
		System.out.println("saving dog");
		dogService.save(dog);
		return "redirect:/views/dogs/";
	}

	private void savePhotoOnDogObj(MultipartFile photoToAdd, Dog dog) {
		try {
			byte[] photoBytes = photoToAdd.getBytes();
			Path absolutePath = Paths.get(RELATIVE_PATH + "//" + photoToAdd.getOriginalFilename());
			Files.write(absolutePath, photoBytes);
			dog.setPhoto(photoToAdd.getOriginalFilename());
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}
}