package gift.kakao;

import gift.dto.kakaoApi.KakaoMessageRequest;
import gift.dto.request.OrderRequestDto;
import gift.exception.MessageNotSentException;
import gift.service.ProductService;
import org.springframework.stereotype.Service;


@Service
public class KakaoMessageService {

    private final KakaoMessageInterface kakaoMessageInterface;
    private final KakaoAuthService kakaoAuthService;
    private final ProductService productService;
    static final int MESSAGE_SEND_SUCCESS = 0;

    public KakaoMessageService(
        KakaoMessageInterface kakaoMessageInterface,
        KakaoAuthService kakaoAuthService, ProductService productService) {
        this.kakaoMessageInterface = kakaoMessageInterface;
        this.kakaoAuthService = kakaoAuthService;
        this.productService = productService;
    }

    public void sendMessageToMyself(String email, OrderRequestDto request) {
        String token = kakaoAuthService.getKakaoTokenByEmail(email);
        String bearerToken = "Bearer " + token;

        KakaoMessageRequest messageRequest = new KakaoMessageRequest(
            "text",
            request.message(),
            null,
            null,
            null
        );

        Integer resultCode = kakaoMessageInterface.sendMessageToMySelf(bearerToken, messageRequest).getBody();

        if (resultCode != null || resultCode != MESSAGE_SEND_SUCCESS){
            throw new MessageNotSentException("메세지 전송은 실패했지만 주문은 성공함");
        }
    }
}
