package de.ardania.urutar.ardacarts.commands;

import de.ardania.urutar.ardacarts.MessageType;
import org.bukkit.command.CommandSender;

import de.ardania.urutar.ardacarts.MessageHandler;

public class HelpCommand {
	public static void Execute(CommandSender sender) {
        if (!sender.isOp() && !sender.hasPermission("ardacarts.help")) {
            // Deny command
            MessageHandler.informPassengers(sender, MessageType.NoPermission);
			return;
        }
        // List all commands
        MessageHandler.informPassengers(sender, MessageType.Help);
    }
}
