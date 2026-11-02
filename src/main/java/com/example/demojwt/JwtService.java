package com.example.demojwt;


import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

@Service
public class JwtService {

	private final String secretKey = "3TKnIyAF8tGYSkbuNa0yVwQCdKokZsjFpwdBU8DGz2QBN5rqgxKqYI47iZwCcC3N";
	
	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}
	
	public String generateAccessToken(User user) {
		
		Date issueTime = new Date();
		Date expirationTime = Date.from(issueTime.toInstant().plus(30, ChronoUnit.MINUTES));
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issueTime(issueTime)
				.expirationTime(expirationTime)
				.build();
		Payload payload = new Payload(claimsSet.toJSONObject());
		JWSObject jwsObject = new JWSObject(header, payload);
		try {
			jwsObject.sign(new MACSigner(secretKey));
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		return jwsObject.serialize();
	}
	
	public String generateRefreshToken(User user) {
		
		Date issueTime = new Date();
		Date expirationTime = Date.from(issueTime.toInstant().plus(30, ChronoUnit.DAYS));
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issueTime(issueTime)
				.expirationTime(expirationTime)
				.build();
		Payload payload = new Payload(claimsSet.toJSONObject());
		JWSObject jwsObject = new JWSObject(header, payload);
		try {
			jwsObject.sign(new MACSigner(secretKey));
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		return jwsObject.serialize();
	}

	
}
