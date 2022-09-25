package com.vmware.retail.controller;

import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import nyla.solutions.core.util.Organizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static nyla.solutions.core.util.Organizer.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoteControllerTest
{
    @Mock
    private PromotionRepository repository;
    @Mock
    private RedisTemplate<String, Promotion> template;
    private Promotion expected = new Promotion("1",
            "new stuff",
            toList(new Product("productId","productName")));

    @Test
    void given_promote_When_savePromotion_Then_getPromote_returns_Saved()
    {
        when(repository.findById(anyString())).thenReturn(Optional.of(expected));
        System.out.println(expected);
        var subject = new PromoteController(repository,template);

        subject.savePromotion(expected);
        assertEquals(expected,subject.getPromotion(expected.id()));
        verify(repository).findById(anyString());
    }
}