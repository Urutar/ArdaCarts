package de.ardania.urutar.ardacarts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import static de.ardania.urutar.ardacarts.ArdaCarts.PLUGIN;


public class EventListener implements Listener {
    // EventHandler
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
    	Vehicle vehicle = event.getVehicle();
        if (!(vehicle instanceof Minecart))
            return;

        Minecart cart = (Minecart) vehicle;
        String cartName = cart.getCustomName();
        if (cartName == null || !cartName.equals(ConfigHandler.getConfigText(Constants.CONFIG_ENTITY_NAME))) {
            return;
        }
        if (cart.isEmpty()) {
            // remove derailed/empty minecart
            cart.remove();
            return;
        }
        if (PLUGIN.SPEEDUP_CARTS) {
            // boost minecart speed
            setVelocity(cart, ConfigHandler.getConfigDouble(Constants.CONFIG_MAX_SPEED));
        } else {
            setVelocity(cart, 0.4);
        }
    }

    // Checks for barrier blocks and set velocity
    private void setVelocity(Minecart cart, double vel) {

    	// For debugging
    	if (ConfigHandler.getConfigBoolean(Constants.CONFIG_DEBUG)) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(ChatColor.YELLOW + "[ArdaCartsDebug] MaxSpeed:" + vel + " " + PLUGIN.SPEEDUP_CARTS);
        }

        String speedBlockName = ConfigHandler.getConfigText(Constants.CONFIG_BLOCK_TYPE);
    	Material speedMaterial = Material.valueOf(speedBlockName);

        Location cartLocation = cart.getLocation();
        Block cartBlock = cartLocation.getBlock();
        Block checkBlock = cartBlock.getRelative(BlockFace.DOWN, 3);

        double speedLimit = 0.4;
        if (checkBlock.getType() == speedMaterial) {
            speedLimit = vel;
        }

        cart.setMaxSpeed(speedLimit);
    }
}




