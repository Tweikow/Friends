package fr.tweikow.friends;

import fr.tweikow.friends.Commands.FriendCommand;
import fr.tweikow.friends.Listener.InventoryClick;
import fr.tweikow.friends.Listener.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin main;

    @Override
    public void onEnable() {
        main = this;
        getCommand("friend").setExecutor(new FriendCommand());
        Bukkit.getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);

    }

    @Override
    public void onDisable() {
        main = null;

    }

}
