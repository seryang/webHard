package com.cloud;

import com.cloud.service.DataService;
import com.cloud.service.DirectoryTreeService;
import com.cloud.service.OauthService;
import com.cloud.model.MemoDao;
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

	@Bean
	public MemoDao memoDao(){
		return new MemoDao();
	}
}