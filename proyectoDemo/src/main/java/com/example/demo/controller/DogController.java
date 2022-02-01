package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Breed;
import com.example.demo.entity.Dog;
import com.example.demo.service.BreedService;
import com.example.demo.service.DogService;

@Controller
@RequestMapping("/views/dogs")
public class DogController {

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
		Dog perro = new Dog();
		List<Breed> listBreeds= this.breedService.listBreed();
		model.addAttribute("titulo", "Alta de perro");
		model.addAttribute("perro", perro);
		model.addAttribute("breed", listBreeds);
		return "/views/dogs/createForm";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Dog dog) {
		dogService.save(dog);
		return "redirect:/views/dogs/";
	}
}