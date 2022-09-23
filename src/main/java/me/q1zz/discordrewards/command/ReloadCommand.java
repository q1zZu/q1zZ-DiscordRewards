package me.q1zz.discordrewards.command;

import eu.okaeri.configs.exception.OkaeriException;
import lombok.RequiredArgsConstructor;
import me.q1zz.discordrewards.data.configuration.MessagesConfiguration;
import me.q1zz.discordrewards.data.configuration.PluginConfiguration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ReloadCommand implements CommandExecutor {

    private final PluginConfiguration pluginConfiguration;
    private final MessagesConfiguration messagesConfiguration;

    @Override
    public boolean onCommand(CommandSender sender,  @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender.hasPermission("discordRewards.reload")) {
            try {
                long startTime = System.currentTimeMillis();

                this.pluginConfiguration.load();
                this.messagesConfiguration.load();

                long reloadTime = (System.currentTimeMillis() - startTime);

                sender.sendMessage(ChatColor.GREEN + "Successfully reloaded in " + reloadTime + "ms!");
            } catch (OkaeriException exception) {
                sender.sendMessage(ChatColor.RED + "Error while reloading configs...\nCheck console to get more information!");
                exception.printStackTrace();
            }
        }
        return false;
    }
}
