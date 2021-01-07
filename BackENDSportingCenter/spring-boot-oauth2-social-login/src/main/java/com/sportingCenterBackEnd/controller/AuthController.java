package com.sportingCenterBackEnd.controller;

import javax.validation.Valid;

import com.sportingCenterBackEnd.dto.*;
import com.sportingCenterBackEnd.model.Role;
import com.sportingCenterBackEnd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import com.sportingCenterBackEnd.exception.UserAlreadyExistAuthenticationException;
import com.sportingCenterBackEnd.security.jwt.TokenProvider;
import com.sportingCenterBackEnd.service.UserService;
import com.sportingCenterBackEnd.util.GeneralUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@EnableGlobalAuthentication
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	TokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getEmail());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			userService.registerNewUser(signUpRequest);
		} catch (UserAlreadyExistAuthenticationException e) {
			//log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
	}

	@RequestMapping(value = "/{authToken}", method = RequestMethod.GET)
	public ResponseEntity<String> check_token(@PathVariable("authToken") String token) {
		token = token.substring(7, token.length());
		if(tokenProvider.validateToken(token)){
			System.out.println("token okkkk");
			Long userIdFromToken = tokenProvider.getUserIdFromToken(token);
			User user = userService.findUserById(userIdFromToken).orElseThrow();
			List<String> rolesList = new ArrayList<>();
			String userRoles = new String();
			for (Role role: user.getRoles()) {
				rolesList.add(role.getName());
				userRoles = userRoles + role.getName() + " ";
			}
			UserInfo userInfo = new UserInfo(user.getId().toString(),user.getDisplayName(),user.getEmail(),rolesList);
			return ResponseEntity.ok()
					//.headers(responseHeaders)
					.body(userRoles);
		}else {
			return null;
		}
	}




}