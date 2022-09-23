package me.q1zz.discordrewards.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private final UUID uniqueID;
    private long discordAccountID;

}
