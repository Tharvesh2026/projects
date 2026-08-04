package com.opensourceapi.server.repository;

import com.opensourceapi.server.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByCategoryIgnoreCase(String category);
}
