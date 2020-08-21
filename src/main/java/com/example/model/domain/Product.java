package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(
        name = "tb_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}, name = "tb_product_id_pk"),
                @UniqueConstraint(columnNames = {"name"}, name = "tb_product_name_uc")
        },
        indexes = {
                @Index(columnList = "id", name = "tb_product_id_pk", unique = true),
                @Index(columnList = "name", name = "tb_product_name_uc", unique = true)
        }
)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "overview", nullable = false)
    private String overview;

    public Product() {
        this(null, null, null, null);
    }

    public Product(final Long id, final String name, final BigDecimal price, final String overview) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.overview = overview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(overview, product.overview);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, overview);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price + '\'' +
                ", overview=" + overview + '\'' +
                '}';
    }
}
