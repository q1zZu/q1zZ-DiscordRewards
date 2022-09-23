package me.q1zz.discordrewards.discord;

import me.q1zz.discordrewards.DiscordRewardsPlugin;
import me.q1zz.discordrewards.data.configuration.MessagesConfiguration;
import me.q1zz.discordrewards.data.configuration.PluginConfiguration;
import me.q1zz.discordrewards.listener.MessageReceivedListener;
import me.q1zz.discordrewards.user.UserManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

public class BotManager {

    private JDA jda;
    private final String token;
    private String status;

    public BotManager(String token) {
        this.token = token;
    }

    public void start(PluginConfiguration pluginConfiguration, MessagesConfiguration messagesConfiguration, UserManager userManager, Logger logger, DiscordRewardsPlugin discordRewardsPlugin) {
        try {
            JDABuilder builder = JDABuilder.createDefault(token);

            Message.suppressContentIntentWarning();

            this.jda = builder.build();

            this.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(Activity.ActivityType.PLAYING, status == null || status.isEmpty() ? "Created by q1zZ" : status));
            this.jda.setAutoReconnect(true);

            this.registerListeners(pluginConfiguration, messagesConfiguration, userManager, logger, discordRewardsPlugin);

            this.jda.awaitReady();

            logger.info("Discord bot was successfully started!");
        } catch (InterruptedException e) {
            logger.severe("Error while starting discord bot...");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(discordRewardsPlugin);
        }
    }

    public void stopBot() {
        if(this.jda != null) jda.shutdownNow();
    }

    private void registerListeners(PluginConfiguration pluginConfiguration, MessagesConfiguration messagesConfiguration, UserManager userManager, Logger logger, DiscordRewardsPlugin discordRewardsPlugin) {
        this.jda.addEventListener(new MessageReceivedListener(pluginConfiguration, messagesConfiguration, userManager, logger, discordRewardsPlugin));
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
