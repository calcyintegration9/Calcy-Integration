package com.CalcyIntegration.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.CalcyIntegration.Entity.Channel;
import com.CalcyIntegration.Entity.User;
import com.CalcyIntegration.Repository.ChannelRepository;
import com.CalcyIntegration.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class InstallationController {
	
    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${redirectUrl}")
    private String redirectUrl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChannelRepository channelRepository;
	
	@GetMapping("/checking")
	public String Checking() {
		return "hello!  Everything is Fine";
	}
	
	
	
	@GetMapping("install/shopify")
	public RedirectView installShopifyApp(HttpServletRequest request, @RequestParam Map<String, Object> data) {
		System.out.println("install API start");
		User user = userRepository.findByLogin(data.get("shop").toString().toLowerCase());
		if (user == null) {
			User newUser = User.builder().login(data.get("shop").toString()).password("Ankit").email("ankit@gmial.com")
					.firstName("Ankit").lastName("mittal").activated(true).build();
			userRepository.save(newUser);
		}
		String redirectUrlData = String.format(
				"https://%s/admin/oauth/authorize?client_id=%s&scope=read_products,write_products,read_orders,read_product_listings,read_customers,read_price_rules,write_price_rules&redirect_uri=%s",
				data.get("shop"), clientId, redirectUrl);
		System.out.println("API end with redirect URL " + redirectUrlData);
		return new RedirectView(redirectUrlData);
	}
	
	@GetMapping("/shopify/redirect")
    public RedirectView registerShopifyApp(HttpServletRequest request, @RequestParam Map<String, Object> data) {
        System.out.println(request);
        System.out.println("hellooo----" + data); 	
        //		String clientId = "d7b8511a684384a1ec60c6e4e61183ed";
        //        String clientSecret = "63f846d6a2ea93b3fb303835af4210ba";

        String authorizationCode = (String) data.get("code");

        String shopUrl = (String) data.get("shop"); // Replace with the actual shop URL

        // Simulating the request parameters
        Map<String, String> params = Map.of(
            "client_id",
            clientId,
            "client_secret",
            clientSecret,
            "code",
            authorizationCode // Replace with the actual access code from the URL
        );

        // Generate access token URL
        String accessTokenUrl = "https://" + shopUrl + "/admin/oauth/access_token";

        try {
            // Create a URL object
            URL url = new URL(accessTokenUrl);

            // Create an HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Write the request parameters to the connection
            String requestBody = params
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

            connection.getOutputStream().write(requestBody.getBytes());

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response content
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            // Print the response content
            System.out.println("Response Content: " + responseContent.toString());
            User userData = userRepository.findByLogin(data.get("shop").toString().toLowerCase());
            if (userData != null) {
                Channel channel = new Channel();
                channel.setAccessToken(responseContent.toString());
                channel.setAccountId(userData.getId());
                channel.setName(data.get("shop").toString());
                channelRepository.save(channel);
            }
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RedirectView("https://dde2-160-202-38-50.ngrok-free.app/checking");
    }
}
