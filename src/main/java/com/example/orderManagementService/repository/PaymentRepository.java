package com.example.orderManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderManagementService.entity.PaymentDetail;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetail, Long>{

}
