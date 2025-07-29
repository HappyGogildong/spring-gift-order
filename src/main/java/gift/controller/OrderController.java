package gift.controller;


import gift.dto.request.OrderRequestDto;
import gift.dto.response.OrderResponseDto;
import gift.service.OrderService;
import gift.wishPreProcess.LoginMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

     public OrderController(OrderService orderService){
         this.orderService = orderService;
     }

     @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
        @LoginMember String userEmail,
        @RequestBody OrderRequestDto orderRequestDto){

         return new ResponseEntity<>(orderService.createOrder(userEmail, orderRequestDto),
             HttpStatus.CREATED);
     }

}
