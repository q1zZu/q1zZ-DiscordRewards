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

    public User getUser(UUID uniqueID, long discordAccountID) {
        User user = this.usersCache.asMap().values().stream().filter(filterUser -> filterUser.getUniqueID().equals(uniqueID) || filterUser.getDiscordAccountID() == discordAccountID).findFirst().orElse(null);
        if(user == null) {
            user = this.database.loadData(uniqueID, discordAccountID);
            if(user != null) this.usersCache.put(uniqueID, user);
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
