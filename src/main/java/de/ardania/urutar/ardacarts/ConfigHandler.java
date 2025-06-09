package de.ardania.urutar.ardacarts;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class ConfigHandler {

    private static FileConfiguration _config = null;

    public static void refreshConfig() {
        _config = PLUGIN.getConfig();
    }

    public static String getConfigText(String name) {
        return _config.getString(name);
    }

    public static String getConfigMessage(String name) {
        String message = getConfigText(name);
        return translateColorCodes(message);
    }

    public static String getConfigMessage(String name, String variable, String replacement) {
        String message = getConfigText(name);
        if (message == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', message.replace(variable, replacement));
    }

    private static String translateColorCodes(String message) {
        if (message == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static double getConfigDouble(String name) {
        return _config.getDouble(name);
    }

    public static boolean getConfigBoolean(String name) {
        return _config.getBoolean(name);
    }

    public static int getConfigInt(String name) {
        return _config.getInt(name);
    }
}
