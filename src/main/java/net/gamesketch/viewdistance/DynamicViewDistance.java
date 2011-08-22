package net.gamesketch.viewdistance;

import net.gamesketch.viewdistance.commands.ResetViewCommand;
import net.gamesketch.viewdistance.commands.SetViewCommand;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicViewDistance extends JavaPlugin {

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        getCommand("setview").setExecutor(new SetViewCommand(this));
        getCommand("resetview").setExecutor(new ResetViewCommand(this));
    }

    public boolean setPlayerViewDistance(Player player, int viewDistance) throws IllegalArgumentException {
        if (player == null) {
            return false;
        }
        player.setViewDistance(viewDistance);
        return true;
    }

    public boolean setServerViewDistance(int viewDistance) throws IllegalArgumentException{
        getServer().setViewDistance(viewDistance);
        return true;
    }

    public boolean setWorldViewDistance(World world, int viewDistance) throws IllegalArgumentException {
        if (world == null) {
            return false;
        }
        world.setViewDistance(viewDistance);
        return true;
    }
}
