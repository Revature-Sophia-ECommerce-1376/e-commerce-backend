package com.revature.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
<<<<<<< HEAD
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "product", "ownerUser" })
=======
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "ownerUser" })
>>>>>>> 541f396f65da567367daf5f3294d67d15607649f
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int id;

    @CreationTimestamp
    @Column(name = "order_placed")
    @EqualsAndHashCode.Exclude
    private Timestamp orderPlaced;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @ToString.Exclude
    private Product product;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @ToString.Exclude
    private User ownerUser;

	@BusinessKey
    @Column(name = "quantity")
    private int quantity;

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
