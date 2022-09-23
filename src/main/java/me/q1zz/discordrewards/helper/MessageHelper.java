package me.q1zz.discordrewards.helper;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class MessageHelper {

    private static final Pattern HEX_REGEX = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String fixColor(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }
        for (Matcher matcher = HEX_REGEX.matcher(message); matcher.find(); matcher = HEX_REGEX.matcher(message)) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
        }
        return ChatColor.translateAlternateColorCodes('&', message
                .replace("<<", "«")
                .replace(">>", "»"));
    }

}
