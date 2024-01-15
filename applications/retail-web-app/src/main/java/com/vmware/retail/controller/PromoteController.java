/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.controller;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("promotions")
public record PromoteController(PromotionRepository repository)
{

    private static final String channel = "default";

    @PostMapping("promotion")
    public void savePromotion(@RequestBody Promotion promotion)
    {
        repository.save(promotion);
    }

    @PostMapping("promotion/publish")
    public void publishPromotion(@RequestBody Promotion promotion)
    {
        savePromotion(promotion);
    }


    @GetMapping("promotion/{id}")
    public Promotion getPromotion(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }
}
