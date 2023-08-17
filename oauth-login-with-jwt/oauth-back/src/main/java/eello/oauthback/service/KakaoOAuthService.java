package eello.oauthback.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import eello.oauthback.vo.AccessTokenResponseVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("KAKAO")
public class KakaoOAuthService implements OAuthProviderService {

	private static final String REDIRECT_URI = "http://localhost:8080/auth/code/kakao";

	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Override
	public String getLoginUri(String state) {
		return UriComponentsBuilder.newInstance()
			.scheme("https")
			.host("kauth.kakao.com")
			.path("/oauth/authorize")
			.queryParam("client_id", clientId)
			.queryParam("redirect_uri", REDIRECT_URI)
			.queryParam("response_type", "code")
			.queryParam("state", state)
			.build()
			.toUriString();
	}

	@Override
	public AccessTokenResponseVO getAccessToken(String authorizationCode, String state) {
		String baseUri = "https://kauth.kakao.com";
		String uri = "/oauth/token";

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", clientId);
		formData.add("redirect_uri", REDIRECT_URI);
		formData.add("grant_type", "authorization_code");
		formData.add("code", authorizationCode);

		WebClient webClient = WebClient.builder()
			.baseUrl(baseUri)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();

		AccessTokenResponseVO response = webClient.post()
			.uri(uri)
			.body(BodyInserters.fromFormData(formData))
			.retrieve()
			.bodyToMono(AccessTokenResponseVO.class)
			.block();

		return response;
	}

	@Override
	public Map<String, String> getOAuthUserInfo(String tokenType, String accessToken) {
		String baseUri = "https://kapi.kakao.com";
		String uri = "/v2/user/me";

		WebClient webClient = WebClient.builder()
			.baseUrl(baseUri)
			.defaultHeader(HttpHeaders.AUTHORIZATION, tokenType + " " + accessToken)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();

		Map response = webClient.get()
			.uri(uri)
			.retrieve()
			.bodyToMono(Map.class)
			.block();

		return response;
	}
}
