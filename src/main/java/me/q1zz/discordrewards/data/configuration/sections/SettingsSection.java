package me.q1zz.discordrewards.data.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SettingsSection extends OkaeriConfig {

    @Comment("PL: Regex dla nicków minecraft do sprawdzania poprawności nicku.")
    @Comment("EN: Minecraft nickname regex for checking if nickname is valid.")
    @CustomKey("nickname-regex")
    private String nicknameRegex = "[a-zA-Z0-9_]+";

    @Comment("PL: Id kanału z discorda do odbierania nagrody.")
    @Comment("EN: Id of discord channel for receiving rewards.")
    private String discordChannelID = "00000000000000000";

}
