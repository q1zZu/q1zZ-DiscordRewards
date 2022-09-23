package me.q1zz.discordrewards.data.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BotSection extends OkaeriConfig {

    @Comment("PL: Token bota z strony https://discord.com/developers/applications")
    @Comment("EN: Bot token from https://discord.com/developers/applications")
    private String token = "your_bot_token!";

    @Comment("PL: Status bota")
    @Comment("EN: Bot status")
    private String status = "q1zZ-DiscordRewards v0.5";

}
