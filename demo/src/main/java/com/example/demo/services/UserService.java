package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.payload.request.token.ConfirmationToken;
import com.example.demo.repositories.UserRepository;



@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
//	public UserService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("USER WITH USERNAME " + username + "NOT FOUND")
				);
	}
	
	
	public String signUpUser(User user) {
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
		if(userExists) {
			throw new IllegalStateException("EMAIL ALREADY TAKEN");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken cfToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user
				);
		confirmationTokenService.saveConfirmationToken(cfToken);
		return token;
	}
	
	public int enableUser(String email) {
		return userRepository.enableUser(email);
	}
	
}
