package net.gamesketch.viewdistance.commands;

import net.gamesketch.viewdistance.DynamicViewDistance;
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
                if (!sender.hasPermission("viewdistance.set.server")) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                try {
                    int viewDistance = Integer.parseInt(args[1]);
                    plugin.getServer().setViewDistance(viewDistance);
                } catch (NumberFormatException e) {
                    return false;
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(e.getMessage());
                    return false;
                }
                return true;
            }
        }
        if (args.length > 2) {
            if ("world".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("viewdistance.set.world") && !sender.hasPermission("viewdistance.set.world."+args[1])) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                World world = plugin.getServer().getWorld(args[1]);
                if (world == null) {
                    return false;
                }
                try {
                    int viewDistance = Integer.parseInt(args[2]);
                    world.setViewDistance(viewDistance);
                } catch (NumberFormatException e) {
                    return false;
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(e.getMessage());
                    return false;
                }
                return true;
            } else if ("player".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("viewdistance.set.player") && !sender.hasPermission("viewdistance.set.player."+args[1])) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                Player player = plugin.getServer().getPlayer(args[1]);
                if (player == null) {
                    return false;
                }
                try {
                    int viewDistance = Integer.parseInt(args[2]);
                    player.setViewDistance(viewDistance);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
