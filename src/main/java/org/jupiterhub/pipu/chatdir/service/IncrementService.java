package org.jupiterhub.pipu.chatdir.service;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class IncrementService {

    private ReactiveKeyCommands<String> keyCommands;
    private ValueCommands<String, Long> countCommands;

    public IncrementService(RedisDataSource dataSource, ReactiveRedisDataSource reactiveDs) {
        this.keyCommands = reactiveDs.key();
        this.countCommands = dataSource.value(Long.class);
    }

    public long get(String key) {
        Long value = countCommands.get(key);
        if (value == null) {
            return 0L;
        }

        return value;
    }

    public void set(String key, Long value) {
        countCommands.set(key, value);
    }

    public void increment(String key, Long incrementBy) {
        countCommands.incrby(key, incrementBy);
    }

    public Uni<Void> del(String key) {
        return keyCommands.del(key).replaceWithVoid();
    }

    public Uni<List<String>> keys() {
        return keyCommands.keys("*");
    }

}
