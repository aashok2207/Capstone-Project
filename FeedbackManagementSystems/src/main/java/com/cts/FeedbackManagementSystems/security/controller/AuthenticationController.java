package com.cts.FeedbackManagementSystems.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.FeedbackManagementSystems.security.model.AuthenticationRequest;
import com.cts.FeedbackManagementSystems.security.model.AuthenticationResponse;
import com.cts.FeedbackManagementSystems.security.service.UserService;
import com.cts.FeedbackManagementSystems.security.util.JWTUtil;
import com.cts.FeedbackManagementSystems.security.util.PBKDF2Encoder;

import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private PBKDF2Encoder passwordEncoder;

	@Autowired
	private UserService userRepository;

	//@RequestMapping(value = "/login", method = RequestMethod.POST)
	@PostMapping(value = "/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthenticationRequest ar) {
		return userRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
			if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}