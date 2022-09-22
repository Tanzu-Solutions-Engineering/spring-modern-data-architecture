package com.vmware.springwebapp.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Product")
public record Product(String id, String name)
{
}
