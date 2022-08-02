package com.revature.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @BusinessKey
    private int quantity;
    @NotNull
    @BusinessKey
    private double price;
    @NotNull
    @BusinessKey
    private String description;
    @NotNull
    @BusinessKey
    private String image;
    @NotNull
    @BusinessKey
    private String name;
    
    @Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
