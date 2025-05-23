package com.example.smallbusinessmanagement.repo;

import com.example.smallbusinessmanagement.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
