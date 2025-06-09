package de.ardania.urutar.ardacarts;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class EconomyHandler {

    private static Economy eco = null;

    public static void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = PLUGIN.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }
    }

    public static boolean economyExists() {
        return eco != null;
    }

    public static boolean hasMoney(Player player, double amount) {
        return economyExists() && eco.has(player, amount);
    }

    public static boolean takeMoney(Player player, double amount) {
        if (!economyExists())
            return false;

        EconomyResponse response = eco.withdrawPlayer(player, amount);
        return response.transactionSuccess();
    }
}
