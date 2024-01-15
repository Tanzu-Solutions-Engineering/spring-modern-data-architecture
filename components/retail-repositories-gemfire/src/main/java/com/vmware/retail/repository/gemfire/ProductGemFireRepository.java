/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository.gemfire;

import com.vmware.retail.domain.Product;
import com.vmware.retail.repository.ProductRepository;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGemFireRepository extends ProductRepository, GemfireRepository<Product,String> {
}
