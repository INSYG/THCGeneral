package com.insyg.thcplug;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
    @Override
    public void onEnable() {
    	this.saveDefaultConfig();
    	getLogger().info("┌───────────────────────────────────────────────┐");
    	getLogger().info("│ Hey walls, when am I getting my Mod back? :^) │");
    	getLogger().info("└───────────────────────────────────────────────┘");
    	if (!getConfig().contains("Events.HolidaySeason")) {
    		getConfig().set("Events.HolidaySeason", true);
    		saveConfig();
    	}
    	Calendar targetDate = new GregorianCalendar();
    	targetDate.set(2016, Calendar.JANUARY, 2);
    	if (getConfig().getBoolean("Events.HolidaySeason") && System.currentTimeMillis() > targetDate.getTimeInMillis()) {
    		getConfig().set("Events.HolidaySeason", false);
    	}
    	getServer().getPluginManager().registerEvents(new HeyListen(this), this);
    }
    
    @Override
    public void onDisable() {
    	this.saveConfig();
    	getLogger().info("┌───────────────────────────────────────────────┐");
    	getLogger().info("│ I'll be waiting, walls.   :               )   │");
    	getLogger().info("└───────────────────────────────────────────────┘");
    }
    
    // Commands, yo
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	// /luna
    	if (cmd.getName().equalsIgnoreCase("luna")) {
    		sender.sendMessage(Functions.altColor("&eYou expected Luna, but it was me! ... It's still me, Luna."));
    		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					sender.sendMessage(Functions.altColor("&eAlso, I heard Luna's pretty cool."));
				}
			}, 100);
    		return true;
    	}
    	
    	// /thc
    	if (cmd.getName().equalsIgnoreCase("thc")) {
    		String cmdTitle = Functions.altColor("&8(  : &eTouhouCraft &3General Plugin &7v0.1 &8:  )");
    		
    		// no args
    		//if (args.length == 0) {
    			
    			sender.sendMessage(cmdTitle);
    			sender.sendMessage(Functions.altColor("&6More information coming &cSoon\u2122&6."));
    			sender.sendMessage(Functions.altColor("&6Firework Arrows will be globally disabled after:"));
    			sender.sendMessage(Functions.altColor("&cJanuary 1, 2016"));
    		/*	sender.sendMessage(Functions.altColor("&6Use &c/thc help &6for more commands."));
    			
    		// /thc help
    		} else if (args[1].equalsIgnoreCase("help")) {
    			
    			sender.sendMessage(cmdTitle);
    			sender.sendMessage(Functions.altColor("&6Use &c/thc fwarrows <true/false> &6to enable/disable firework arrows."));
    		*/	
    		//}
    		return true;
    		
    	}
    	
    	if (cmd.getName().equalsIgnoreCase("fwtoggle")) {
    		
    		Player player = (Player) sender;
    		String fwpath = "Players." + player.getUniqueId().toString() + ".fwarrows";
    		
    		if (!getConfig().contains(fwpath) || getConfig().getBoolean(fwpath)) {
    			
    			getConfig().set(fwpath, false);
    			saveConfig();
    			player.sendMessage(Functions.altColor("&6Firework arrows &cdisabled&6. (Doesn't work yet.)"));
    			
    		} else if (!getConfig().getBoolean(fwpath)) {
    			
    			getConfig().set(fwpath, true);
    			saveConfig();
    			player.sendMessage(Functions.altColor("&6Firework arrows &aenabled&6. (Doesn't work yet.)"));
    			
    		}
    		return true;
    		
    	}
    	
    	// test command aaaa
    	if (cmd.getName().equalsIgnoreCase("testhc")) {
    		sender.sendMessage(Functions.altColor("&eI was too lazy to remove this command. -Luna"));
    		return true;
    	}
    	
    	// /anal
    	if (cmd.getName().equalsIgnoreCase("anal")) {
    		
    		if (sender instanceof Player) {
    			Player player = (Player) sender;
    			if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
    				player.sendMessage(Functions.altColor("&4It needs to hurt if you want it to feel good."));
    			} else {
    				player.damage(69, player);
    			}
    		} else {
    			sender.sendMessage("Only a player may run this command.");
    		}
    		return true;
    		
    	}
    	return false;
    	
    }
    
}
