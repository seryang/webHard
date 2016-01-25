package com.teamcloud.utill;

public class UrlPath {
	public final String K_CLIENT_ID = "client_id";
	public final String V_CLIENT_ID = "test-client";

	public final String K_RESPONSE_TYPE = "response_type";
	public final String V_RESPONSE_TYPE = "code";

	public final String K_REDIRECT_URI = "redirect_uri";

	public final String K_GRANT_TYPE = "grant_type";
	public final String V_GRANT_TYPE = "authorization_code";

	public final String K_CLIENT_SECRET = "client_secret";
	public final String V_CLIENT_SECRET = "test-secret";

	public final String K_CODE = "code";
	
	// 사용자 인증을 하는 페이지
	public final String URL_OAUTH_AUTHORIZATION = "https://auth.tmup.com/oauth2/authorize";
	
	// token을 발급받는 페이지.
	public final String URL_OAUTH_TOKEN = "https://auth.tmup.com/oauth2/token?";

	// 자신의 정보를 받는 url.
	public final String URL_MY_INFO = "https://auth.tmup.com/v1/user";

	public final String K_ACCESS_TOKEN = "access_token";
	public final String KEY_REFRESH_TOKEN = "refresh_token";

	public final String KEY_ERR = "err";
	public final String KEY_RES = "res";
	public final String VAL_RES_SUCCESS = "success";
}