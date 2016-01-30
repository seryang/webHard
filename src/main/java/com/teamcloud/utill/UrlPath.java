package com.teamcloud.utill;

/*
 * enum or properties 파일로 수정 예정
 */
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
	public final String URL_OAUTH_AUTHORIZATION = "https://auth.tmup.com/oauth2/authorize";
	public final String URL_OAUTH_TOKEN = "https://auth.tmup.com/oauth2/token?";
	public final String URL_MY_INFO = "https://auth.tmup.com/v1/user";
	public final String K_ACCESS_TOKEN = "access_token";
	public final String KEY_REFRESH_TOKEN = "refresh_token";
	public final String KEY_ERR = "err";
	public final String KEY_RES = "res";
	public final String VAL_RES_SUCCESS = "success";
}