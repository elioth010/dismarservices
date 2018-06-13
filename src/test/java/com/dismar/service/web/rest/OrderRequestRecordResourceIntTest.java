package com.dismar.service.web.rest;

import com.dismar.service.DismarservicesApp;

import com.dismar.service.domain.OrderRequestRecord;
import com.dismar.service.repository.OrderRequestRecordRepository;
import com.dismar.service.service.OrderRequestRecordService;
import com.dismar.service.service.dto.OrderRequestRecordDTO;
import com.dismar.service.service.mapper.OrderRequestRecordMapper;
import com.dismar.service.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.dismar.service.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderRequestRecordResource REST controller.
 *
 * @see OrderRequestRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DismarservicesApp.class)
public class OrderRequestRecordResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_SUB_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUB_TOTAL = new BigDecimal(2);

    @Autowired
    private OrderRequestRecordRepository orderRequestRecordRepository;

    @Autowired
    private OrderRequestRecordMapper orderRequestRecordMapper;

    @Autowired
    private OrderRequestRecordService orderRequestRecordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderRequestRecordMockMvc;

    private OrderRequestRecord orderRequestRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderRequestRecordResource orderRequestRecordResource = new OrderRequestRecordResource(orderRequestRecordService);
        this.restOrderRequestRecordMockMvc = MockMvcBuilders.standaloneSetup(orderRequestRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderRequestRecord createEntity(EntityManager em) {
        OrderRequestRecord orderRequestRecord = new OrderRequestRecord()
            .quantity(DEFAULT_QUANTITY)
            .subTotal(DEFAULT_SUB_TOTAL);
        return orderRequestRecord;
    }

    @Before
    public void initTest() {
        orderRequestRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderRequestRecord() throws Exception {
        int databaseSizeBeforeCreate = orderRequestRecordRepository.findAll().size();

        // Create the OrderRequestRecord
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(orderRequestRecord);
        restOrderRequestRecordMockMvc.perform(post("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderRequestRecord in the database
        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeCreate + 1);
        OrderRequestRecord testOrderRequestRecord = orderRequestRecordList.get(orderRequestRecordList.size() - 1);
        assertThat(testOrderRequestRecord.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderRequestRecord.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void createOrderRequestRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRequestRecordRepository.findAll().size();

        // Create the OrderRequestRecord with an existing ID
        orderRequestRecord.setId(1L);
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(orderRequestRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderRequestRecordMockMvc.perform(post("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderRequestRecord in the database
        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRequestRecordRepository.findAll().size();
        // set the field null
        orderRequestRecord.setQuantity(null);

        // Create the OrderRequestRecord, which fails.
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(orderRequestRecord);

        restOrderRequestRecordMockMvc.perform(post("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isBadRequest());

        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRequestRecordRepository.findAll().size();
        // set the field null
        orderRequestRecord.setSubTotal(null);

        // Create the OrderRequestRecord, which fails.
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(orderRequestRecord);

        restOrderRequestRecordMockMvc.perform(post("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isBadRequest());

        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderRequestRecords() throws Exception {
        // Initialize the database
        orderRequestRecordRepository.saveAndFlush(orderRequestRecord);

        // Get all the orderRequestRecordList
        restOrderRequestRecordMockMvc.perform(get("/api/order-request-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderRequestRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getOrderRequestRecord() throws Exception {
        // Initialize the database
        orderRequestRecordRepository.saveAndFlush(orderRequestRecord);

        // Get the orderRequestRecord
        restOrderRequestRecordMockMvc.perform(get("/api/order-request-records/{id}", orderRequestRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderRequestRecord.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderRequestRecord() throws Exception {
        // Get the orderRequestRecord
        restOrderRequestRecordMockMvc.perform(get("/api/order-request-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderRequestRecord() throws Exception {
        // Initialize the database
        orderRequestRecordRepository.saveAndFlush(orderRequestRecord);
        int databaseSizeBeforeUpdate = orderRequestRecordRepository.findAll().size();

        // Update the orderRequestRecord
        OrderRequestRecord updatedOrderRequestRecord = orderRequestRecordRepository.findOne(orderRequestRecord.getId());
        // Disconnect from session so that the updates on updatedOrderRequestRecord are not directly saved in db
        em.detach(updatedOrderRequestRecord);
        updatedOrderRequestRecord
            .quantity(UPDATED_QUANTITY)
            .subTotal(UPDATED_SUB_TOTAL);
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(updatedOrderRequestRecord);

        restOrderRequestRecordMockMvc.perform(put("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isOk());

        // Validate the OrderRequestRecord in the database
        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeUpdate);
        OrderRequestRecord testOrderRequestRecord = orderRequestRecordList.get(orderRequestRecordList.size() - 1);
        assertThat(testOrderRequestRecord.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderRequestRecord.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderRequestRecord() throws Exception {
        int databaseSizeBeforeUpdate = orderRequestRecordRepository.findAll().size();

        // Create the OrderRequestRecord
        OrderRequestRecordDTO orderRequestRecordDTO = orderRequestRecordMapper.toDto(orderRequestRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderRequestRecordMockMvc.perform(put("/api/order-request-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRequestRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderRequestRecord in the database
        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderRequestRecord() throws Exception {
        // Initialize the database
        orderRequestRecordRepository.saveAndFlush(orderRequestRecord);
        int databaseSizeBeforeDelete = orderRequestRecordRepository.findAll().size();

        // Get the orderRequestRecord
        restOrderRequestRecordMockMvc.perform(delete("/api/order-request-records/{id}", orderRequestRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderRequestRecord> orderRequestRecordList = orderRequestRecordRepository.findAll();
        assertThat(orderRequestRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderRequestRecord.class);
        OrderRequestRecord orderRequestRecord1 = new OrderRequestRecord();
        orderRequestRecord1.setId(1L);
        OrderRequestRecord orderRequestRecord2 = new OrderRequestRecord();
        orderRequestRecord2.setId(orderRequestRecord1.getId());
        assertThat(orderRequestRecord1).isEqualTo(orderRequestRecord2);
        orderRequestRecord2.setId(2L);
        assertThat(orderRequestRecord1).isNotEqualTo(orderRequestRecord2);
        orderRequestRecord1.setId(null);
        assertThat(orderRequestRecord1).isNotEqualTo(orderRequestRecord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderRequestRecordDTO.class);
        OrderRequestRecordDTO orderRequestRecordDTO1 = new OrderRequestRecordDTO();
        orderRequestRecordDTO1.setId(1L);
        OrderRequestRecordDTO orderRequestRecordDTO2 = new OrderRequestRecordDTO();
        assertThat(orderRequestRecordDTO1).isNotEqualTo(orderRequestRecordDTO2);
        orderRequestRecordDTO2.setId(orderRequestRecordDTO1.getId());
        assertThat(orderRequestRecordDTO1).isEqualTo(orderRequestRecordDTO2);
        orderRequestRecordDTO2.setId(2L);
        assertThat(orderRequestRecordDTO1).isNotEqualTo(orderRequestRecordDTO2);
        orderRequestRecordDTO1.setId(null);
        assertThat(orderRequestRecordDTO1).isNotEqualTo(orderRequestRecordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderRequestRecordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderRequestRecordMapper.fromId(null)).isNull();
    }
}
