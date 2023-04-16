/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository;

import com.vmware.retail.domain.Product;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author Gregory Green
 */
@Repository
public interface ProductRepository extends KeyValueRepository<Product,String>
{
}
