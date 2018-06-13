package com.dismar.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dismar.service.service.OrderRequestRecordService;
import com.dismar.service.web.rest.errors.BadRequestAlertException;
import com.dismar.service.web.rest.util.HeaderUtil;
import com.dismar.service.service.dto.OrderRequestRecordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderRequestRecord.
 */
@RestController
@RequestMapping("/api")
public class OrderRequestRecordResource {

    private final Logger log = LoggerFactory.getLogger(OrderRequestRecordResource.class);

    private static final String ENTITY_NAME = "orderRequestRecord";

    private final OrderRequestRecordService orderRequestRecordService;

    public OrderRequestRecordResource(OrderRequestRecordService orderRequestRecordService) {
        this.orderRequestRecordService = orderRequestRecordService;
    }

    /**
     * POST  /order-request-records : Create a new orderRequestRecord.
     *
     * @param orderRequestRecordDTO the orderRequestRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderRequestRecordDTO, or with status 400 (Bad Request) if the orderRequestRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-request-records")
    @Timed
    public ResponseEntity<OrderRequestRecordDTO> createOrderRequestRecord(@Valid @RequestBody OrderRequestRecordDTO orderRequestRecordDTO) throws URISyntaxException {
        log.debug("REST request to save OrderRequestRecord : {}", orderRequestRecordDTO);
        if (orderRequestRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderRequestRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderRequestRecordDTO result = orderRequestRecordService.save(orderRequestRecordDTO);
        return ResponseEntity.created(new URI("/api/order-request-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-request-records : Updates an existing orderRequestRecord.
     *
     * @param orderRequestRecordDTO the orderRequestRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderRequestRecordDTO,
     * or with status 400 (Bad Request) if the orderRequestRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderRequestRecordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-request-records")
    @Timed
    public ResponseEntity<OrderRequestRecordDTO> updateOrderRequestRecord(@Valid @RequestBody OrderRequestRecordDTO orderRequestRecordDTO) throws URISyntaxException {
        log.debug("REST request to update OrderRequestRecord : {}", orderRequestRecordDTO);
        if (orderRequestRecordDTO.getId() == null) {
            return createOrderRequestRecord(orderRequestRecordDTO);
        }
        OrderRequestRecordDTO result = orderRequestRecordService.save(orderRequestRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderRequestRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-request-records : get all the orderRequestRecords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderRequestRecords in body
     */
    @GetMapping("/order-request-records")
    @Timed
    public List<OrderRequestRecordDTO> getAllOrderRequestRecords() {
        log.debug("REST request to get all OrderRequestRecords");
        return orderRequestRecordService.findAll();
        }

    /**
     * GET  /order-request-records/:id : get the "id" orderRequestRecord.
     *
     * @param id the id of the orderRequestRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderRequestRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-request-records/{id}")
    @Timed
    public ResponseEntity<OrderRequestRecordDTO> getOrderRequestRecord(@PathVariable Long id) {
        log.debug("REST request to get OrderRequestRecord : {}", id);
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderRequestRecordDTO));
    }

    /**
     * DELETE  /order-request-records/:id : delete the "id" orderRequestRecord.
     *
     * @param id the id of the orderRequestRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-request-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderRequestRecord(@PathVariable Long id) {
        log.debug("REST request to delete OrderRequestRecord : {}", id);
        orderRequestRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
