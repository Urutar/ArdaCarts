package de.ardania.urutar.ardacarts.commands;

import de.ardania.urutar.ardacarts.MessageType;
import org.bukkit.command.CommandSender;

import de.ardania.urutar.ardacarts.MessageHandler;

public class VersionCommand {

	public static void Execute(CommandSender sender) {
        if (!sender.isOp() && !sender.hasPermission("ardacarts.version")) {
            // Deny command
			MessageHandler.informPassengers(sender, MessageType.NoPermission);
        } else {
            // Send plugin info
			MessageHandler.informPassengers(sender, MessageType.Version);
        }

    }
	
}
