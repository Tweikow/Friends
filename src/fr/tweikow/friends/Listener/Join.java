package fr.tweikow.friends.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static fr.tweikow.friends.Main.main;

public class Join implements Listener {

    public HashMap<Player, Integer> i = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        File file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<String> list = config.getStringList("friends.list");

        for(String friend : list){
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(friend));
            if(target.isOnline()){
                i.put(player, 1);
                Player target1 = (Player) target;
                target1.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.GRAY + " vient de se connecté !");
            }
        }
        if(i.size() > 0) {
            player.sendMessage(ChatColor.GRAY + "Vous avez actuellement " + ChatColor.YELLOW + i.size() + ChatColor.GRAY + " amis en ligne !");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        File file = new File(main.getDataFolder(), "/" + player.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<String> list = config.getStringList("friends.list");

        for(String friend : list){
            i.remove(player, 1);
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(friend));
            Player target1 = (Player) target;
            target1.sendMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " vient de se déconnecté !");

        }
    }
}
