package com.cloud.service;

import com.cloud.model.vo.UserVO;
import org.json.JSONObject;
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

	public String getUriPath(){
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("oauth.url.authorization") )
				.queryParam( environment.getRequiredProperty("oauth.client.id.key"), environment.getRequiredProperty("oauth.client.id.value") )
				.queryParam( environment.getRequiredProperty("oauth.response.type.key"), environment.getRequiredProperty("oauth.response.type.value") )
				.queryParam( environment.getRequiredProperty("oauth.redirect.uri.key"), environment.getRequiredProperty("oauth.redirect.uri.value"));
		return urlBuilder.build().encode().toUriString();
	}

	public JSONObject getToken(String code) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("oauth.url.token") )
				.queryParam( environment.getRequiredProperty("oauth.grant.type.key"), environment.getRequiredProperty("oauth.grant.type.value") )
				.queryParam( environment.getRequiredProperty("oauth.client.id.key"), environment.getRequiredProperty("oauth.client.id.value") )
				.queryParam( environment.getRequiredProperty("oauth.client.secret.key"), environment.getRequiredProperty("oauth.client.secret.value") )
				.queryParam( environment.getRequiredProperty("oauth.code.key"), code);
		String response = restTemplate.getForObject(urlBuilder.build().encode().toUriString(), String.class);
		return new JSONObject(response);
	}

	public UserVO getUserInfo(JSONObject tokenInfo) {
		JSONObject userJson = new JSONObject( restTemplate.getForObject(environment.getRequiredProperty("oauth.url.my.info")+"?token="
												+tokenInfo.get( environment.getRequiredProperty("oauth.access.token")), String.class) );
		
		UserVO userInfo = new UserVO( userJson.getString("email"),
									  userJson.getString("name"), 
									  userJson.getString("profile_image").replace(".jpg", "_80x80.jpg").replace(".gif", "_80x80.gif").replace(".png", "_80x80.png").replace(".bmp", "_80x80.bmp"),
									  userJson.getJSONArray("teams").getJSONObject(0).getJSONObject("department").getString("name")
									  );
		return userInfo;
	}
}