package com.sportingCenterBackEnd.controller;

import com.sportingCenterBackEnd.dto.ApiResponse;
import com.sportingCenterBackEnd.dto.SignUpRequest;
import com.sportingCenterBackEnd.exception.UserAlreadyExistAuthenticationException;
import com.sportingCenterBackEnd.model.User;
import com.sportingCenterBackEnd.repo.UserRepository;
import com.sportingCenterBackEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sportingCenterBackEnd.config.CurrentUser;
import com.sportingCenterBackEnd.dto.LocalUser;
import com.sportingCenterBackEnd.util.GeneralUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	UserService userService;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content goes here");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content goes here");
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> getModeratorContent() {
		return ResponseEntity.ok("Moderator content goes here");
	}


	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		System.out.println("ciao");
		return (List<User>) userRepository.findAll();
	}

	@RequestMapping(value = "usersbyrole/{role}", method = RequestMethod.GET)
	public List<User> getUsersByRole(@PathVariable("role") Long role) {
		return (List<User>) userRepository.findUsersByRole(role);
	}

	@PostMapping("/users")
	void addUser(@RequestBody User user){
		userRepository.save(user);
	}

	@PostMapping("/users/deleteUser")
	void deleteUser(@RequestBody User user) {
		userRepository.delete(user);
	}

	@PostMapping("/modify")
	public ResponseEntity<?> modifyUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			userService.modificaUserEsistente(signUpRequest);
		} catch (UserAlreadyExistAuthenticationException e) {
			//log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(new ApiResponse(true, "Utente modificato correttamente"));
	}


}
