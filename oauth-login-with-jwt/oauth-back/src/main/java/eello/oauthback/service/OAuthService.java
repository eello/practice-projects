package eello.oauthback.service;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import eello.oauthback.entity.OAuthProvider;
import eello.oauthback.repository.OAuthStateRepository;
import eello.oauthback.vo.AccessTokenResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

	private final Map<String, OAuthProviderService> services;
	private final OAuthStateRepository stateRepository;

	private OAuthProviderService getServiceBy(OAuthProvider provider) {
		return services.get(provider.name());
	}

	public String getLoginUri(OAuthProvider provider) {
		OAuthProviderService service = getServiceBy(provider);

		String state = RandomStringUtils.randomAlphanumeric(8);
		stateRepository.save(state);

		return service.getLoginUri(state);
	}

	public AccessTokenResponseVO getAccessToken(OAuthProvider provider, String authorizationCode, String state) {
		OAuthProviderService service = getServiceBy(provider);
		return service.getAccessToken(authorizationCode, state);
	}

	public Map<String, String> getOAuthUserInfo(OAuthProvider provider, String tokenType, String accessToken) {
		OAuthProviderService service = getServiceBy(provider);
		return service.getOAuthUserInfo(tokenType, accessToken);
	}
}
