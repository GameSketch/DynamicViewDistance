package net.gamesketch.viewdistance.commands;

import net.gamesketch.viewdistance.DynamicViewDistance;
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
        if (args.length > 0) {
            if ("server".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("viewdistance.reset.server")) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                plugin.getServer().setViewDistance(SERVER_DEFAULT_VIEW_DISTANCE);
                return true;
            }
        }
        if (args.length > 1) {
            if ("world".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("viewdistance.reset.world") && !sender.hasPermission("viewdistance.reset.world."+args[1])) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                World world = plugin.getServer().getWorld(args[1]);
                if (world == null) {
                    return false;
                }
                world.resetViewDistance();
                return true;
            } else if ("player".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("viewdistance.reset.player") && !sender.hasPermission("viewdistance.reset.player."+args[1])) {
                    sender.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                Player player = plugin.getServer().getPlayer(args[1]);
                if (player == null) {
                    return false;
                }
                player.resetViewDistance();
                return true;
            }
        }
        return false;
    }
}
