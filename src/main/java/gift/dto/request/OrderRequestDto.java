package gift.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(

    @NotNull
    Long optionId,

    @Min(1)
    @Max(100_000_000-1)
    int quantity,

    String message
) {

}
