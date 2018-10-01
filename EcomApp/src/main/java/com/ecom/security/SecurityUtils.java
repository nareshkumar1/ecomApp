package com.ecom.security;

import static com.ecom.security.SecurityConstants.HEADER_STRING;
import static com.ecom.security.SecurityConstants.SECRET;
import static com.ecom.security.SecurityConstants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;

public class SecurityUtils {

	public static String getUserNameFromToken(HttpServletRequest req) {
		String token = req.getHeader(HEADER_STRING);
		String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
				.getSubject();

		return user;
	}
}
