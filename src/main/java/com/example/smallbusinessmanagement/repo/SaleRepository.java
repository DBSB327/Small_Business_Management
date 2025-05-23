package com.example.smallbusinessmanagement.repo;

import com.example.smallbusinessmanagement.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomerId(Long customerId);

    List<Sale> findByDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    @Query("SELECT FUNCTION('DATE', s.date), SUM(i.sellingPrice * i.quantity) " +
            "FROM Sale s JOIN s.items i " +
            "WHERE s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE', s.date) " +
            "ORDER BY FUNCTION('DATE', s.date)")
    List<Object[]> getDailySalesSum(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);
}
