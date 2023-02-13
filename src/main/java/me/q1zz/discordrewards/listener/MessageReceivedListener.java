package me.q1zz.discordrewards.listener;

import lombok.RequiredArgsConstructor;
import me.q1zz.discordrewards.DiscordRewardsPlugin;
import me.q1zz.discordrewards.data.configuration.MessagesConfiguration;
import me.q1zz.discordrewards.data.configuration.PluginConfiguration;
import me.q1zz.discordrewards.helper.ItemBuilder;
import me.q1zz.discordrewards.helper.MessageHelper;
import me.q1zz.discordrewards.user.User;
import me.q1zz.discordrewards.user.UserManager;
import me.q1zz.discordrewards.helper.RoleHelper;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class MessageReceivedListener extends ListenerAdapter {

    private final PluginConfiguration pluginConfiguration;
    private final MessagesConfiguration messagesConfiguration;
    private final UserManager userManager;
    private final Logger logger;
    private final DiscordRewardsPlugin plugin;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.isWebhookMessage() || event.getAuthor().isBot()) {
            return;
        }

        if (!event.isFromGuild() || !event.isFromType(ChannelType.TEXT)) {
            return;
        }

        final TextChannel textChannel = event.getChannel().asTextChannel();

        if (!textChannel.getId().equals(this.pluginConfiguration.getSettings().getDiscordChannelID())) {
            return;
        }

        final Member member = event.getMember();

        Objects.requireNonNull(member, "Member cannot be null!");

        final Message message = event.getMessage();
        final String rawMessage = message.getContentRaw();

        if (!rawMessage.matches(this.pluginConfiguration.getSettings().getNicknameRegex())) {

            message.replyEmbeds(this.messagesConfiguration.getEmbeds().getWrongNickname(member.getAsMention())).queue();

        } else {
            final Player player = Bukkit.getPlayer(rawMessage);

            if (player == null) {

                message.replyEmbeds(this.messagesConfiguration.getEmbeds().getPlayerOffline(member.getAsMention())).queue();

            } else {
                final User user = this.userManager.getUser(player.getUniqueId(), member.getIdLong());

                if (user != null) {

                    message.replyEmbeds(this.messagesConfiguration.getEmbeds().getAlreadyReceived(member.getAsMention())).queue();

                } else {
                    this.userManager.createUser(new User(player.getUniqueId(), event.getAuthor().getIdLong())).thenRunAsync(() -> {

                        this.pluginConfiguration.getRewards().getCommands().forEach(command -> Bukkit.getScheduler().runTask(this.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{PLAYER}", player.getName()))));

                        this.pluginConfiguration.getRewards().getItems().forEach(item -> Bukkit.getScheduler().runTask(this.plugin, () -> new ItemBuilder(item, player).refreshLore().addItem(player)));

                        this.pluginConfiguration.getRewards().getBroadcast().forEach(broadcast -> Bukkit.broadcastMessage(MessageHelper.fixWithPlaceholders(player, broadcast.replace("{PLAYER}", player.getName()))));

                        RoleHelper.addRoles(event.getGuild(), member, this.pluginConfiguration.getRewards().getRoles(), this.logger);

                        message.replyEmbeds(this.messagesConfiguration.getEmbeds().getSuccessfullyReceived(member.getAsMention(), player.getName())).queue();
                    });
                }
            }
        }
    }
}
