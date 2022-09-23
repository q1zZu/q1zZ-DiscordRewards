package me.q1zz.discordrewards.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import me.q1zz.discordrewards.data.configuration.sections.EmbedMessagesSection;

@Getter
public class MessagesConfiguration extends OkaeriConfig {

    @Comment("PL: Konfiguracja wiadomości embed.")
    @Comment("EN: Embed messages configuration.")
    private EmbedMessagesSection embeds = new EmbedMessagesSection();

}
