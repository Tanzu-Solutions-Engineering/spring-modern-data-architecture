/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.SortedSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFavorites
{
    private String id;
    private SortedSet<ProductQuantity> favorites;
}
