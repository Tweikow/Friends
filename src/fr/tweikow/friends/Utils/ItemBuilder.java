package fr.tweikow.friends.Utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class ItemBuilder {
    ItemBuilder items;
    public ItemBuilder(ItemBuilder itemBuilder) {
        this.items=itemBuilder;
    }

    public ItemStack ItemBuilder(Material material, int amount, short damage, byte data, Enchantment[] enchantments,
                                 int[] enchantmentLevels, String name, String... lores) {
        return create(material, amount, damage, data, enchantments, enchantmentLevels, name, lores);
    }
    public static ItemStack skull(Material material, int amount, short damage, String name, String... lores){
        ItemStack itemStack;
        ItemMeta itemMeta;
        if (material == null || name == null || lores == null) return null;

        itemStack = new ItemStack(material, amount, damage);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lores));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack create(Material material, int amount, short damage, byte data, Enchantment[] enchantments,
                                   int[] enchantmentLevels, String name, String... lores) {
        ItemStack itemStack;
        ItemMeta itemMeta;

        if (material == null)
            return null;
        itemStack = new ItemStack(material, amount, damage, data);
        if (enchantments != null && enchantmentLevels != null && enchantments.length == enchantmentLevels.length) {
            for (int i = 0; i < enchantments.length; i++)
                itemStack.addEnchantment(enchantments[i], enchantmentLevels[i]);
        }
        if (name == null && (lores == null || lores.length < 1))
            return itemStack;
        itemMeta = itemStack.getItemMeta();
        if (name != null)
            itemMeta.setDisplayName(name);
        if (lores != null)
            itemMeta.setLore(Arrays.asList(lores));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, int amount, String name, String... lores) {
        return create(material, amount, (short) 0, (byte) 0, null, null, name, lores);
    }

    public static ItemStack create(Material material, int amount, int data, String name, String... lores) {
        return create(material, amount, (short) 0, (byte) data, null,null, name, lores);
    }

    public static ItemStack createWithoutLores(Material material, int amount, short damage, byte data, String name) {
        return create(material, amount, (short) 0, data, null, null, name, new String[0]);
    }

    public static ItemStack createWithoutLores(Material material, int amount, byte data, String name) {
        return createWithoutLores(material, amount, (short) 0, data, name);
    }

    public static ItemStack createWithoutLores(Material material, int amount, short damage, String name) {
        return createWithoutLores(material, amount, damage, (byte) 0, name);
    }

    public static ItemStack createWithoutLores(Material material, int amount, byte data) {
        return createWithoutLores(material, amount, (short) 0, data, null);
    }

    public static ItemStack createWithoutLores(Material material, int amount, short damage) {
        return createWithoutLores(material, amount, damage, (byte) 0, null);
    }

    public static ItemStack createWithoutLores(Material material, int amount, String name, short damage, byte data, String... lores) {
        return create(material, amount, damage, data, null, null, name, lores);
    }
}