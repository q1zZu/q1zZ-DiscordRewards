package me.q1zz.discordrewards.data.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.q1zz.discordrewards.helper.ItemHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class RewardSection extends OkaeriConfig {

    @Comment("PL: Komendy zostaną wykonane po odbiorze nagrody.")
    @Comment("EN: This commands will be executed when player receive reward.")
    private List<String> commands = Collections.singletonList("give {PLAYER} diamond 1");

    @Comment("PL: Te role zostaną dodane do użytkownika na discordzie po odbiorze nagrody.")
    @Comment("EN: This roles will be added to user on discord when receive reward.")
    private List<String> roles = Collections.singletonList("00000000000000000");

    @Comment("PL: Ogłoszenie gdy gracz odbierze nagrodę.")
    @Comment("EN: Broadcast when player receive reward.")
    private List<String> broadcast = Arrays.asList(
            " ",
            "&8» &fPlayer &9{PLAYER} &freceived the discord reward!",
            "&8» &fDiscord link: &9&nhttps://discord.com/xxxxxx",
            " "
    );

    @Comment("PL: Przedmioty które zostaną nadane po odbiorze nagrody.")
    @Comment("EN: Items that will be added to player when receive reward.")
    private List<ItemStack> items = Collections.singletonList(new ItemHelper(Material.EMERALD)
                    .name("&2&lEMERALD")
                    .lore(" ", "&aReward emerald!", " ")
                    .enchant(Enchantment.DURABILITY, 1)
                    .flag(ItemFlag.HIDE_ENCHANTS)
                    .build()
    );

}
