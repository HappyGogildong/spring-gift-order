package gift.dto.response;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record OrderResponseDto(

    Long orderId,
    Long optionId,
    int quantity,
    LocalDateTime orderDateTime,
    String message

) {

}
