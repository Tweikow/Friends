package fr.tweikow.friends.Commands;

import fr.tweikow.friends.Utils.FriendRequest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Liste des commandes pour les amis:");
            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend add <player>");
            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend remove <player>");
            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend accept <player>");
            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend deny <player>");
            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend list");
            return false;
        }
        if (args[0].equalsIgnoreCase("accept")) {
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && target != player) {
                    FriendRequest.acceptInvite(target, player);
                } else player.sendMessage(ChatColor.RED + "Ce joueur est invalide ou n'est pas en ligne");
            } else player.sendMessage(ChatColor.RED + "Utilisation: /friend accept <player>");
            return false;
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if(args.length == 2) {
                String target = args[1];
                if (target != null) {
                    FriendRequest.removeFriends(player, target);
                } else player.sendMessage(ChatColor.RED + "Ce joueur est invalide ou n'est pas en ligne");
            } else player.sendMessage(ChatColor.RED + "Utilisation: /friend remove <player>");
            return false;
        }
        if (args[0].equalsIgnoreCase("deny")) {
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && target != player) {
                    FriendRequest.denyInvite(target, player);
                } else player.sendMessage(ChatColor.RED + "Ce joueur est invalide ou n'est pas en ligne");
            } else player.sendMessage(ChatColor.RED + "Utilisation: /friend deny <player>");
            return false;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && target != player) {
                    FriendRequest.sendInvite(player, target);
                } else player.sendMessage(ChatColor.RED + "Ce joueur est invalide ou n'est pas en ligne");
            } else player.sendMessage(ChatColor.RED + "Utilisation: /friend add <player>");
            return false;
        }
        if (args[0].equalsIgnoreCase("list")) {
            FriendRequest.friendList(player);
            return false;
        }
        player.sendMessage(ChatColor.YELLOW + "Liste des commandes pour les amis:");
        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend add <player>");
        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend remove <player>");
        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend accept <player>");
        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend deny <player>");
        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/friend list");
        return false;
    }
}
