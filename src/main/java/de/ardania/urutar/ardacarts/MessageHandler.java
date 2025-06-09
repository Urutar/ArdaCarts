package de.ardania.urutar.ardacarts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;

public class MessageHandler {

	public static void informPassengers(CommandSender sender, MessageType messageType) {
		ArrayList<String> messages = new ArrayList<>();

		switch (messageType) {
			case AlreadyRunning: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_ALREADY_RUNNING));
				break;
			}
			case Error: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_ERROR));
				break;
			}
			case Help: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_HELP));
				messages.add(ConfigHandler.getConfigMessage(Constants.COMMANDS_RELOAD) + ConfigHandler.getConfigMessage(Constants.DESCRIPTION_RELOAD));
				messages.add(ConfigHandler.getConfigMessage(Constants.COMMANDS_SPEEDUP) + ConfigHandler.getConfigMessage(Constants.DESCRIPTION_SPEEDUP));
				messages.add(ConfigHandler.getConfigMessage(Constants.COMMANDS_SLOWDOWN) + ConfigHandler.getConfigMessage(Constants.DESCRIPTION_SLOWDOWN));
				messages.add(ConfigHandler.getConfigMessage(Constants.COMMANDS_VERSION) + ConfigHandler.getConfigMessage(Constants.DESCRIPTION_VERSION));
				break;
			}
			case InvalidCommand: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_INVALID_COMMAND));
				break;
			}
			case NoMoney: {
				String message = ConfigHandler.getConfigMessage(Constants.MESSAGES_NO_MONEY);
				if(message != null && !message.isEmpty())
					message = message.replace("%price%", ConfigHandler.getConfigText(Constants.CONFIG_PRICE));
				messages.add(message);
				break;
			}
			case NoPermission: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_NO_PERMISSION));
				break;
			}
			case Reload: {
				messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_RELOAD));
				break;
			}
			case Slowdown: {
				if (PLUGIN.SHOW_SLOWDOWN_MESSAGE) {
					String message = ConfigHandler.getConfigMessage(Constants.MESSAGES_SLOWDOWN);
					sender.sendMessage(message);

					informPlayers(sender, message);
				} else {
					messages.add(ConfigHandler.getConfigMessage(Constants.MESSAGES_ALREADY_SLOW));
				}
				break;
			}
			case Speedup: {
				int activationTime = ConfigHandler.getConfigInt(Constants.CONFIG_ACTIVATION_TIME);
				// Activation time is in ticks - 20 for 1 second
				double minutes = activationTime / 1200.0;
				DecimalFormat format = new DecimalFormat("0.#");
				String message = ConfigHandler.getConfigMessage(Constants.MESSAGES_SPEEDUP, "%minutes%",format.format(minutes) );
				sender.sendMessage(message);

				String messageSpeedupOther = ConfigHandler.getConfigMessage(Constants.MESSAGES_SPEEDUP_OTHER, "%sendername%", sender.getName());
				informPlayers(sender, messageSpeedupOther);
				break;
			}
			case Version: {
				PluginDescriptionFile description = PLUGIN.getDescription();
				String messageVersion = ConfigHandler.getConfigMessage(Constants.MESSAGES_VERSION);
				messages.add(messageVersion + ChatColor.GREEN + description.getVersion());
				messages.add(ChatColor.DARK_AQUA + "Authors: " + ChatColor.GREEN + description.getAuthors());
				break;
			}
            default: {
				break;
			}
		}
		for (String message : messages) {
			sender.sendMessage(message);
		}
	}

	private static void informPlayers(CommandSender sender, String message) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player player : players) {
			if (!player.equals(sender))
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}

	
}
	
