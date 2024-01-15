/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.caching.consumers;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavePromotionConsumerTest {


    @Mock
    private PromotionRepository repository;


    private SavePromotionConsumer subject;
    private Promotion expected = JavaBeanGeneratorCreator.of(Promotion.class).create();

    @Test
    void given_Promotion_when_save_then_when_save_then_repository_saves() {


        subject = new SavePromotionConsumer(repository);

        subject.accept(expected);

        verify(this.repository).save(any(Promotion.class));
    }
}