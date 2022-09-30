package com.vmware.retail.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.SortedSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFavorites
{
    private String id;
    private SortedSet<ProductQuantity> favorites;
}
