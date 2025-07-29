package gift.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAuthTokenResponse(

    String tokenType,
    String accessToken,
    int expiresIn,
    String refreshToken,
    int refreshTokenExpiresIn,
    String scope
) {

}
