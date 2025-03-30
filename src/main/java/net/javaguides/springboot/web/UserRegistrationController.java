package net.javaguides.springboot.web;

import net.javaguides.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("/registration")
public class UserRegistrationController {
	private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
	private final UserService userService;

	public UserRegistrationController(UserService userService) {
		this.userService = userService;
		log.info("UserRegistrationController initialized");
	}

	@GetMapping
	public ResponseEntity<?> getRegistrationInfo() {
		log.debug("Received GET request for registration info");
		String responseMessage = "{\"message\": \"Send POST request with user data to register.\"}";
		log.info("Returning registration instructions");
		return ResponseEntity.ok(responseMessage);
	}

	@PostMapping
	public ResponseEntity<?> registerUserAccount(@RequestBody UserRegistrationDto registrationDto) {
		log.info("Received registration request for email: {}", registrationDto.getEmail());
		log.debug("Registration DTO details: {}", registrationDto.toString());

		try {
			User registeredUser = userService.save(registrationDto);
			log.info("User registered successfully with ID: {}", registeredUser.getId());
			log.debug("Registered user details: {}", registeredUser.toString());

			return ResponseEntity.ok(registeredUser);
		} catch (Exception e) {
			log.error("Registration failed for email: {}", registrationDto.getEmail(), e);
			return ResponseEntity.badRequest().body("{\"error\": \"Registration failed. Please try again later.\"}");
		}
	}
}