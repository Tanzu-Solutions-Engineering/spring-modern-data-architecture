package spring.modern.data.architecture.valkey.console.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValKeyConsoleControllerTest {

    private ValKeyConsoleController subject;
    private String key = "test";
    private long start = 0;
    private long end = 0;

    @Mock
    private RedisTemplate<String,String> template;
    @Mock
    private ListOperations<String, String> listOps;
    @Mock
    private ValueOperations<String, String> valueOps;

    @BeforeEach
    void setUp() {
        subject = new ValKeyConsoleController(template);
    }


    //LRANGE 2 0 0
    @Test
    void lrange() {

        when(template.opsForList()).thenReturn(listOps);

        List<String> expected = List.of("test");
        when(listOps.range(anyString(),anyLong(),anyLong())).thenReturn(expected);

        List<String> actual = subject.lrange(key,start,end);
        assertThat(actual).isEqualTo(expected);
    }

    //DEL

    @Test
    void del() {
        String[] keys = {"k1"};

        when(template.delete(any(List.class))).thenReturn(Long.valueOf(keys.length));

        var actual = subject.del(keys);

        assertEquals(keys.length, actual);

    }
}