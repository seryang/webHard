package com.teamcloud;

import com.teamcloud.service.DataService;
import com.teamcloud.service.DirectoryTreeService;
import com.teamcloud.service.OauthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){	return new RestTemplate(); }

	@Bean
	public OauthService oauthService(){
		return new OauthService();
	}

	@Bean
	public DataService dataService(){
		return new DataService();
	}

	@Bean
	public DirectoryTreeService directoryTreeService() { return new DirectoryTreeService(); }
}