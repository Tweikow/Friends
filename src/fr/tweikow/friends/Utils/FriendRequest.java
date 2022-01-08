package fr.tweikow.friends.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static fr.tweikow.friends.Main.main;

public class FriendRequest {

    public static HashMap<Player, Player> request = new HashMap();
    private static File file;
    private static YamlConfiguration config;
    private static List<String> list;
    private static OfflinePlayer op;

    public static void friendList(Player player) {
        file = new File(main.getDataFolder(), "/friends/" + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        list = config.getStringList("friends.list");

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Liste d'amis");
        ItemStack glass = ItemBuilder.create(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.DARK_GRAY + "Enoria");

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, glass);
            for (int ii = 45; ii < 54; ii++) {
                inv.setItem(ii, glass);
            }
        }

        ItemStack myhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta myheadMeta = (SkullMeta) myhead.getItemMeta();
        List<String> mylore = new ArrayList<>();
        myheadMeta.setOwner(player.getName());
        mylore.add(ChatColor.GRAY + "Nombre d'amis: " + ChatColor.YELLOW + list.size());
        myheadMeta.setDisplayName(ChatColor.YELLOW + player.getName());
        myheadMeta.setLore(mylore);
        myhead.setItemMeta(myheadMeta);
        inv.setItem(4, myhead);
        ;

        for (String friend : list) {
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            List<String> lore = new ArrayList<>();
            String playerName = Bukkit.getOfflinePlayer(UUID.fromString(friend)).getName();
            if (Bukkit.getOfflinePlayer(UUID.fromString(friend)).isOnline())
                lore.add(ChatColor.GRAY + "Statut: " + ChatColor.GREEN + "En ligne");
            else
                lore.add(ChatColor.GRAY + "Statut: " + ChatColor.RED + "Hors-ligne");
            headMeta.setOwner(playerName);
            headMeta.setLore(lore);
            headMeta.setDisplayName(ChatColor.YELLOW + playerName);
            head.setItemMeta(headMeta);

            inv.addItem(head);
        }
        player.openInventory(inv);
    }

    public static void sendInvite(Player player, Player target) {
        file = new File(main.getDataFolder(), "/friends/" + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        list = config.getStringList("friends.list");

        if (!file.exists()) {
            config.set("player_name", player.getName());
            config.set("friends.info.friend_limit", 9 * 4);
            config.set("friends.info.friend_count", list.size());
            config.set("friends.list", list);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!(list.contains(target.getUniqueId().toString()))) {
            if (!request.containsKey(player)) {
                request.put(player, target);
                player.sendMessage(ChatColor.YELLOW + "Vous venez d'envoyé une invitation d'ami à " + ChatColor.WHITE + target.getName());
                target.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.YELLOW + " vient de vous demandez en ami.");
                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else player.sendMessage(ChatColor.RED + "Vous avez déjà envoyé une invitation !");
        } else player.sendMessage(ChatColor.RED + "Vous êtes déjà ami avec ce joueur !");
    }

    public static void acceptInvite(Player player, Player target){
        if(request.containsKey(player)){
            file = new File(main.getDataFolder(), "/friends/" + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            File filetarget = new File(main.getDataFolder(), "/friends/" + target.getUniqueId() + ".yml");
            YamlConfiguration configtarget = YamlConfiguration.loadConfiguration(filetarget);

            if(config.getInt("friends.info.friend_count") < config.getInt("friends.info.friend_limit")) {
                if (configtarget.getInt("friends.info.friend_count") < configtarget.getInt("friends.info.friend_limit")) {
                    list = config.getStringList("friends.list");
                    list.add(target.getUniqueId().toString());

                    config.set("player_name", player.getName());
                    if (!file.exists()) {
                        config.set("friends.info.friend_limit", 9 * 4);
                    }
                    config.set("friends.info.friend_count", list.size());
                    config.set("friends.list", list);

                    try {
                        config.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    list = configtarget.getStringList("friends.list");
                    list.add(player.getUniqueId().toString());

                    configtarget.set("player_name", target.getName());
                    if (!filetarget.exists()) {
                        configtarget.set("friends.info.friend_limit", 9 * 4);
                    }
                    configtarget.set("friends.info.friend_count", list.size());
                    configtarget.set("friends.list", list);

                    try {
                        configtarget.save(filetarget);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    target.sendMessage(ChatColor.YELLOW + "Vous avez accepté la demande d'ami de " + ChatColor.WHITE + player.getName());
                    player.sendMessage(target.getName() + ChatColor.YELLOW + " §evient d'accepter votre demande d'ami.");
                    request.remove(player, target);
                } else {
                    target.sendMessage(ChatColor.RED + "Vous n'avez plus de place dans votre liste d'ami");
                    player.sendMessage(target.getName() + ChatColor.RED + " ne possède plus assez de place dans sa liste d'ami.");
                    request.remove(player, target);

                }
            } else {
                player.sendMessage(ChatColor.RED + "Vous n'avez plus de place dans votre liste d'ami");
                target.sendMessage(player.getName() + ChatColor.RED + " ne possède plus assez de place dans sa liste d'ami.");
                request.remove(player, target);
            }
        } else target.sendMessage(ChatColor.RED + "Vous n'avez pas de demande d'ami en attente !");
    }
    public static void denyInvite(Player player, Player target){
        if(request.containsKey(player)){
            target.sendMessage(ChatColor.YELLOW + "Vous avez refusé la demande d'ami de " + ChatColor.WHITE + player.getName());
            player.sendMessage(target.getName() + ChatColor.YELLOW + " §evient de refusé votre demande d'ami.");

            file = new File(main.getDataFolder(), "/friends/" + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            config.set("friends.info.request_limit", 1);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            file = new File(main.getDataFolder(), "/friends/" + target.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            config.set("friends.info.request_limit", 1);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            request.remove(player, target);

        } else target.sendMessage(ChatColor.RED + "Vous n'avez pas de demande d'ami en attente !");
    }
    public static void removeFriends(Player player, String target) {
        file = new File(main.getDataFolder(), "/friends/" + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        list = config.getStringList("friends.list");
        op = Bukkit.getOfflinePlayer(target);
        if(op != null && list.contains(op.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "Vous n'êtes désormais plus amis avec " + ChatColor.WHITE + op.getName());
            list.remove(op.getUniqueId().toString());
            config.set("friends.info.friend_count", list.size());
            config.set("friends.list", list);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            file = new File(main.getDataFolder(), "/friends/" + op.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            list = config.getStringList("friends.list");
            list.remove(player.getUniqueId().toString());
            config.set("friends.info.friend_count", list.size());
            config.set("friends.list", list);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else player.sendMessage(ChatColor.RED + "Vous n'êtes pas amis avec ce joueur !");
    }
}