package com.vmware.retail.repository;

import com.vmware.retail.domain.CustomerFavorites;
import lombok.AllArgsConstructor;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * CustomerFavoriteSortedSetRepository
 *
 * @author Gregory Green
 */
@Repository
public interface CustomerFavoriteRepository extends KeyValueRepository<CustomerFavorites,String>
{

//    private final ReactiveRedisTemplate<String,CustomerFavorites> reactiveRedisTemplate;
//    private final RedisTemplate redisTemplate;
//
//    public Mono<CustomerFavorites> findById(String id){
//        return reactiveRedisTemplate.opsForValue().get(id);
//    }
//
//    public CustomerFavorites findCustomerFavoriteById(String id){
//        return reactiveRedisTemplate.
//    }
//
//    public void saveCustomerFavorites(CustomerFavorites customerFavorites)
//    {
//        redisTemplate.opsForValue().set(customerFavorites.id(),customerFavorites);
//    }
}
