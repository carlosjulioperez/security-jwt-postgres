package com.nirsa.security.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private String username;
	private List<String> roles;

	public JwtResponse(String accessToken, String username, List<String> roles) {
		this.accessToken = accessToken;
		this.username = username;
		this.roles = roles;
	}

}
