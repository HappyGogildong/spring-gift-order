package gift.dto.kakaoApi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAccount(
    boolean hasEmail,
    boolean emailNeedsAgreement,
    boolean isEmailValid,
    boolean isEmailVerified,
    String email
) {}
