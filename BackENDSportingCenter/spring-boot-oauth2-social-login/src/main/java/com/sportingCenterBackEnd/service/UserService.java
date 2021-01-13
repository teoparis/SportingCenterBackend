package com.sportingCenterBackEnd.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.sportingCenterBackEnd.dto.LocalUser;
import com.sportingCenterBackEnd.dto.SignUpRequest;
import com.sportingCenterBackEnd.exception.UserAlreadyExistAuthenticationException;
import com.sportingCenterBackEnd.model.User;


public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	List<User> findUsersByRole(Long role);

	User modificaUserEsistente(final SignUpRequest signUpRequest);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
