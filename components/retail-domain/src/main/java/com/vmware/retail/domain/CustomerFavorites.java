package com.vmware.retail.domain;

import java.util.SortedSet;

public record CustomerFavorites(String id, SortedSet<ProductQuantity> favorites)
{
}
