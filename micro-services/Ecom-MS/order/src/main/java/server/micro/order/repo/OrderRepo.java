package server.micro.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.micro.order.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
