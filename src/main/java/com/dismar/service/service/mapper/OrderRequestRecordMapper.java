package com.dismar.service.service.mapper;

import com.dismar.service.domain.*;
import com.dismar.service.service.dto.OrderRequestRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderRequestRecord and its DTO OrderRequestRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderRequestMapper.class, ProductMapper.class})
public interface OrderRequestRecordMapper extends EntityMapper<OrderRequestRecordDTO, OrderRequestRecord> {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    OrderRequestRecordDTO toDto(OrderRequestRecord orderRequestRecord);

    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "productId", target = "product")
    OrderRequestRecord toEntity(OrderRequestRecordDTO orderRequestRecordDTO);

    default OrderRequestRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderRequestRecord orderRequestRecord = new OrderRequestRecord();
        orderRequestRecord.setId(id);
        return orderRequestRecord;
    }
}
