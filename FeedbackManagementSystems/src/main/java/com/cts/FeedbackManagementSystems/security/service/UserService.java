package com.cts.FeedbackManagementSystems.security.service;



import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.cts.FeedbackManagementSystems.security.model.Role;
import com.cts.FeedbackManagementSystems.security.model.User;

import reactor.core.publisher.Mono;

@Service
public class UserService {
	private final String userUsername = "user";// password: user
	private final User user = new User(userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER));
	
	private final String user1Username = "ashok";// password: ashok
	private final User user1 = new User(user1Username, "ePh2Bv35qiwoiqIeYF1FNU344REW8CrFvJT+oUgDRyI=", true, Arrays.asList(Role.ROLE_PMO));
	
	
	private final String pmoUsername = "pmo";// password: pmo
	private final User pmo = new User(pmoUsername, "S3ReJy0odJ/k1Kh7JYY/cPplbH+Gls/Y5BbO5fwGDE0=", true, Arrays.asList(Role.ROLE_PMO));
	
	private final String pocUsername = "poc";// password: poc
	private final User poc = new User(pocUsername, "kXfZwSmUmW1Hh5Nf8vksyzkh/0J6UJoqWPUg/8ugg7Q=", true, Arrays.asList(Role.ROLE_POC));
	
	
	private final String adminUsername = "admin";// password: admin
	private final User admin = new User(adminUsername, "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN));
	
	public Mono<User> findByUsername(String username) {
		if (username.equals(pmoUsername)) {
			return Mono.just(pmo);
		}else if (username.equals(user1Username)) {
			return Mono.just(user1);
		}else if (username.equals(userUsername)) {
			return Mono.just(user);
		}else if (username.equals(pocUsername)) {
			return Mono.just(poc);
		} 
		else if (username.equals(adminUsername)) {
			return Mono.just(admin);
		} else {
			return Mono.empty();
		}
	}
	
}
