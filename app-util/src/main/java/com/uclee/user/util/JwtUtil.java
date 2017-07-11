package com.uclee.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public class JwtUtil {
	private final static String secret="g4345gHertr,";
	public static String genToken(Integer userId){
		String token="";
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    token = JWT.create().withClaim("userId", userId).sign(algorithm);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}
	public static Integer decodeTokenToGetUserId(String token){
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    return Integer.valueOf(jwt.getClaim("userId").toString());
		} catch (Exception exception){
		    //Invalid signature/claims
		}
		return 0;
	}
	
/*	public static void main(String[] args){
		String s=genToken("3452352352345");
		System.out.println(s);
		System.out.println(decodeTokenToGetUserId(s));
		System.out.println(genToken("3453463456734564687dfgderfg"));
	}*/
}
