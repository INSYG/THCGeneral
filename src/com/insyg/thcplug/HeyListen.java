package com.insyg.thcplug;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class HeyListen implements Listener { //could extend EntityHider

	private Main plugin;

    public HeyListen(Main plug) {
        plugin = plug;
    }
	
	@EventHandler(priority = EventPriority.LOW)
	public void arrowStop(ProjectileHitEvent event) {
		if (plugin.getConfig().getBoolean("Events.HolidaySeason") && event.getEntityType().toString() == "ARROW") {
			
			Entity ent = event.getEntity();
			Location loc = ent.getLocation();
			Firework fw = ent.getWorld().spawn(loc, Firework.class);
			FireworkMeta fwm = fw.getFireworkMeta();
			fwm.addEffect(FireworkEffect.builder()
					.flicker(false)
					.trail(false)
					.with(Functions.randFWType())
					.withColor(Functions.randColor())
					.build());
			fwm.setPower(0);
			fw.setFireworkMeta(fwm);
			
			/*List<Player> players = fw.getWorld().getPlayers();
			for (Player player : players) {
				if (!plugin.getConfig().getBoolean("Player." + player.getUniqueId().toString() + ".fwarrows", true))
					hideEntity(player, fw);
			}*/
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					fw.detonate();
				}
			}, 1);
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void coolItem(ItemSpawnEvent event) {
		
		Item drop = event.getEntity();
		ItemStack stack = drop.getItemStack();
		int count = stack.getAmount();
		String name = stack.getItemMeta().getDisplayName();
		if (name == null) name = stack.getType().name();
		
		if (count == 1) drop.setCustomName(name);
		else drop.setCustomName(count + "x " + name);
		drop.setCustomNameVisible(true);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void stackItems(ItemMergeEvent event) {
		
		Item target = event.getTarget();
		Item entity = event.getEntity();
		ItemStack stack = target.getItemStack();
		
		int count1 = stack.getAmount();
		int count2 = entity.getItemStack().getAmount();
		int count = (count1 + count2);
		
		String name = stack.getItemMeta().getDisplayName();
		if (name == null) name = stack.getType().name();
		if (count == 1) target.setCustomName(name);
		else target.setCustomName(count + "x " + name);
		target.setCustomNameVisible(true);
		
	}
	
}
