package gift.service;

import gift.dto.request.WishAddRequestDto;
import gift.dto.request.WishUpdateRequestDto;
import gift.dto.response.WishIdResponseDto;
import gift.dto.response.WishResponseDto;
import gift.entity.Product;
import gift.entity.Wish;
import java.util.List;
import java.util.Optional;

public interface WishService {

    WishIdResponseDto addProduct(WishAddRequestDto wishAddRequestDto, String email);

    List<WishResponseDto> getWishList(String email, int pageNo, String sortBy);

    void deleteWish(String email, Long wishId);

    void updateWish(Long wishId, String email, WishUpdateRequestDto wishUpdateRequestDto);

    Optional<Wish> findWishByMemberIdAndProduct(Long memberId, Product product);


}
