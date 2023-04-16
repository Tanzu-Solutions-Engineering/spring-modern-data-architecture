/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductQuantityTest
{

    @Test
    void given_o2_null_when_compareTo_then_return_Greater()
    {
        Product pA = new Product("1L","name");
        var o1 = new ProductQuantity(pA,20);
        assertEquals(o1.compareTo(null),1);
    }

    @Test
    void given_o1_quantityGreater_when_compareTo_then_return_Greater()
    {
        Product pA = new Product("1L","name");
        Product pB = new Product("2L","name");
        var o1 = new ProductQuantity(pA,20);
        var o2 = new ProductQuantity(pB,1);


        assertEquals(o1.compareTo(o2),1);
        assertEquals(o2.compareTo(o1),-1);
    }

    @Test
    void given_queues_when_compareTo_then_return_0()
    {
        Product pA = new Product("1L","name");
        Product pB = new Product("1L","name");
        var o1 = new ProductQuantity(pA,1);
        var o2 = new ProductQuantity(pB,1);

        assertEquals(o1.compareTo(o2),0);
    }

    @Test
    void given_quantityEqual_ButProductNameDifferent_when_compareTo_then_return_BasedOnProductName()
    {
        Product pA = new Product("1L","name2");
        Product pB = new Product("2L","name1");
        var o1 = new ProductQuantity(pA,1);
        var o2 = new ProductQuantity(pB,1);


        assertEquals(o1.compareTo(o2),1);
    }
}