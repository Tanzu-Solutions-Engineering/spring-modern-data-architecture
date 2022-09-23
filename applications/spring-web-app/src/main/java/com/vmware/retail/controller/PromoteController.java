package com.vmware.retail.controller;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("promotions")
public record PromoteController(PromotionRepository repository,
                                RedisTemplate<String,Promotion> redisTemplate)
{

    @PostMapping("promotion")
    public void savePromotion(@RequestBody Promotion promotion)
    {
        repository.save(promotion);
    }

    @PostMapping("promotion/publish")
    public void publishPromotion(@RequestBody Promotion promotion)
    {
        redisTemplate.convertAndSend("hello",promotion);
    }


    @GetMapping("promotion/{id}")
    public Promotion getPromotion(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }
}
