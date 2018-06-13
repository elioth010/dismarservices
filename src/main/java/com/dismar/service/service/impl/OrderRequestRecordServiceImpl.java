package com.dismar.service.service.impl;

import com.dismar.service.service.OrderRequestRecordService;
import com.dismar.service.domain.OrderRequestRecord;
import com.dismar.service.repository.OrderRequestRecordRepository;
import com.dismar.service.service.dto.OrderRequestRecordDTO;
import com.dismar.service.service.mapper.OrderRequestRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderRequestRecord.
 */
@Service
@Transactional
public class OrderRequestRecordServiceImpl implements OrderRequestRecordService {

    private final Logger log = LoggerFactory.getLogger(OrderRequestRecordServiceImpl.class);

    private final OrderRequestRecordRepository orderRequestRecordRepository;

    private final OrderRequestRecordMapper orderRequestRecordMapper;

    public OrderRequestRecordServiceImpl(OrderRequestRecordRepository orderRequestRecordRepository, OrderRequestRecordMapper orderRequestRecordMapper) {
        this.orderRequestRecordRepository = orderRequestRecordRepository;
        this.orderRequestRecordMapper = orderRequestRecordMapper;
    }

    /**
     * Save a orderRequestRecord.
     *
     * @param orderRequestRecordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderRequestRecordDTO save(OrderRequestRecordDTO orderRequestRecordDTO) {
        log.debug("Request to save OrderRequestRecord : {}", orderRequestRecordDTO);
        OrderRequestRecord orderRequestRecord = orderRequestRecordMapper.toEntity(orderRequestRecordDTO);
        orderRequestRecord = orderRequestRecordRepository.save(orderRequestRecord);
        return orderRequestRecordMapper.toDto(orderRequestRecord);
    }

    /**
     * Get all the orderRequestRecords.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderRequestRecordDTO> findAll() {
        log.debug("Request to get all OrderRequestRecords");
        return orderRequestRecordRepository.findAll().stream()
            .map(orderRequestRecordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orderRequestRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderRequestRecordDTO findOne(Long id) {
        log.debug("Request to get OrderRequestRecord : {}", id);
        OrderRequestRecord orderRequestRecord = orderRequestRecordRepository.findOne(id);
        return orderRequestRecordMapper.toDto(orderRequestRecord);
    }

    /**
     * Delete the orderRequestRecord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderRequestRecord : {}", id);
        orderRequestRecordRepository.delete(id);
    }
}
