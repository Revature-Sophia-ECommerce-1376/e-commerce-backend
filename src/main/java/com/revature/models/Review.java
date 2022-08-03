package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import java.sql.Timestamp;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "product" })
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;

	@BusinessKey
    @NotNull
    @NonNull
    @Min(value = 0)
    @Max(value = 5)
    private int stars;

	@BusinessKey
    @NotNull
    @NonNull
    private String title;

	@BusinessKey
    @NotNull
    @NonNull
    private String reviewMessage;

    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    private Timestamp posted;

    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    private Timestamp updated;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @NonNull
    @ToString.Exclude
    private Product product;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @NonNull
    @ToString.Exclude
    private User user;

    public Review(@NotBlank int stars, @Length(max = 100) String title, @Length(max = 400) String review, User user,
            Product product) {
        super();
        this.stars = stars;
        this.title = title;
        this.reviewMessage = review;
        this.user = user;
        this.product = product;
    }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
