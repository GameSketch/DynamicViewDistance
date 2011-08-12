package net.gamesketch.viewdistance;

import net.gamesketch.viewdistance.commands.ResetViewCommand;
import net.gamesketch.viewdistance.commands.SetViewCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicViewDistance extends JavaPlugin {

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        getCommand("setview").setExecutor(new SetViewCommand(this));
        getCommand("resetview").setExecutor(new ResetViewCommand(this));
    }
}