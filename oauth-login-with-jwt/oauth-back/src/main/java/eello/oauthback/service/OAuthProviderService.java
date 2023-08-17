package eello.oauthback.service;

import java.util.Map;

import eello.oauthback.vo.AccessTokenResponseVO;

public interface OAuthProviderService {

	String getLoginUri(String state);
	AccessTokenResponseVO getAccessToken(String authorizationCode, String state);

	Map<String, String> getOAuthUserInfo(String tokenType, String accessToken);
}
