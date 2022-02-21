package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.SendMailService;

@Controller
@RequestMapping("/views/mails")
public class MailController {
	private static final String EMAIL_SENT = "SU EMAIL HA SIDO ENVIADO";
	private static final String TITTLE_SECTION = "ENVIE SU CONSULTA";
	private Long idDog;
	@Autowired
	private SendMailService sendMailService;

	@GetMapping("/consultar/{id}")
	public String consultar(@PathVariable("id") Long id, Model model) {
		this.idDog = id;
		model.addAttribute("perro", this.idDog);
		model.addAttribute("titulo", TITTLE_SECTION);
		return "/views/mails/sendMailForm";
	}

	@PostMapping("/consultar/{id}")
	public String sendMail(@PathVariable("id") Long id, @RequestParam("mail") String mail, @RequestParam("comment") String description, RedirectAttributes attribute) {
		String message = "\n INFORMACIÓN RECIBIDA: " + "\n Mail usuario: " + mail + "\n Descripción usuario: " + description;
		this.sendMailService.sendMail("adogtameproject@gmail.com", mail, "TESTING", message);
		attribute.addFlashAttribute("sent", EMAIL_SENT);
		return "redirect:/views/dogs/";
	}

}
