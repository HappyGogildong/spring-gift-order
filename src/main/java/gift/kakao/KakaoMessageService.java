package gift.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import gift.dto.kakaoApi.KakaoMessageRequest;
import gift.dto.kakaoApi.Link;
import gift.dto.request.OrderRequestDto;
import gift.exception.MessageNotSentException;
import gift.service.ProductService;
import java.util.Map;
import org.springframework.stereotype.Service;


@Service
public class KakaoMessageService {

    private final KakaoMessageInterface kakaoMessageInterface;
    private final KakaoAuthService kakaoAuthService;
    static final int MESSAGE_SEND_SUCCESS = 0;

    public KakaoMessageService(
        KakaoMessageInterface kakaoMessageInterface,
        KakaoAuthService kakaoAuthService, ProductService productService) {
        this.kakaoMessageInterface = kakaoMessageInterface;
        this.kakaoAuthService = kakaoAuthService;
    }

    public void sendMessageToMyself(String email, OrderRequestDto request)
    {
        String token = kakaoAuthService.getKakaoTokenByEmail(email);
        String bearerToken = "Bearer " + token;
        String jsonString = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        KakaoMessageRequest kakaoMessageRequest = new KakaoMessageRequest(
            "text",
            request.message(),
            new Link("http://localhost/8080"),
            "View Details",
            null
        );

        try{
            jsonString = objectMapper.writeValueAsString(kakaoMessageRequest);
            System.out.println(jsonString);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        Map<String, Object> responseWithResultCode = kakaoMessageInterface.sendMessageToMySelf(
            bearerToken,
            jsonString).getBody();

        if (responseWithResultCode == null ||
            !responseWithResultCode.get("result_code").equals(MESSAGE_SEND_SUCCESS)){
            throw new MessageNotSentException("메세지 전송은 실패했지만 주문은 성공함");
        }
    }
}
