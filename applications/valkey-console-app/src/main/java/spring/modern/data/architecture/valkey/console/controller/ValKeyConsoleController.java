package spring.modern.data.architecture.valkey.console.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("valKey")
public class ValKeyConsoleController {
    private final RedisTemplate<String, String> template;

    @GetMapping("lrange")
    public List<String> lrange(String key, long start, long end) {
        return template.opsForList().range(key,start,end);
    }


    @DeleteMapping("del")
    public long del(String... keys) {
        return template.delete(Arrays.asList(keys));
    }
}
