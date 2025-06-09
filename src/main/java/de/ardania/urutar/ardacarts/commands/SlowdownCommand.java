package de.ardania.urutar.ardacarts.commands;

import de.ardania.urutar.ardacarts.MessageType;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import de.ardania.urutar.ardacarts.MessageHandler;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class SlowdownCommand {

	public static void Execute(CommandSender sender) {

		if (!(sender instanceof Player || sender instanceof ConsoleCommandSender))
			return;

		if (!(sender instanceof Player && sender.hasPermission("ardacarts.slowdown")) && !(sender instanceof ConsoleCommandSender)) {
			// Deny command
			MessageHandler.informPassengers(sender, MessageType.NoPermission);
			return;
		}

		PLUGIN.SPEEDUP_CARTS = false;

		// Send slowdown message
		MessageHandler.informPassengers(sender, MessageType.Slowdown);
		PLUGIN.SHOW_SLOWDOWN_MESSAGE = false;

		new BukkitRunnable() {
			@Override
			public void run() {
				// Remove all ArdaCart event listeners
				HandlerList.unregisterAll(PLUGIN);
			}
		}.runTaskLater(PLUGIN, 7);
	}
	
}
