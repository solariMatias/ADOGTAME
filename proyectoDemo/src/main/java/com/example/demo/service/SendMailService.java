package com.example.demo.service;

public interface SendMailService {
	
	public void sendMail(String from, String to, String subject, String body);
}
