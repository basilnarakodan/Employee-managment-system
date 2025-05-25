package com.retailcloud.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.retailcloud.model.UserPrincipal;
import com.retailcloud.repository.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

	@Autowired
	MyUserDetailsService myUserDetailsService;
	@Autowired
	UserRepo userRepo;

	private static String secretkey = "dGVzdGtleWZvc3V1c2VyYWRtaW5lY2x1ZGVpZC03Nzg5Zm9yZXgtMzg6Z29vZ2xlNzRz";

	public JwtService() {

//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
	}

	public String generateToken(String username) {
		UserPrincipal userDetails = myUserDetailsService.loadUserByUsername(username);
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (30 * 60 * 1000))).and().signWith(getKey()).compact();

	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		// extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}

	public String extractUserId(String token) {
		try {
			Claims claims = extractAllClaims(token);
			return claims.get("userId", String.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid or expired token", e);
		}
	}

	public String extractRole(String token) {
		try {
			Claims claims = extractAllClaims(token);
			String role = claims.get("role", String.class);
			return role;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid or expired token", e);
		}
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String getTokenFromRequest(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader("Authorization").substring(7,
				httpServletRequest.getHeader("Authorization").length());

	}

	public String getUserIdFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userId = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			userId = extractUserId(token);

		}
		return userId;
	}

}