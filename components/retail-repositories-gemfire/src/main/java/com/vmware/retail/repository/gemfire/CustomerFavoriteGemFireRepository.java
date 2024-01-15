/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository.gemfire;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFavoriteGemFireRepository extends CustomerFavoriteRepository, GemfireRepository<CustomerFavorites,String> {
}
