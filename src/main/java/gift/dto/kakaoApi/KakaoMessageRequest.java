package gift.dto.kakaoApi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMessageRequest(

    String objectType,
    String text,
    Link link,
    String buttonTitle,
    List<Button> buttons
) {

}
