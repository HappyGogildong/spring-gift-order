package gift.service;

import gift.dto.request.OrderRequestDto;
import gift.dto.response.OrderResponseDto;
import gift.entity.Order;

public interface OrderService {

    OrderResponseDto createOrder(String userEmail, OrderRequestDto orderRequestDto);

    OrderResponseDto toResponseDto(Order order);

}
