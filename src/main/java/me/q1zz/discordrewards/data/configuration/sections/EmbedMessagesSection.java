package me.q1zz.discordrewards.data.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Getter(AccessLevel.NONE)
public class EmbedMessagesSection extends OkaeriConfig {

    @Comment("EN: Wiadomość gdy użytkownik wpisze zły nick.")
    @Comment("EN: Message when user entered wrong nickname.")
    private MessageEmbed wrongNickname = new EmbedBuilder()
            .setTitle("ERROR!")
            .setDescription("{MENTION} > You entered the wrong nickname!")
            .setColor(Color.RED)
            .setThumbnail("https://i.imgur.com/ZvUyyic.png")
            .setFooter("An error occurred...", "https://i.imgur.com/ZvUyyic.png")
            .build();

    @Comment("PL: Wiadomość gdy gracz jest offline.")
    @Comment("EN: Message when player is offline.")
    private MessageEmbed playerOffline = new EmbedBuilder()
            .setTitle("ERROR!")
            .setDescription("{MENTION} > You must be online on the server!")
            .setColor(Color.RED)
            .setThumbnail("https://i.imgur.com/ZvUyyic.png")
            .setFooter("An error occurred...", "https://i.imgur.com/ZvUyyic.png")
            .build();

    @Comment("PL: Wiadomość gdy użytkownik już odebrał nagrodę.")
    @Comment("EN: Message when user already received reward.")
    private MessageEmbed alreadyReceived = new EmbedBuilder()
            .setTitle("ERROR!")
            .setDescription("{MENTION} > Reward already been received!")
            .setColor(Color.RED)
            .setThumbnail("https://i.imgur.com/ZvUyyic.png")
            .setFooter("An error occurred...", "https://i.imgur.com/ZvUyyic.png")
            .build();

    @Comment("PL: Wiadomość gdy nagroda została pomyślnie odebrana.")
    @Comment("EN: Message when reward has been successfully received.")
    private MessageEmbed successfullyReceived = new EmbedBuilder()
            .setTitle("SUCCESS!")
            .setDescription("{MENTION} > You successfully received reward to **{NICK}**!")
            .setColor(Color.GREEN)
            .setThumbnail("https://i.imgur.com/ax7t35s.png")
            .build();

    public MessageEmbed getWrongNickname(String mention) {
        return new EmbedBuilder(this.wrongNickname)
                .setDescription(this.wrongNickname.getDescription()
                    .replace("{MENTION}", mention))
                .build();
    }
    public MessageEmbed getPlayerOffline(String mention) {
        return new EmbedBuilder(this.playerOffline)
                .setDescription(this.playerOffline.getDescription()
                        .replace("{MENTION}", mention))
                .build();
    }
    public MessageEmbed getAlreadyReceived(String mention) {
        return new EmbedBuilder(this.alreadyReceived)
                .setDescription(this.alreadyReceived.getDescription()
                        .replace("{MENTION}", mention))
                .build();
    }
    public MessageEmbed getSuccessfullyReceived(String mention, String nick) {
        return new EmbedBuilder(this.successfullyReceived)
                .setDescription(this.successfullyReceived.getDescription()
                        .replace("{MENTION}", mention)
                        .replace("{NICK}", nick))
                .build();
    }
}
