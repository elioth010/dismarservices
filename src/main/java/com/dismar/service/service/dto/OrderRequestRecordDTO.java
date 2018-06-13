package com.dismar.service.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrderRequestRecord entity.
 */
public class OrderRequestRecordDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal subTotal;

    private Long orderId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderRequestId) {
        this.orderId = orderRequestId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderRequestRecordDTO orderRequestRecordDTO = (OrderRequestRecordDTO) o;
        if(orderRequestRecordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderRequestRecordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderRequestRecordDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", subTotal=" + getSubTotal() +
            "}";
    }
}
