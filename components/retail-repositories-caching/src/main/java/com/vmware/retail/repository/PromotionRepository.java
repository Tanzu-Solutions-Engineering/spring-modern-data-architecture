/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository;

import com.vmware.retail.domain.Promotion;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * PromotionRepository
 *
 * @author Gregory Green
 */

    public interface PromotionRepository
        extends KeyValueRepository<Promotion,String>
    {
    }

