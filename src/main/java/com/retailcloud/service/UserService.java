package com.retailcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.retailcloud.dto.LoginResponse;
import com.retailcloud.dto.RegisterRequest;
import com.retailcloud.model.Users;
import com.retailcloud.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private UserRepo userRepo;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public Users register(RegisterRequest request) {
		Users user = new Users();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		Users savedUser = userRepo.save(user);
		return savedUser;
	}

	public LoginResponse verify(RegisterRequest user) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
//			Users userData = userRepo.findByUsername(user.getUsername());
//			boolean isAdmin = userData.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()));
			String token = jwtService.generateToken(user.getUsername());
			LoginResponse response = new LoginResponse(token);
			return response;
		} else {
			return null;
		}
	}
}