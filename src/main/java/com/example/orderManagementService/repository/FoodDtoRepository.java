package com.example.orderManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderManagementService.entity.FoodDto;

@Repository
public interface FoodDtoRepository extends JpaRepository<FoodDto, Long>{

}
