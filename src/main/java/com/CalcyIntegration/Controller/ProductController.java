package com.CalcyIntegration.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/getallproduct")
	public String getProducts() {
		 final String apiUrl = "https://quickstart-e219e438.myshopify.com/admin/api/2024-01/customers.json";
		 final String accessToken = "shpua_0fab2d2c7668278812e90841ce764a8b";
        // Set up headers, including the access token
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Shopify-Access-Token", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                String.class);

        // Retrieve the response body
        return responseEntity.getBody();
    }

}
