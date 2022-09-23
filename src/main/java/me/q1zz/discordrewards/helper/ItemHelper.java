package me.q1zz.discordrewards.helper;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemHelper {

    private final ItemStack itemStack;

    public ItemHelper(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemHelper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemHelper name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemHelper lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemHelper lore(String... lore) {
        this.lore(Arrays.asList(lore));
        return this;
    }

    public ItemHelper enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemHelper flag(ItemFlag... flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public void addItem(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), this.itemStack);
        } else
            player.getInventory().addItem(this.itemStack);
    }

    public ItemStack get() {
        return this.itemStack;
    }
    public ItemStack build() {
        return this.get();
    }
}
