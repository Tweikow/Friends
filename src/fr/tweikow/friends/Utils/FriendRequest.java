package fr.tweikow.friends.Utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static fr.tweikow.friends.Main.main;

public class FriendRequest {

    public static HashMap<Player, Player> request = new HashMap();
    public static HashMap<Player, Player> demande = new HashMap();
    private static File file;
    private static YamlConfiguration config;
    private static File filetarget;
    private static YamlConfiguration configtarget;
    private static List<String> list;
    private static OfflinePlayer op;

    public static void friendList(Player player) {
        String status;
        file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        list = config.getStringList("friends.list");
        player.sendMessage(" ");
        if(config.getInt("friends.info.friend_count") > 0) {
            player.sendMessage(ChatColor.GRAY + "Nombre total d'amis: " + ChatColor.YELLOW + list.size());
            player.sendMessage("§8§m---------------------------------------------------");
            for (String friend : list) {

                OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(friend));
                if (Bukkit.getOfflinePlayer(UUID.fromString(friend)).isOnline()) {
                    status = ChatColor.GRAY + target.getName() + ChatColor.GREEN + " est actuellement en ligne";
                } else
                    status = ChatColor.GRAY + target.getName() + ChatColor.RED + " est actuellement hors-ligne";

                player.sendMessage(status);
            }
            player.sendMessage("§8§m---------------------------------------------------");
        } else {
            player.sendMessage("§8§m---------------------------------------------------");
            player.sendMessage(ChatColor.RED + "Vous ne possédez aucun amis.");
            player.sendMessage("§8§m---------------------------------------------------");
        }
    }

    public static void sendInvite(Player player, Player target) {
        file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        filetarget = new File(main.getDataFolder(), "/" + target.getUniqueId() + ".yml");
        configtarget = YamlConfiguration.loadConfiguration(filetarget);
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
        if (!filetarget.exists()) {
            configtarget.set("player_name", player.getName());
            configtarget.set("friends.info.friend_limit", 9 * 4);
            configtarget.set("friends.info.friend_count", list.size());
            configtarget.set("friends.list", list);
            try {
                configtarget.save(filetarget);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!(list.contains(target.getUniqueId().toString()))) {
            if (!request.containsKey(player)) {
                if(!request.containsValue(player)) {
                    request.put(player, target);

                    player.sendMessage(ChatColor.YELLOW + "Vous venez d'envoyé une invitation d'ami à " + ChatColor.WHITE + target.getName());
                    target.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.YELLOW + " vient de vous demandez en ami.");

                    TextComponent accept = new TextComponent(ChatColor.GREEN + "§l[Accepté] ");
                    TextComponent deny = new TextComponent(ChatColor.RED + "§l[Refuser]");

                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName()));
                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + player.getName()));

                    target.spigot().sendMessage(accept, deny);

                    try {
                        config.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else acceptInvite(target, player);
            } else player.sendMessage(ChatColor.RED + "Vous avez déjà envoyé une invitation !");
        } else player.sendMessage(ChatColor.RED + "Vous êtes déjà ami avec ce joueur !");
    }

    public static void acceptInvite(Player player, Player target){
        if(request.containsKey(player)){
            file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            filetarget = new File(main.getDataFolder(), "/" + target.getUniqueId() + ".yml");
            configtarget = YamlConfiguration.loadConfiguration(filetarget);

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

            file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            config.set("friends.info.request_limit", 1);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            file = new File(main.getDataFolder(), "/" + target.getUniqueId() + ".yml");
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
        file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
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
            file = new File(main.getDataFolder(), "/" + op.getUniqueId() + ".yml");
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