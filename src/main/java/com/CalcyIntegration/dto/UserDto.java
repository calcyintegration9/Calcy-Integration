package com.CalcyIntegration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private String password;

	private String login;

	private String firstName;

	private String lastName;

	private String email;

	private boolean activated = false;

}
