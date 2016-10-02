package com.cloud.service;

import com.cloud.model.vo.Token;
import com.cloud.model.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@PropertySource( value = { "classpath:application.properties" })
public class OauthService {

	@Autowired
	private Environment environment;

	@Autowired
	private RestTemplate restTemplate;

	private final String imgResize = "_80x80";
	private final String [] imgList = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

	public String getUriPath(){
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("oauth.url.authorization") )
				.queryParam( environment.getRequiredProperty("oauth.client.id.key"), environment.getRequiredProperty("oauth.client.id.value") )
				.queryParam( environment.getRequiredProperty("oauth.response.type.key"), environment.getRequiredProperty("oauth.response.type.value") )
				.queryParam( environment.getRequiredProperty("oauth.redirect.uri.key"), environment.getRequiredProperty("oauth.redirect.uri.value"));
		return urlBuilder.build().encode().toUriString();
	}

	public Token getToken(String code) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("oauth.url.token") )
				.queryParam( environment.getRequiredProperty("oauth.grant.type.key"), environment.getRequiredProperty("oauth.grant.type.value") )
				.queryParam( environment.getRequiredProperty("oauth.client.id.key"), environment.getRequiredProperty("oauth.client.id.value") )
				.queryParam( environment.getRequiredProperty("oauth.client.secret.key"), environment.getRequiredProperty("oauth.client.secret.value") )
				.queryParam( environment.getRequiredProperty("oauth.code.key"), code);
		return restTemplate.getForObject(urlBuilder.build().encode().toUriString(), Token.class);
	}

	public UserVO getUserInfo(Token tokenInfo) {
		UserVO uvo = restTemplate.getForObject(environment.getRequiredProperty("oauth.url.my.info")+"?token="
												+tokenInfo.getAccess_token(), UserVO.class);

		for(String imgType : imgList){
			uvo.setProfile_image( uvo.getProfile_image().replace(imgType, imgResize + imgType) );
		}

		return uvo;
	}
}