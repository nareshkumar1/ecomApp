package com.ecom.security;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.hibernate.modal.EcomUser;
import com.ecom.service.iface.EcomUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EcomUserService userDaoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EcomUser user = userDaoService.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				emptyList());
	}

}
