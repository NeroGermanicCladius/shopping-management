package com.example.model.domain;

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
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_rating_product_id_fk")
    )
    private Product product;

    public Rating() {
        this(null, null, null, null);
    }

    public Rating(final Long id, final Integer rate, final User user, final Product product) {
        this.id = id;
        this.rate = rate;
        this.user = user;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating product = (Rating) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(rate, product.rate) &&
                Objects.equals(user, product.user) &&
                Objects.equals(product, product.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, user, product);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rate='" + rate + '\'' +
                ", userId=" + user + '\'' +
                ", productId=" + product + '\'' +
                '}';
    }
}
