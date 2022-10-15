package com.deutsche.naceservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deutsche.naceservices.model.NaceOrder;

@Repository
public interface NaceRepository extends JpaRepository<NaceOrder, Integer> {

}