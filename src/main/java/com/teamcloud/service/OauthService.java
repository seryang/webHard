package com.teamcloud.service;

import com.teamcloud.model.vo.UserVO;
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
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("URL_OAUTH_AUTHORIZATION") )
				.queryParam( environment.getRequiredProperty("K_CLIENT_ID"), environment.getRequiredProperty("V_CLIENT_ID") )
				.queryParam( environment.getRequiredProperty("K_RESPONSE_TYPE"), environment.getRequiredProperty("V_RESPONSE_TYPE") )
				.queryParam( environment.getRequiredProperty("K_REDIRECT_URI"), "http://localhost:8080/authorization");
		return urlBuilder.build().encode().toUriString();
	}

	public JSONObject getToken(String code) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl( environment.getRequiredProperty("URL_OAUTH_TOKEN") )
				.queryParam( environment.getRequiredProperty("K_GRANT_TYPE"), environment.getRequiredProperty("V_GRANT_TYPE") )
				.queryParam( environment.getRequiredProperty("K_CLIENT_ID"), environment.getRequiredProperty("V_CLIENT_ID") )
				.queryParam( environment.getRequiredProperty("K_CLIENT_SECRET"), environment.getRequiredProperty("V_CLIENT_SECRET") )
				.queryParam( environment.getRequiredProperty("K_CODE"), code);
		String response = restTemplate.getForObject(urlBuilder.build().encode().toUriString(), String.class);
		return new JSONObject(response);
	}

	public UserVO getUserInfo(JSONObject tokenInfo) {
		JSONObject userJson = new JSONObject( restTemplate.getForObject(environment.getRequiredProperty("URL_MY_INFO")+"?token="
												+tokenInfo.get( environment.getRequiredProperty("K_ACCESS_TOKEN")), String.class) );
		
		UserVO userInfo = new UserVO( userJson.getString("email"),
									  userJson.getString("name"), 
									  userJson.getString("profile_image").replace(".jpg", "_80x80.jpg"),
									  userJson.getJSONArray("teams").getJSONObject(0).getJSONObject("department").getString("name")
									  );
		return userInfo;
	}
}