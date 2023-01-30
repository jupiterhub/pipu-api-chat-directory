package org.jupiterhub.pipu.chatdir.service;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.mutiny.Uni;
import org.jupiterhub.pipu.chatdir.record.Directory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DirectoryService {

    private ReactiveKeyCommands<String> keyCommands;
    private ValueCommands<String, Directory> countCommands;

    public DirectoryService(RedisDataSource dataSource, ReactiveRedisDataSource reactiveDs) {
        this.keyCommands = reactiveDs.key();
        this.countCommands = dataSource.value(Directory.class);
    }


    public Optional<Directory> get(String key) {
        Directory value = countCommands.get(key);
        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public void set(String key, Directory directory) {
        countCommands.set(key, directory);
    }

    public Uni<Void> del(String key) {
        return keyCommands.del(key).replaceWithVoid();
    }



    public void increment(String key, Long incrementBy) {
        countCommands.incrby(key, incrementBy);
    }

    public Uni<List<String>> keys() {
        return keyCommands.keys("*");
    }

}
