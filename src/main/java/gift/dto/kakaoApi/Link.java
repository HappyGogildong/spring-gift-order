package gift.dto.kakaoApi;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Link (

    String webUrl,
    String mobileWebUrl,
    String androidExecutionParams,
    String iosExecutionParams

){

    public Link(String webUrl)
    {
        this(webUrl, webUrl, webUrl, webUrl);
    }
}
