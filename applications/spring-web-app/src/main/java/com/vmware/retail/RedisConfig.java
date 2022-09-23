package com.vmware.retail;

import com.vmware.retail.domain.Promotion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.function.Consumer;

/**
 * RedisConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig
{
    @Value("${retail.promotion.listener.pattern.topic}")
    private String patternTopic;

    @Bean
    public TaskExecutor taskExecutor()
    {
        return new ThreadPoolTaskExecutor();
    }


    @Bean
    public RedisSerializer redisSerializer()
    {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer redisSerializer)
    {
        var template = new RedisTemplate();
        template.setDefaultSerializer(redisSerializer);
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(Consumer<Promotion> listener,
                                                                       RedisConnectionFactory connectionFactory,
                                                                       RedisSerializer redisSerializer)
    {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        MessageListenerAdapter messageListener = new MessageListenerAdapter(listener);
        messageListener.setDefaultListenerMethod("accept");
        messageListener.setSerializer(redisSerializer);
        messageListener.afterPropertiesSet();
        container.addMessageListener(messageListener,PatternTopic.of(this.patternTopic));
        return container;
    }

}
