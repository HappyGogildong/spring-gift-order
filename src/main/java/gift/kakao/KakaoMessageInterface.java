package gift.kakao;

import gift.dto.kakaoApi.KakaoMessageRequest;
import gift.dto.kakaoApi.KakaoUserInfoResponse;
import gift.dto.response.OrderResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange()
public interface KakaoMessageInterface {

    @PostExchange()
    ResponseEntity<Integer> sendMessageToMySelf(
        @RequestHeader("Authorization") String token,
        @RequestBody KakaoMessageRequest request
    );

    @GetExchange(value = "https://kapi.kakao.com/v2/user/me")
    ResponseEntity<KakaoUserInfoResponse> getUserInfo(
        @RequestHeader("Authorization") String token
    );
}
