package fr.tweikow.friends.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        event.getPlayer().teleport(new Location(Bukkit.getWorld("hub"), (float) -1118.473, 69.0, (double) -271.545, (float) 0.2, (float) -0.9));

    }

}
