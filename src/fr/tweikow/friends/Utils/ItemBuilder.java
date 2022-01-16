package fr.tweikow.friends.Utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    public static ItemStack create(Material material, int amount, String name, String... lores) {
        if (material == null) {
            return null;
        } else {
            ItemStack itemStack = new ItemStack(material, amount);

            if (name == null && (lores == null || lores.length < 1)) {
                return itemStack;
            } else {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (name != null) {
                    itemMeta.setDisplayName(name);
                }

                if (lores != null) {
                    itemMeta.setLore(Arrays.asList(lores));
                }

                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }
        }
    }
    public static ItemStack withoutCreate(Material material, int amount, String name, Enchantment enchantment, int level, Boolean hideEnchant, String... lores) {
        ItemStack item;
        ItemMeta meta;
        if (material == null)
            return null;
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lores != null)
            meta.setLore(Arrays.asList(lores));
        meta.addEnchant(enchantment, level, true);
        if (hideEnchant == true)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack withoutCreate(Material material, int amount, String name, Enchantment[] enchantments, int[] enchantmentLevels, Boolean hideEnchant, String... lores) {
        ItemStack item;
        ItemMeta meta;
        if (material == null)
            return null;
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lores != null)
            meta.setLore(Arrays.asList(lores));
        if (enchantments != null && enchantmentLevels != null && enchantments.length == enchantmentLevels.length) {
            for (int i = 0; i < enchantments.length; i++)
                item.addEnchantment(enchantments[i], enchantmentLevels[i]);
        }
        if (hideEnchant == true)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}