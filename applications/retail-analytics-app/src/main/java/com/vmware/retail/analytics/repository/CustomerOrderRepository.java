/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.repository;

import com.vmware.retail.analytics.entity.CustomerOrderEntity;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface CustomerOrderRepository
            extends CrudRepository<CustomerOrderEntity,Long> {
    }

