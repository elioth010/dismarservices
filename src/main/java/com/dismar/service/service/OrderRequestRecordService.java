package com.dismar.service.service;

import com.dismar.service.service.dto.OrderRequestRecordDTO;
import java.util.List;

/**
 * Service Interface for managing OrderRequestRecord.
 */
public interface OrderRequestRecordService {

    /**
     * Save a orderRequestRecord.
     *
     * @param orderRequestRecordDTO the entity to save
     * @return the persisted entity
     */
    OrderRequestRecordDTO save(OrderRequestRecordDTO orderRequestRecordDTO);

    /**
     * Get all the orderRequestRecords.
     *
     * @return the list of entities
     */
    List<OrderRequestRecordDTO> findAll();

    /**
     * Get the "id" orderRequestRecord.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrderRequestRecordDTO findOne(Long id);

    /**
     * Delete the "id" orderRequestRecord.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
