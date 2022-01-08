package fr.tweikow.friends.Listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inv = event.getClickedInventory();

        if(event.getCurrentItem() == null) return;

        if(inv.getName().equalsIgnoreCase(ChatColor.DARK_GRAY + "Liste d'amis")){
            event.setCancelled(true);
        }
    }

}
