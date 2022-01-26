package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Dog;
import com.example.demo.service.DogService;

@Controller
@RequestMapping("/views/dogs")
public class UserController {

	@Autowired
	private DogService dogService;

	@GetMapping("/")
	public String index(Model model) {

		List<Dog> listDogs = dogService.listAll();
		model.addAttribute("titulo", "Lista de perros");
		model.addAttribute("perros", listDogs);
		return "/views/dogs/list";
	}
}