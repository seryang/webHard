package com.teamcloud;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamcloud.model.service.DataService;
import com.teamcloud.model.service.OauthService;
import com.teamcloud.utill.DataPath;
import com.teamcloud.utill.UrlPath;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	@Bean (name="oauthPath")
	public UrlPath urlPath(){
		return new UrlPath();
	}

	@Bean (name="dataPath")
	public DataPath dataPath(){
		return new DataPath();
	}

	@Bean
	public RestTemplate rest(){
		return new RestTemplate();
	}

	@Bean
	public OauthService oauthService(){
		return new OauthService();
	}

	@Bean
	public DataService dataService(){
		return new DataService();
	}
}