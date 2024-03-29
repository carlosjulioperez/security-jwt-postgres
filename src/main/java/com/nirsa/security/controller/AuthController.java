package com.nirsa.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nirsa.security.model.Parametro;
import com.nirsa.security.model.Role;
import com.nirsa.security.model.User;
import com.nirsa.security.payload.request.LoginRequest;
import com.nirsa.security.payload.request.SignupRequest;
import com.nirsa.security.payload.response.JwtResponse;
import com.nirsa.security.payload.response.MessageResponse;
import com.nirsa.security.repository.ParametroRepository;
import com.nirsa.security.repository.RoleRepository;
import com.nirsa.security.repository.UserRepository;
import com.nirsa.security.security.jwt.JwtUtils;
import com.nirsa.security.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@ApiResponses(value = {
    @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
    @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
    @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
})
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ParametroRepository parametroRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Buscar el parámetro expiracionMs en la tabla
		Optional<Parametro> parametro = parametroRepository.findById(1L);
		int expiracionMs = parametro.isPresent() ? parametro.get().getExpiracionMs(): 600000;

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication, expiracionMs);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		System.out.println("\n\n\n>>> expiracionMs: " + expiracionMs);
		// System.out.println("\n\n\n>>> Roles: " + roles);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		// if (userRepository.existsByEmail(signUpRequest.getEmail())) {
		// 	return ResponseEntity
		// 			.badRequest()
		// 			.body(new MessageResponse("Error: Email is already in use!"));
		// }

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 encoder.encode(signUpRequest.getPassword()));
		user.setExpirarSesion(true);
		user.setEstado(true);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName("ROLE_ADMIN")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName("ROLE_MODERATOR")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName("ROLE_USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
