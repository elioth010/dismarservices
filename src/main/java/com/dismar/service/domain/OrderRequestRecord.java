package com.dismar.service.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A OrderRequestRecord.
 */
@Entity
@Table(name = "order_request_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderRequestRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "sub_total", precision=10, scale=2, nullable = false)
    private BigDecimal subTotal;

    @ManyToOne
    private OrderRequest order;

    @ManyToOne
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderRequestRecord quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public OrderRequestRecord subTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public OrderRequest getOrder() {
        return order;
    }

    public OrderRequestRecord order(OrderRequest orderRequest) {
        this.order = orderRequest;
        return this;
    }

    public void setOrder(OrderRequest orderRequest) {
        this.order = orderRequest;
    }

    public Product getProduct() {
        return product;
    }

    public OrderRequestRecord product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderRequestRecord orderRequestRecord = (OrderRequestRecord) o;
        if (orderRequestRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderRequestRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderRequestRecord{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", subTotal=" + getSubTotal() +
            "}";
    }
}
