package com.example.smallbusinessmanagement.repository;

import com.example.smallbusinessmanagement.dto.InventoryStatsDTO;
import com.example.smallbusinessmanagement.dto.RecentSalesDTO;
import com.example.smallbusinessmanagement.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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

    @Query("SELECT SUM(t.amount) FROM Sale s JOIN s.transaction t")
    BigDecimal sumTotalRevenue();

    @Query("""
    SELECT 
        p.id, 
        p.name, 
        p.category, 
        p.size, 
        p.color, 
        SUM(si.quantity), 
        SUM(si.quantity * si.sellingPrice)
    FROM SaleItem si 
    JOIN si.product p 
    GROUP BY p.id, p.name, p.category, p.size, p.color
    ORDER BY SUM(si.quantity) DESC
""")
    List<Object[]> findTopSellingProducts(Pageable pageable);


    List<Sale> findTop10ByOrderByDateDesc();
}
