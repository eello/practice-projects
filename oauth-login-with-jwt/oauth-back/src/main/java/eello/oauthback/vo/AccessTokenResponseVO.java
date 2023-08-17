package eello.oauthback.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccessTokenResponseVO {

	private String tokenType;
	private String accessToken;
	private Integer expiresIn;
	private String refreshToken;
	private String state;
}
