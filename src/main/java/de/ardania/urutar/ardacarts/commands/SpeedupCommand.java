package de.ardania.urutar.ardacarts.commands;

import de.ardania.urutar.ardacarts.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class SpeedupCommand {

	public static void Execute(CommandSender sender) {

		if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
			return;
		}

		// Plugin is already running
		if (PLUGIN.SPEEDUP_CARTS) {
			// Send already running message
			MessageHandler.informPassengers(sender, MessageType.AlreadyRunning);
			return;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("ardacarts.speedup") && !player.isOp()) {
				// Sender is Player without permission
				MessageHandler.informPassengers(sender, MessageType.NoPermission);
				return;
			}

			double price = ConfigHandler.getConfigDouble(Constants.CONFIG_PRICE);
			if (price > 0) {
				if (!EconomyHandler.hasMoney(player, price)) {
					// Player has not enough money
					MessageHandler.informPassengers(sender, MessageType.NoMoney);
					return;
				}
				boolean moneyTaken = EconomyHandler.takeMoney(player, price);
				if (!moneyTaken) {
					// Price was not paid
					MessageHandler.informPassengers(sender, MessageType.NoMoney);
					return;
				}
			}
		}

		runSpeedupTask(sender);
	}

	// Run Speedup BukkitRunnable
	private static void runSpeedupTask(CommandSender sender) {
		if (PLUGIN.SPEEDUP_CARTS)
			return;

		PLUGIN.SPEEDUP_CARTS = true;

		// Send speedup message
		MessageHandler.informPassengers(sender, MessageType.Speedup);
		PLUGIN.SHOW_SLOWDOWN_MESSAGE = true;
		int activationTime = ConfigHandler.getConfigInt(Constants.CONFIG_ACTIVATION_TIME);

        // Remove all ArdaCart event listeners
        HandlerList.unregisterAll(PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new EventListener(), PLUGIN);

        new BukkitRunnable() {
            @Override
            public void run() {
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
        }.runTaskLater(PLUGIN, activationTime);

    }

}
