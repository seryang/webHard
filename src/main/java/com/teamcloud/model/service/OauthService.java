package com.teamcloud.model.service;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.teamcloud.model.vo.UserVO;
import com.teamcloud.utill.UrlPath;

@Service
public class OauthService {

	@Resource (name="TeamUP_Login_URI")
	private UrlPath urlPath;

	@Resource (name="restTemplate")
	private RestTemplate restTemplate;

	public String getUriPath(){
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(urlPath.URL_OAUTH_AUTHORIZATION)
				.queryParam(urlPath.K_CLIENT_ID, urlPath.V_CLIENT_ID)
				.queryParam(urlPath.K_RESPONSE_TYPE, urlPath.V_RESPONSE_TYPE)
				.queryParam(urlPath.K_REDIRECT_URI, "http://localhost:8080/authorization.est");
		return urlBuilder.build().encode().toUriString();
	}

	public JSONObject getToken(String code) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(urlPath.URL_OAUTH_TOKEN)
				.queryParam(urlPath.K_GRANT_TYPE, urlPath.V_GRANT_TYPE)
				.queryParam(urlPath.K_CLIENT_ID, urlPath.V_CLIENT_ID)
				.queryParam(urlPath.K_CLIENT_SECRET, urlPath.V_CLIENT_SECRET)
				.queryParam(urlPath.K_CODE, code);
		String response = restTemplate.getForObject(urlBuilder.build().encode().toUriString(), String.class);
		return new JSONObject(response);
	}

	public UserVO getUserInfo(JSONObject tokenInfo) {
		JSONObject userJson = new JSONObject( restTemplate.getForObject(urlPath.URL_MY_INFO+"?token="
												+tokenInfo.get(urlPath.K_ACCESS_TOKEN), String.class) );
		
		UserVO userInfo = new UserVO( userJson.getString("email"),
									  userJson.getString("name"), 
									  userJson.getString("profile_image").replace(".jpg", "_80x80.jpg"),
									  userJson.getJSONArray("teams").getJSONObject(0).getJSONObject("department").getString("name")
									  );
		return userInfo;
	}
}