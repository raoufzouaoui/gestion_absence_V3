package com.raouf.gestionabsence;

import com.raouf.gestionabsence.config.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication
public class ProjetWebVersion11Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetWebVersion11Application.class, args);
	}

//	@PostConstruct
//	public void init(){
//		// Setting Spring Boot SetTimeZone
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//	}

	/*@Bean
	public CorsFilter customCorsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}*/
	/*@Bean
	public CorsFilter customCorsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// Add the necessary "Access-Control-Allow-Origin" header
		corsConfiguration.addAllowedHeader("Access-Control-Allow-Origin");

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		// Log the CORS configuration
		logCorsConfiguration(corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	private void logCorsConfiguration(CorsConfiguration corsConfiguration) {
		Logger logger = LoggerFactory.getLogger(ProjetWebVersion11Application.class);
		logger.info("CORS Configuration - Allow Credentials: {}", corsConfiguration.getAllowCredentials());
		logger.info("CORS Configuration - Allowed Origins: {}", corsConfiguration.getAllowedOrigins());
		logger.info("CORS Configuration - Allowed Headers: {}", corsConfiguration.getAllowedHeaders());
		logger.info("CORS Configuration - Allowed Methods: {}", corsConfiguration.getAllowedMethods());
	}*/

}
