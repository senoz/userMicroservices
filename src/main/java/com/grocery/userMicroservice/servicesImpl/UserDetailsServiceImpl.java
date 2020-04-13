package com.grocery.userMicroservice.servicesImpl;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grocery.userMicroservice.dtos.Users;
import com.grocery.userMicroservice.repositories.UsersRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { 
	
	@Autowired
	UsersRepo usersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users applicationUser = this.usersRepo.findByUserName(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(applicationUser.getUserName(), applicationUser.getPassword(), emptyList());
	}
	
	public boolean authenticateUser(String username, String password) {
		Users applicationUser = this.usersRepo.findByUserName(username);
		if (applicationUser == null) {
			return false;
		}
		return true;
	}
}
