package me.q1zz.discordrewards.helper;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class MessageHelper {

    private final static Pattern HEX_REGEX = Pattern.compile("#[a-fA-F0-9]{6}");

    public String fixColor(String message) {
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

    public String fixWithPlaceholders(Player player, String message) {
        return fixColor(includePlaceholders(message, player));
    }

    private String includePlaceholders(String message, Player player) {
        if(player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

}
