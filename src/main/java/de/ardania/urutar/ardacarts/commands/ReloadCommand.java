package de.ardania.urutar.ardacarts.commands;

import de.ardania.urutar.ardacarts.ConfigHandler;
import de.ardania.urutar.ardacarts.MessageType;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import de.ardania.urutar.ardacarts.MessageHandler;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class ReloadCommand {

	public static void Execute(CommandSender sender) {

		if (!sender.isOp() && !sender.hasPermission("ardacarts.reload")) {
			// Deny command
			MessageHandler.informPassengers(sender, MessageType.NoPermission);
			return;
		}

		PLUGIN.SPEEDUP_CARTS = false;
		PLUGIN.SHOW_SLOWDOWN_MESSAGE = false;

		new BukkitRunnable() {
			@Override
			public void run() {
				HandlerList.unregisterAll(PLUGIN);
			}

		}.runTaskLater(PLUGIN, 7);

		// Send reload message
		PLUGIN.reloadConfig();
		ConfigHandler.refreshConfig();
		MessageHandler.informPassengers(sender, MessageType.Reload);
	}
	
}
