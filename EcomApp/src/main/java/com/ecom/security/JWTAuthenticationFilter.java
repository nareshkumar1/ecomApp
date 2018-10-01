package com.ecom.security;

import static com.ecom.security.SecurityConstants.EXPIRATION_TIME;
import static com.ecom.security.SecurityConstants.HEADER_STRING;
import static com.ecom.security.SecurityConstants.SECRET;
import static com.ecom.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Seller;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		
		try {
			EcomUser creds = new ObjectMapper().readValue(req.getInputStream(), EcomUser.class);
			System.out.println(creds.isRetailer());
			if(creds.isRetailer()) {
				Seller sellerCred = new ObjectMapper().readValue(req.getInputStream(), Seller.class);
				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sellerCred.getUsername(),
						sellerCred.getPassword(), new ArrayList<>()));
			} else {
				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
						creds.getPassword(), new ArrayList<>()));
			}
			

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		System.out.println(" In Authenication 1");
		String token = Jwts.builder()
				.setSubject(
						((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername().trim())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

}
