package me.q1zz.discordrewards;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.postprocessor.SectionSeparator;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import me.q1zz.discordrewards.bStats.Metrics;
import me.q1zz.discordrewards.command.ReloadCommand;
import me.q1zz.discordrewards.data.configuration.MessagesConfiguration;
import me.q1zz.discordrewards.data.configuration.PluginConfiguration;
import me.q1zz.discordrewards.data.configuration.serializers.EmbedFooterSerializer;
import me.q1zz.discordrewards.data.configuration.serializers.EmbedMessageSerializer;
import me.q1zz.discordrewards.data.database.Database;
import me.q1zz.discordrewards.discord.BotManager;
import me.q1zz.discordrewards.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DiscordRewardsPlugin extends JavaPlugin {

    private PluginConfiguration pluginConfiguration;
    private MessagesConfiguration messagesConfiguration;
    private UserManager userManager;
    private Database database;
    private BotManager botManager;

    @Override
    public void onEnable() {

        this.initConfigs();

        this.database = new Database(this.pluginConfiguration, this.getLogger());
        this.database.connect();

        this.userManager = new UserManager(this.database);

        this.botManager = new BotManager(this.pluginConfiguration.getBot().getToken());
        this.botManager.setStatus(this.pluginConfiguration.getBot().getStatus());
        this.botManager.start(this.pluginConfiguration, this.messagesConfiguration, this.userManager, this.getLogger(), this);

        this.registerCommands();

        new Metrics(this, 13115);
    }

    @Override
    public void onDisable() {
        if(this.botManager != null) this.botManager.stopBot();
        if(this.database != null) this.database.closeConnection();
    }

    private void registerCommands() {
        this.getServer().getPluginCommand("dcReload").setExecutor(new ReloadCommand(this.pluginConfiguration, this.messagesConfiguration));
    }

    private void initConfigs() {
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(SectionSeparator.NEW_LINE), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "configuration.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        this.messagesConfiguration = ConfigManager.create(MessagesConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(SectionSeparator.NEW_LINE), registry -> {
                registry.register(new EmbedFooterSerializer());
                registry.register(new EmbedMessageSerializer());
            });
            it.withBindFile(new File(this.getDataFolder(), "messages.yml"));
            it.saveDefaults();
            it.withRemoveOrphans(true);
            it.load(true);
        });
    }
}
