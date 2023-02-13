package me.q1zz.discordrewards.helper;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final Player player;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.player = null;
    }

    public ItemBuilder(ItemStack itemStack, Player player) {
        this.itemStack = itemStack;
        this.player = player;
    }

    public ItemBuilder name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageHelper.fixWithPlaceholders(this.player, name));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore.stream().map(line -> MessageHelper.fixWithPlaceholders(this.player, line)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder refreshLore() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if(itemMeta == null) return this;
        if(!itemMeta.hasLore()) return this;
        itemMeta.setLore(itemMeta.getLore().stream().map(line -> MessageHelper.fixWithPlaceholders(this.player, line)).collect(Collectors.toList()));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        this.lore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder flag(ItemFlag... flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public void addItem(Player player) {
        try {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), this.itemStack);
            } else
                player.getInventory().addItem(this.itemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack get() {
        return this.itemStack;
    }
    public ItemStack build() {
        return this.get();
    }
}
