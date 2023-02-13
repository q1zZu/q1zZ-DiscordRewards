package me.q1zz.discordrewards.user;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import me.q1zz.discordrewards.data.database.Database;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class UserManager {

    private final Database database;

    private final Cache<UUID, User> usersCache = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();

    private User getCachedUser(UUID uniqueId, long discordAccountId) {
        if(this.usersCache.asMap().containsKey(uniqueId)) {
            return this.usersCache.getIfPresent(uniqueId);
        }
        return this.usersCache.asMap().values().stream().filter(user -> user.getDiscordAccountID() == discordAccountId).findFirst().orElse(null);
    }

    private void putToCache(User user) {
        if(user == null) {
            return;
        }
        this.usersCache.put(user.getUniqueID(), user);
    }

    public User getUser(UUID uniqueID, long discordAccountId) {
        User user = this.getCachedUser(uniqueID, discordAccountId);
        if(user == null) {
            user = this.database.loadData(uniqueID, discordAccountId);
            this.putToCache(user);
        }
        return user;
    }

    public CompletableFuture<Void> createUser(User user) {
        return CompletableFuture.runAsync(() -> {
            this.usersCache.put(user.getUniqueID(), user);
            this.database.saveData(user);
        });
    }

}
