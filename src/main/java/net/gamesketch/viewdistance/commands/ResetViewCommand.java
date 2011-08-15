package net.gamesketch.viewdistance.commands;

import net.gamesketch.viewdistance.DynamicViewDistance;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetViewCommand implements CommandExecutor {
    private static final int SERVER_DEFAULT_VIEW_DISTANCE = 5;
    private final DynamicViewDistance plugin;

    public ResetViewCommand(DynamicViewDistance plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player player = (Player) sender;
            return resetPlayerViewDistance(sender, player);
        }
        if (args.length > 0) {
            if ("server".equalsIgnoreCase(args[0])) {
                return resetServerViewDistance(sender, plugin.getServer());
            }
        }
        // These commands act on the world the sender is on, or the sender themselves
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                return false;
            }
            if ("world".equalsIgnoreCase(args[0])) {
                World world = ((Player) sender).getWorld();
                return resetWorldViewDistance(sender, world);
            } else if ("player".equalsIgnoreCase(args[0])) {
                Player player = (Player) sender;
                return resetPlayerViewDistance(sender, player);
            }
        }
        // These commands act on an arbitrary world or player, identified by name
        if (args.length > 1) {
            if ("world".equalsIgnoreCase(args[0])) {
                World world = plugin.getServer().getWorld(args[1]);
                return resetWorldViewDistance(sender, world);
            } else if ("player".equalsIgnoreCase(args[0])) {
                Player player = plugin.getServer().getPlayer(args[1]);
                return resetPlayerViewDistance(sender, player);
            }
        }
        return false;
    }

    private boolean resetWorldViewDistance(CommandSender sender, World world) {
        if (world == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.reset.world") && !sender.hasPermission("viewdistance.reset.world." + world.getName())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            world.resetViewDistance();
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("World \""+world.getName()+"\" view distance reset. It is now "+world.getViewDistance());
        return true;
    }

    private boolean resetServerViewDistance(CommandSender sender, Server server) {
        if (server == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.reset.server") && !sender.hasPermission("viewdistance.reset.server." + server.getName())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            server.setViewDistance(SERVER_DEFAULT_VIEW_DISTANCE);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Server view distance reset. It is now "+server.getViewDistance());
        return true;
    }

    private boolean resetPlayerViewDistance(CommandSender sender, Player player) {
        if (player == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.reset.player") && !sender.hasPermission("viewdistance.reset.player." + player.getName()) && (!sender.hasPermission("viewdistance.reset.self") && player.equals(sender))) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            player.resetViewDistance();
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Player \""+player.getName()+"\" view distance reset. It is now "+player.getViewDistance());
        return true;
    }
}
