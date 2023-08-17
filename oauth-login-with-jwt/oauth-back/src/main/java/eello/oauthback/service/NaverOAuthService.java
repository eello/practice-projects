package eello.oauthback.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
@Service("NAVER")
public class NaverOAuthService implements OAuthProviderService {

	private static final String REDIRECT_URI = "http://localhost:8080/auth/code/naver";

	@Value("${oauth.naver.client-id}")
	private String clientId;

	@Value("${oauth.naver.client-secret}")
	private String clientSecret;

	@Override
	public String getLoginUri(String state) {
		return UriComponentsBuilder.newInstance()
			.scheme("https")
			.host("nid.naver.com")
			.path("/oauth2.0/authorize")
			.queryParam("client_id", clientId)
			.queryParam("redirect_uri", REDIRECT_URI)
			.queryParam("response_type", "code")
			.queryParam("state", URLEncoder.encode(state, StandardCharsets.UTF_8))
			.build()
			.toUriString();
	}

	@Override
	public AccessTokenResponseVO getAccessToken(String authorizationCode, String state) {
		String baseUri = "https://nid.naver.com";
		String uri = "/oauth2.0/token";

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "authorization_code");
		formData.add("client_id", clientId);
		formData.add("client_secret", clientSecret);
		formData.add("code", authorizationCode);
		formData.add("state", state);

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
		String baseUri = "https://openapi.naver.com";
		String uri = "/v1/nid/me";

		WebClient webClient = WebClient.builder()
			.baseUrl(baseUri)
			.defaultHeader(HttpHeaders.AUTHORIZATION, tokenType + " " + accessToken)
			.build();

		Map response = webClient.get()
			.uri(uri)
			.retrieve()
			.bodyToMono(Map.class)
			.block();

		return response;
	}
}
