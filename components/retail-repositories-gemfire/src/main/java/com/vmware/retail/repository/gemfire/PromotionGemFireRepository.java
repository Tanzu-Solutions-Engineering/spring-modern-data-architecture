/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository.gemfire;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionGemFireRepository extends PromotionRepository, GemfireRepository<Promotion,String> {
}
