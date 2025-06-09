package de.ardania.urutar.ardacarts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArdaCarts extends JavaPlugin {

    private final CommandHandler commands = new CommandHandler();
    
    public static ArdaCarts PLUGIN;
    
    public boolean SHOW_SLOWDOWN_MESSAGE = false;
    public boolean SPEEDUP_CARTS = false;
    
    // on startup
    public void onEnable() {
        PLUGIN = this;
        saveDefaultConfig();

        // Register commands
        PluginCommand ardaCartsCommand = getCommand("ardacarts");
        if(ardaCartsCommand == null)
            return;

        ardaCartsCommand.setExecutor(commands);
        ardaCartsCommand.setTabCompleter(commands);

        ConfigHandler.refreshConfig();

        double price = ConfigHandler.getConfigDouble(Constants.CONFIG_PRICE);

        // Check vault
        if (price <= 0)
            return;

        EconomyHandler.setupEconomy();

        if (!EconomyHandler.economyExists()) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(ChatColor.RED + "[ArdaCarts] Disabled since Vault/Economy dependency is missing!");
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    // on shutdown
    public void onDisable() {

    }

    
}
