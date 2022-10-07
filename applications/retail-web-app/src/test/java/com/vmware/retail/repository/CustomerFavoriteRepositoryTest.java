//package com.vmware.retail.repository;
//
//import com.vmware.retail.domain.CustomerFavorites;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.core.ReactiveValueOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import reactor.core.publisher.Mono;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CustomerFavoriteRepositoryTest
//{
//    @Mock
//    private ReactiveRedisTemplate<String, CustomerFavorites> reactiveRedisTemplate;
//
//    @Mock
//    private RedisTemplate redisTemplate;
//
//    @Mock
//    private ReactiveValueOperations<String, CustomerFavorites> opsForValue;
//
//    private CustomerFavorites expectCustomerFavorites = new CustomerFavorites("id",null);
//
//    @Mock
//    private ValueOperations valueOperations;
//
//    private CustomerFavoriteRepository subject;
//
//    @BeforeEach
//    void setUp()
//    {
//        subject = new CustomerFavoriteRepository(reactiveRedisTemplate,redisTemplate);
//    }
//
//    @Test
//    void given_id_when_findById_Then_Return_expected()
//    {
//        Mono<CustomerFavorites> expected = mock(Mono.class);
//        when(expected.block()).thenReturn(expectCustomerFavorites);
//
//        when(reactiveRedisTemplate.opsForValue()).thenReturn(opsForValue);
//        when(opsForValue.get(anyString())).thenReturn(expected);
//
//        assertEquals(expectCustomerFavorites,subject.findById(expectCustomerFavorites.id()).block());
//    }
//
//    @Test
//    void given_customerFavorites_when_save_then_saveOnReo()
//    {
//        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//
//        subject.saveCustomerFavorites(expectCustomerFavorites);
//        verify(redisTemplate).opsForValue();
//    }
//}