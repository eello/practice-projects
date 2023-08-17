package eello.oauthback.controller;

import static eello.oauthback.entity.OAuthProvider.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eello.oauthback.service.OAuthService;
import eello.oauthback.repository.OAuthStateRepository;
import eello.oauthback.vo.AccessTokenResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class OAuthController {

	private final OAuthService oAuthService;
	private final OAuthStateRepository stateRepository;

	@GetMapping("/login/kakao")
	public void loginWithKakao(HttpServletResponse response) throws IOException {
		String redirectUri = oAuthService.getLoginUri(KAKAO);
		response.sendRedirect(redirectUri);
	}

	@GetMapping("/code/kakao")
	public String callbackForKakao(
		@RequestParam(value = "code", required = false) String code,
		@RequestParam(value = "state", required = false) String state) {

		if (!stateRepository.isExists(state)) {
			log.warn("invalid state value");
			return "kakao login fail: invalid state value";
		}

		AccessTokenResponseVO accessToken = oAuthService.getAccessToken(KAKAO, code, null);
		log.info("kakao access token: {}", accessToken);

		Map<String, String> userInfo =
			oAuthService.getOAuthUserInfo(KAKAO, accessToken.getTokenType(), accessToken.getAccessToken());
		log.info("kakao user info: {}", userInfo);

		return "kakao login success";
	}

	@GetMapping("/login/naver")
	public void loginWithNaver(HttpServletResponse response) throws IOException {
		String redirectUri = oAuthService.getLoginUri(NAVER);
		response.sendRedirect(redirectUri);
	}

	@GetMapping("/code/naver")
	public String callbackForNaver(
		@RequestParam(value = "code", required = false) String code,
		@RequestParam(value = "state", required = false) String state) {
		
		if (!stateRepository.isExists(state)) {
			log.warn("invalid state value");
			return "naver login fail: invalid state value";
		}

		AccessTokenResponseVO accessToken = oAuthService.getAccessToken(NAVER, code, state);
		log.info("naver access token: {}", accessToken);

		Map<String, String> userInfo =
			oAuthService.getOAuthUserInfo(NAVER, accessToken.getTokenType(), accessToken.getAccessToken());
		log.info("naver user info: {}", userInfo);

		return "naver login success";
	}
}
