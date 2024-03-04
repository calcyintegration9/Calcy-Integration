package com.CalcyIntegration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long customerId;
	private String firstName;
	private String lastName;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String country;
	private String zip;
	private String phone;
	private String name;
	private String provinceCode;
	private String countryCode;
	private String countryName;
	private boolean isDefault;

}
