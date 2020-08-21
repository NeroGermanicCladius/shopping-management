package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "tb_comment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}, name = "tb_comment_id_pk"),
        },
        indexes = {
                @Index(columnList = "id", name = "tb_comment_id_pk", unique = true)
        }
)
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_comment_user_id_fk")
    )
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_comment_product_id_fk")
    )
    private Product product;

    public Comment() {
        this(null, null, null, null);
    }

    public Comment(final Long id, final String commentText, final User user, final Product product) {
        this.id = id;
        this.commentText = commentText;
        this.user = user;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment product = (Comment) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(commentText, product.commentText) &&
                Objects.equals(user, product.user) &&
                Objects.equals(product, product.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, user, product);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", userId=" + user + '\'' +
                ", productId=" + product + '\'' +
                '}';
    }
}
