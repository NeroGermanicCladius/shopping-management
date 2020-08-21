package com.example.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "tb_rating",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}, name = "tb_rating_id_pk"),
        },
        indexes = {
                @Index(columnList = "id", name = "tb_rating_id_pk", unique = true),
                @Index(columnList = "user_id", name = "tb_table_user_id_index"),
                @Index(columnList = "product_id", name = "tb_table_product_id_index")
        }
)
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
    private Long id;

    @Column(name = "rate", nullable = false)
    private Integer rate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_rating_user_id_fk")
    )
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_rating_product_id_fk")
    )
    private Product productId;

    public Rating() {
        this(null, null, null, null);
    }

    public Rating(final Long id, final Integer rate, final User userId, final Product productId) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating product = (Rating) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(rate, product.rate) &&
                Objects.equals(userId, product.userId) &&
                Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, userId, productId);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rate='" + rate + '\'' +
                ", userId=" + userId + '\'' +
                ", productId=" + productId + '\'' +
                '}';
    }
}
