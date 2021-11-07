package com.nab.payment.repository;

import com.nab.payment.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Long> {

	Orders findByOrderId(int orderId);

	List<Orders> findAll();

}
