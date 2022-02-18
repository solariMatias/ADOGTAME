package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Breed;
import com.example.demo.entity.Dog;
import com.example.demo.service.BreedService;
import com.example.demo.service.DogService;

@Controller
@RequestMapping("/views/dogs")
public class DogController {
	private final static String DELETED_SUCCESSFULLY = "| OBJECT WITH ID %d DELETED SUCCESSFULLY| ";
	private final static String INVALID_ID_DELETE = "| ERROR WHILE TRYING TO DELETE - INVALID ID %d |";
	private final static String INVALID_ID_EDIT = "| ERROR WHILE TRYING TO EDIT - INVALID ID %d |";
	private final static String RELATIVE_PATH = "C://Windows//Temp//uploads";
	private final static String MISSING_INPUT_DATA = "Error a la hora de registrar";
	private final static String IMG_NO_FILE_ON_INPUT = "Debe ingresar una foto";
	private final static String IMG_ERROR_EXTERNSION = "Solo formato .jpg .png .jpeg";
	private final static String IMG_MAX_SIZE = "Tamaño de imagen excedido. Máximo de 10MB";
	private final static int MAX_FILE_SIZE = 10485760;
	private String doggoPhoto;
	private boolean comesFromCreatePage;
	private String[] errors;

	public DogController() {
		this.errors = new String[] { IMG_NO_FILE_ON_INPUT, IMG_ERROR_EXTERNSION, IMG_MAX_SIZE };
	}

	@Autowired
	private DogService dogService;
	@Autowired
	private BreedService breedService;

	@GetMapping("/")
	public String listDogs(Model model) {
		List<Dog> listDogs = dogService.listAll();
		model.addAttribute("titulo", "LISTADO DE PERROS");
		model.addAttribute("perros", listDogs);
		return "/views/dogs/list";
	}

	@GetMapping("/create/")
	public String createDog(Model model) {
		comesFromCreatePage = true;
		List<Breed> listBreeds = this.breedService.listBreed();
		model.addAttribute("titulo", "Alta de perro");
		model.addAttribute("perro", new Dog());
		model.addAttribute("breed", listBreeds);
		return "/views/dogs/createForm";
	}

	@GetMapping("/edit/{id}")
	public String editDog(@PathVariable("id") Long idDog, Model model) {
		if (verifyID(idDog)) {
			comesFromCreatePage = false;
			Dog perro = dogService.searchDogById((idDog));
			List<Breed> listBreeds = this.breedService.listBreed();
			model.addAttribute("titulo", "INGRESE DATOS:");
			model.addAttribute("perro", perro);
			model.addAttribute("breed", listBreeds);
			doggoPhoto = perro.getPhoto();
			model.addAttribute("image", doggoPhoto);
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

	@PostMapping("/save/")
	public String save(@RequestParam(name = "file", required = false) MultipartFile photo,
			@Valid @ModelAttribute("perro") Dog dog, BindingResult result, Model model) {

		List<Breed> listBreeds = this.breedService.listBreed();
		if (comesFromCreatePage) {
			short errorNumber = this.verifyImage(photo);
			if (errorNumber == -1 && !result.hasErrors()) {
				savePhoto(photo);
				dog.setPhoto(photo.getOriginalFilename());
				dogService.save(dog);
			} else {
				model.addAttribute("titulo", "INGRESE DATOS: " + MISSING_INPUT_DATA);
				model.addAttribute("perro", dog);
				model.addAttribute("breed", listBreeds);
				if (errorNumber != -1)
					model.addAttribute("errorImage", this.errors[errorNumber]);
				return "/views/dogs/createForm";
			}
		} else {
			if (photo.isEmpty()) {
				if (!result.hasErrors()) {
					dog.setPhoto(doggoPhoto);
					dogService.save(dog);
				} else {
					model.addAttribute("titulo", "INGRESE DATOS: " + MISSING_INPUT_DATA);
					model.addAttribute("perro", dog);
					model.addAttribute("breed", listBreeds);
					model.addAttribute("image", doggoPhoto);
					return "/views/dogs/createForm";
				}

			} else {
				savePhoto(photo);
				dog.setPhoto(photo.getOriginalFilename());
				dogService.save(dog);
			}
		}
		return "redirect:/views/dogs/";
	}

	private boolean verifyImageExtension(MultipartFile file) {
		return !(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")
				|| file.getContentType().equals("image/jpg"));
	}

	private short verifyImage(MultipartFile file) {
		short positionError = -1;
		if (file.isEmpty()) {
			positionError = 0;
		} else if (verifyImageExtension(file)) {
			positionError = 1;
		} else if (file.getSize() > MAX_FILE_SIZE) {
			positionError = 2;
		}
		return positionError;
	}

	private boolean verifyID(Long ID) {
		boolean verified = ID > 0 && dogService.exist(ID);
		return verified;
	}

	private void savePhoto(MultipartFile photoToAdd) {
		try {
			byte[] photoBytes = photoToAdd.getBytes();
			Path absolutePath = Paths.get(RELATIVE_PATH + "//" + photoToAdd.getOriginalFilename());
			Files.write(absolutePath, photoBytes);

		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}
}