package com.nab.payment.repository;

import com.nab.payment.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByUserId(Long userId);

	CartItem findByIdAndUserId(int id, Long userId);

	void deleteByIdAndUserId(int id, Long userId);

	List<CartItem> findAllByUserId(Long userId);

	List<CartItem> findAllByOrderId(int orderId);
}
