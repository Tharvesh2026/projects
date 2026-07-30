package server.micro.order.controller;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import server.micro.order.dto.OrderResponse;
import server.micro.order.dto.ProductResponse;
import server.micro.order.entity.Order;
import server.micro.order.repo.OrderRepo;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OderController {
    private final OrderRepo repo;
    private final WebClient.Builder builder;

    @PostMapping("/place-my-order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Order order) {

        if (order.getPid() == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        return builder.build()
                .get()
                .uri("http://localhost:8081/api/product/id/" + order.getPid())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .map(productResponse -> {

                    repo.save(order);

                    OrderResponse response = new OrderResponse();
                    response.setId(order.getId());
                    response.setPId(order.getPid());
                    response.setQuantity(order.getQuantity());
                    response.setPName(productResponse.getName());
                    response.setPPrice(productResponse.getPrice());
                    response.setPrice(order.getQuantity() * productResponse.getPrice());

                    return ResponseEntity.ok(response);
                });
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(repo.findAll(pageable));
    }
}
