package gift.service;

import gift.dto.request.OrderRequestDto;
import gift.dto.response.OrderResponseDto;
import gift.entity.Order;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.MessageNotSentException;
import gift.kakao.KakaoMessageService;
import gift.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OptionService optionService;
    private final MemberService memberService;
    private final WishService wishService;
    private final KakaoMessageService kakaoMessageService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(
        OptionService optionService,
        ProductService productService,
        WishService wishService,
        MemberService memberService,
        KakaoMessageService kakaoMessageService,
        OrderRepository orderRepository)
    {
        this.optionService = optionService;
        this.wishService = wishService;
        this.memberService = memberService;
        this.kakaoMessageService = kakaoMessageService;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDto toResponseDto(Order order) {

        return new OrderResponseDto(
            order.getId(),
            order.getOption().getId(),
            order.getQuantity(),
            order.getOrderDateTime(),
            order.getMessage()
        );
    }

    // 옵션 수량 제거 v
    // 위시에 있다면 위시에서 목록 제거 v
    // 주문 내역 카카오톡 메세지로 전송 v
    @Override
    @Transactional
    public OrderResponseDto createOrder(String userEmail, OrderRequestDto orderRequestDto) {

        Product product = optionService.getProductByOptionId(orderRequestDto.optionId());
        Long memberId = memberService.getMemberIdByEmail(userEmail);

        Optional<Wish> wish = wishService.findWishByMemberIdAndProduct(memberId, product);
        wish.ifPresent(val -> wishService.deleteWish(userEmail, val.getId()));

        optionService.subtract(
            orderRequestDto.optionId(),
            orderRequestDto.quantity());

        Order order = new Order();
        order.setOption(
            optionService.findOptionById(orderRequestDto.optionId()).orElseThrow());
        order.setQuantity(orderRequestDto.quantity());
        order.setOrderDateTime(LocalDateTime.now());
        order.setMessage(orderRequestDto.message());
        Order savedOrder = orderRepository.save(order);

        kakaoMessageService.sendMessageToMyself(userEmail, orderRequestDto);

        return toResponseDto(savedOrder);
    }
}
