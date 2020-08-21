package com.example.domain;

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
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "tb_comment_product_id_fk")
    )
    private Product productId;

    public Comment() {
        this(null, null, null, null);
    }

    public Comment(final Long id, final String commentText, final User userId, final Product productId) {
        this.id = id;
        this.commentText = commentText;
        this.userId = userId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment product = (Comment) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(commentText, product.commentText) &&
                Objects.equals(userId, product.userId) &&
                Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, userId, productId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", userId=" + userId + '\'' +
                ", productId=" + productId + '\'' +
                '}';
    }
}
