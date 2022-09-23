package me.q1zz.discordrewards.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import me.q1zz.discordrewards.data.configuration.sections.BotSection;
import me.q1zz.discordrewards.data.configuration.sections.DatabaseSection;
import me.q1zz.discordrewards.data.configuration.sections.RewardSection;
import me.q1zz.discordrewards.data.configuration.sections.SettingsSection;

@Getter
public class PluginConfiguration extends OkaeriConfig {

    @Comment("PL: Konfiguracja połączenia bazy danych mysql.")
    @Comment("EN: Mysql database connection configuration.")
    private DatabaseSection database = new DatabaseSection();

    @Comment("PL: Konfiguracja bota discord.")
    @Comment("EN: Discord bot configuration.")
    private BotSection bot = new BotSection();

    @Comment("PL: Ustawienia pluginu")
    @Comment("EN: Plugin settings")
    private SettingsSection settings = new SettingsSection();

    @Comment("PL: Ustawienia nagród.")
    @Comment("EN: Rewards settings.")
    private RewardSection rewards = new RewardSection();

}
