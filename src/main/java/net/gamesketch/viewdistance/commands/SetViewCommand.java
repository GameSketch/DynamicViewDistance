package net.gamesketch.viewdistance.commands;

import net.gamesketch.viewdistance.DynamicViewDistance;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetViewCommand implements CommandExecutor {
    private final DynamicViewDistance plugin;

    public SetViewCommand(DynamicViewDistance plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            if ("server".equalsIgnoreCase(args[0])) {
                return setServerViewDistance(sender, args[1], plugin.getServer());
            }
        }
        // These commands act on the world the sender is on, or the sender themselves
        if (args.length == 2) {
            if (!(sender instanceof Player)) {
                return false;
            }
            if ("world".equalsIgnoreCase(args[0])) {
                World world = ((Player) sender).getWorld();
                return setWorldViewDistance(sender, args[1], world);
            } else if ("player".equalsIgnoreCase(args[0])) {
                Player player = (Player) sender;
                return setPlayerViewDistance(sender, args[1], player);
            }
        }
        // These commands act on an arbitrary world or player, identified by name
        if (args.length > 2) {
            if ("world".equalsIgnoreCase(args[0])) {
                World world = plugin.getServer().getWorld(args[1]);
                return setWorldViewDistance(sender, args[2], world);
            } else if ("player".equalsIgnoreCase(args[0])) {
                Player player = plugin.getServer().getPlayer(args[1]);
                return setPlayerViewDistance(sender, args[2], player);
            }
        }
        return false;
    }

    private boolean setWorldViewDistance(CommandSender sender, String viewDistance, World world) {
        if (world == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.set.world") && !sender.hasPermission("viewdistance.set.world." + world.getName())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            return setWorldViewDistance(sender, Integer.parseInt(viewDistance), world);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setWorldViewDistance(CommandSender sender, int viewDistance, World world) {
        if (world == null) {
            return false;
        }
        try {
            world.setViewDistance(viewDistance);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("World \""+world.getName()+"\" view distance set to "+viewDistance);
        return true;
    }

    private boolean setServerViewDistance(CommandSender sender, String viewDistance, Server server) {
        if (server == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.set.server") && !sender.hasPermission("viewdistance.set.server." + server.getName())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            return setServerViewDistance(sender, Integer.parseInt(viewDistance), server);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setServerViewDistance(CommandSender sender, int viewDistance, Server server) {
        if (server == null) {
            return false;
        }
        try {
            server.setViewDistance(viewDistance);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Server view distance set to "+viewDistance);
        return true;
    }

    private boolean setPlayerViewDistance(CommandSender sender, String viewDistance, Player player) {
        if (player == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.set.player") && !sender.hasPermission("viewdistance.set.player." + player.getName())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        try {
            return setPlayerViewDistance(sender, Integer.parseInt(viewDistance), player);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setPlayerViewDistance(CommandSender sender, int viewDistance, Player player) {
        if (player == null) {
            return false;
        }
        try {
            player.setViewDistance(viewDistance);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Player \""+player.getName()+"\" view distance set to "+viewDistance);
        return true;
    }
}
