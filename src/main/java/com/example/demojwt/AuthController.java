package com.example.demojwt;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/public/api/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
		Map<String, String> tokens = authService.login(request.getEmail(), request.getPassword());
		LoginResponseDto response = new LoginResponseDto(tokens.get("accessToken"), tokens.get("refreshToken"));
		return ResponseEntity.ok(response);
	}

}
