package gift.kakao;

import gift.dto.response.KakaoAuthTokenResponse;
import gift.entity.MemberKakaoToken;
import gift.exception.KakaoTokenException;
import gift.repository.KakaoTokenRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class KakaoAuthService {

    @Value("${custom.kakao-client-id}")
    private String kakaoClientId;

    @Value("${custom.kakao-redirect}")
    private String redirectUri;

    private final RestClient kakaoRestClient;
    private final KakaoMessageInterface kakaoMessageInterface;
    private final KakaoTokenRepository kakaoTokenRepository;

    public KakaoAuthService(
        RestClient kakaoRestClient,
        KakaoMessageInterface kakaoMessageInterface, KakaoTokenRepository kakaoTokenRepository) {
        this.kakaoRestClient = kakaoRestClient;
        this.kakaoMessageInterface = kakaoMessageInterface;
        this.kakaoTokenRepository = kakaoTokenRepository;
    }

    public ResponseEntity<KakaoAuthTokenResponse> getAuthToken(String authKey) {
        ResponseEntity<KakaoAuthTokenResponse> response = null;

        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", authKey);

        try {
            response = kakaoRestClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(KakaoAuthTokenResponse.class);
        }
        catch (RestClientResponseException e) {
            throw new KakaoTokenException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
        catch (Exception e) {
            throw new KakaoTokenException(e.getMessage());
        }

        String email = getUserEmail(response.getBody().accessToken());
        saveToken(email, response.getBody());

        return ResponseEntity.ok().build();
    }

    public String getUserEmail(String kakaoToken){
        return Objects.requireNonNull(kakaoMessageInterface
                .getUserInfo(kakaoToken)
                .getBody())
            .kakaoAccount()
            .email();
    }

    public String getKakaoTokenByEmail(String email){
        return kakaoTokenRepository.findAccessTokenByEmail(email);
    }

    public void saveToken(String email, KakaoAuthTokenResponse tokenDto){
        MemberKakaoToken memberKakaoToken = new MemberKakaoToken();
        memberKakaoToken.setEmail(email);
        memberKakaoToken.setAccessToken(tokenDto.accessToken());
        memberKakaoToken.setRefreshToken(tokenDto.refreshToken());
        memberKakaoToken.setAccessTokenexpiresAt(
            LocalDateTime.now()
                .plusSeconds(tokenDto.expiresIn()));

        kakaoTokenRepository.save(memberKakaoToken);
    }

}
