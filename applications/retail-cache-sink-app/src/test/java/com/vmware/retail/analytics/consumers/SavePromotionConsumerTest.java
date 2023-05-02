/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavePromotionConsumerTest {


    @Mock
    private PromotionRepository repository;

    @Mock
    private RedisTemplate redisTemplate;

    private SavePromotionConsumer subject;
    private Promotion expected = JavaBeanGeneratorCreator.of(Promotion.class).create();

    @Test
    void given_Promotion_when_save_then_when_save_then_repository_saves() {


        subject = new SavePromotionConsumer(repository,redisTemplate);

        subject.accept(expected);

        verify(this.repository).save(any(Promotion.class));
        verify(this.redisTemplate).convertAndSend(anyString(),any(Promotion.class));
    }
}