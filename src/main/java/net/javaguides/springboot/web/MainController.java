package net.javaguides.springboot.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/login")
	public ResponseEntity<?> login() {
		// Можно вернуть сообщение об успешном входе или инструкции для клиента
		return ResponseEntity.ok("{\"message\": \"Please, provide credentials\"}");
	}

	@GetMapping("/")
	public ResponseEntity<?> home() {
		// Отдаём приветственное сообщение или необходимые данные
		return ResponseEntity.ok("{\"message\": \"Welcome to the REST API\"}");
	}
}
