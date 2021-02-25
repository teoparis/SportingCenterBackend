package com.sportingCenterBackEnd.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sportingCenterBackEnd.dto.LocalUser;
import com.sportingCenterBackEnd.dto.SignUpRequest;
import com.sportingCenterBackEnd.dto.SocialProvider;
import com.sportingCenterBackEnd.exception.OAuth2AuthenticationProcessingException;
import com.sportingCenterBackEnd.exception.UserAlreadyExistAuthenticationException;
import com.sportingCenterBackEnd.model.Role;
import com.sportingCenterBackEnd.model.User;
import com.sportingCenterBackEnd.repo.RoleRepository;
import com.sportingCenterBackEnd.repo.UserRepository;
import com.sportingCenterBackEnd.security.oauth2.user.OAuth2UserInfo;
import com.sportingCenterBackEnd.security.oauth2.user.OAuth2UserInfoFactory;
import com.sportingCenterBackEnd.util.GeneralUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setNumber(formDTO.getNumber());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		user.setDataNascita(invertDate(formDTO.getDataNascita()));
		user.setAbbonamento(formDTO.getAbbonamento());
		user.setDataScadenza(invertDate(formDTO.getDataScadenza()));
		return user;
	}

	private String invertDate(String date){
		if(date!=null){
			List<String> stringhe = Arrays.asList(date.split("-"));
			if(stringhe.get(0).length()==4)
				return new String(stringhe.get(2)+"-"+stringhe.get(1)+"-"+stringhe.get(0));
		}
		return date;
	}

	private String reInvertDate(String date){
		if(date!=null){
			List<String> stringhe = Arrays.asList(date.split("-"));
			if(stringhe.get(0).length()==2)
				return new String(stringhe.get(2)+"-"+stringhe.get(1)+"-"+stringhe.get(0));
		}
		return date;
	}



	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	public User modificaUserEsistente(final SignUpRequest signUpRequest) {
		User existingUser = findUserByEmail(signUpRequest.getEmail());
		existingUser.setDisplayName(signUpRequest.getDisplayName());
		existingUser.setNumber(signUpRequest.getNumber());
		if(!signUpRequest.getPassword().equals("nochange"))
			existingUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		existingUser.setEmail(signUpRequest.getEmail());
		existingUser.setEnabled(signUpRequest.isEnabled());
		existingUser.setDataNascita(invertDate(signUpRequest.getDataNascita()));
		existingUser.setAbbonamento(signUpRequest.getAbbonamento());
		existingUser.setDataScadenza(invertDate(signUpRequest.getDataScadenza()));
		existingUser.setModifiedDate(Calendar.getInstance().getTime());
		if (existingUser.getAbbonamento() != null){
			try {
				existingUser.setExpired(check_expired(existingUser.getDataScadenza()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userRepository.save(existingUser);
	}

	private Boolean check_expired(String date) throws ParseException {
		if(date != null){
			DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
			Date dataScadenza = dateFormat.parse(date);
			Date now = new Date();
			if(now.after(dataScadenza)) {
				return true;
			}
		}
		return false;
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findUsersByRole(Long role) {
		return (List<User>) userRepository.findUsersByRole(role);
	}
}
