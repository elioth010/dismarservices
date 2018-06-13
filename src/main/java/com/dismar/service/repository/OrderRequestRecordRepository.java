package com.dismar.service.repository;

import com.dismar.service.domain.OrderRequestRecord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderRequestRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRequestRecordRepository extends JpaRepository<OrderRequestRecord, Long> {

}
