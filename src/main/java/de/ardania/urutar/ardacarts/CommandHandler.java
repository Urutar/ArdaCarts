package de.ardania.urutar.ardacarts;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.ardania.urutar.ardacarts.commands.HelpCommand;
import de.ardania.urutar.ardacarts.commands.ReloadCommand;
import de.ardania.urutar.ardacarts.commands.SlowdownCommand;
import de.ardania.urutar.ardacarts.commands.SpeedupCommand;
import de.ardania.urutar.ardacarts.commands.VersionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {
	// Tab completion
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.add("help");
			completions.add("speedup");

			if (sender.isOp() || sender.hasPermission("ardacarts.*")) {
				completions.add("slowdown");
				completions.add("reload");
				completions.add("version");
			}
		}

		return completions;
	}

	// Valid command handling
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player || sender instanceof ConsoleCommandSender))
			return false;

		if (args.length < 1 || (args[0].isEmpty())) {
			// Send invalid command message
			MessageHandler.informPassengers(sender, MessageType.InvalidCommand);
			return true;
		}

		switch (args[0]) {
			case "speedup": {
				SpeedupCommand.Execute(sender);
				break;
			}
			case "slowdown": {
				SlowdownCommand.Execute(sender);
				break;
			}
			case "help": {
				HelpCommand.Execute(sender);
				break;
			}
			case "reload": {
				ReloadCommand.Execute(sender);
				break;
			}
			case "version": {
				VersionCommand.Execute(sender);
				break;
			}
			default: {
				// Send invalid command message
				MessageHandler.informPassengers(sender, MessageType.InvalidCommand);
				break;
			}
		}

		return true;
	}
}