package com.gmail.nossr50;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
public class mcm {
	/*
	 * I'm storing my functions/methods in here in an unorganized manner. Spheal with it.
	 */
	private static mcMMO plugin;
	public mcm(mcMMO instance) {
    	plugin = instance;
    }
	private static volatile mcm instance;
	public static mcm getInstance() {
    	if (instance == null) {
    	instance = new mcm(plugin);
    	}
    	return instance;
    	}
	public static double getDistance(Location loca, Location locb)
    {
    return Math.sqrt(Math.pow(loca.getX() - locb.getX(), 2) + Math.pow(loca.getY() - locb.getY(), 2)
    + Math.pow(loca.getZ() - locb.getZ(), 2));
    }
	public boolean isBlockAround(Location loc, Integer radius, Integer typeid){
		Block blockx = loc.getBlock();
    	int ox = blockx.getX();
        int oy = blockx.getY();
        int oz = blockx.getZ();
    	for (int cx = -radius; cx <= radius; cx++) {
            for (int cy = -radius; cy <= radius; cy++) {
                for (int cz = -radius; cz <= radius; cz++) {
                    Block block = loc.getWorld().getBlockAt(ox + cx, oy + cy, oz + cz);
                    //If block is block
                    if (block.getTypeId() == typeid) {
                        return true;
                    }
                }
            }
        }
    	return false;
	}
	public boolean isPvpEnabled(){
		String propertyName = "pvp";
		FileReader fr = null;
		try {
			fr = new FileReader("server.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String property;
		String s = null;
		try {
			while((s=br.readLine()) .indexOf(propertyName)==-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		property = s.split("=")[1];
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(property.toLowerCase().equals("true")){
			return true;
		} else {
			return false;
		}
	}
    public Integer calculateHealth(Integer health, Integer newvalue){
    	if((health + newvalue) > 20){
    		return 20;
    	} else {
    		return health+newvalue;
    	}
    }
    public Integer calculateMinusHealth(Integer health, Integer newvalue){
    	if((health - newvalue) < 1){
    		return 0;
    	} else {
    		return health-newvalue;
    	}
    }
    public Integer getHealth(Entity entity){
    	if(entity instanceof Monster){
    		Monster monster = (Monster)entity;
    		return monster.getHealth();
    	} else if (entity instanceof Animals){
    		Animals animals = (Animals)entity;
    		return animals.getHealth();
    	} else if (entity instanceof Player){
    		Player player = (Player)entity;
    		return player.getHealth();
    	} else {
    		return 0;
    	}
    }
    public boolean isInt(String string){
		try {
		    int x = Integer.parseInt(string);
		}
		catch(NumberFormatException nFE) {
		    return false;
		}
		return true;
	}
    public void simulateNaturalDrops(Entity entity){
    	Location loc = entity.getLocation();
    	if(entity instanceof Pig){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 319); //BACON
    			}
    			mcDropItem(loc, 319);
    		}
    	}
    	if(entity instanceof Spider){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 287); //SILK
    			}
    			mcDropItem(loc, 287);
    		}
    	}
    	if(entity instanceof Skeleton){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 262); //ARROWS
    			}
    			mcDropItem(loc, 262);
    		}
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 352); //BONES
    			}
    			mcDropItem(loc, 352);
    		}
    	}
    	if(entity instanceof Zombie){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 288); //FEATHERS
    			}
    			mcDropItem(loc, 288);
    		}
    	}
    	if(entity instanceof Cow){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 334); //LEATHER
    			}
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 334);
    			}
    			mcDropItem(loc, 334);
    		}
    	}
    	if(entity instanceof Squid){
    		if(Math.random() * 3 > 2){
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 351); //INK SACS
    			}
    			if(Math.random() * 2 > 1){
    				mcDropItem(loc, 351);
    			}
    			mcDropItem(loc, 351);
    		}
    	}
    	mcSkills.getInstance().arrowRetrievalCheck(entity);
    }
    public void mcDropItem(Location loc, int id){
    	if(loc != null){
    	Material mat = Material.getMaterial(id);
		byte damage = 0;
		ItemStack item = new ItemStack(mat, 1, (byte)0, damage);
		loc.getWorld().dropItemNaturally(loc, item);
    	}
    }
	
    public boolean isSwords(ItemStack is){
    	if(is.getTypeId() == 268 || is.getTypeId() == 267 || is.getTypeId() == 272 || is.getTypeId() == 283 || is.getTypeId() == 276){
    		return true;
    	} else {
    		return false;
    	}
    }
    public boolean isAxes(ItemStack is){
    	if(is.getTypeId() == 271 || is.getTypeId() == 258 || is.getTypeId() == 286 || is.getTypeId() == 279 || is.getTypeId() == 275){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void mcmmoHelpCheck(String[] split, Player player, PlayerChatEvent event){
    	if(split[0].equalsIgnoreCase("/woodcutting")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"WOODCUTTING"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Chopping down trees");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Double Drops: "+ChatColor.GREEN+"Double the normal loot");
    	}
    	if(split[0].equalsIgnoreCase("/archery")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"ARCHERY"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Attacking Monsters");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			//player.sendMessage(ChatColor.DARK_AQUA+"Daze (Monsters): "+ChatColor.GREEN+"Enemies lose interest for 1 second");
			player.sendMessage(ChatColor.DARK_AQUA+"Daze (Players): "+ChatColor.GREEN+"Disorients foes");
			player.sendMessage(ChatColor.DARK_AQUA+"Damage+: "+ChatColor.GREEN+"Modifies Damage");
    	}
    	if(split[0].equalsIgnoreCase("/axes")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"AXES"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Attacking Monsters");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Critical Strikes (Monster): "+ChatColor.GREEN+"Instant kill");
			player.sendMessage(ChatColor.DARK_AQUA+"Critical Strikes (Players): "+ChatColor.GREEN+"Double Damage");
			player.sendMessage(ChatColor.DARK_AQUA+"Axe Mastery (500 SKILL): "+ChatColor.GREEN+"Modifies Damage");
    	}
    	if(split[0].equalsIgnoreCase("/swords")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"SWORDS"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Attacking Monsters");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Parrying: "+ChatColor.GREEN+"Negates Damage");
			player.sendMessage(ChatColor.DARK_AQUA+"Bleed: "+ChatColor.GREEN+"Apply a 2 second bleed DoT to enemies");
    	}
    	if(split[0].equalsIgnoreCase("/acrobatics")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"ACROBATICS"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Falling");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Roll: "+ChatColor.GREEN+"Negates Damage");
    	}
    	if(split[0].equalsIgnoreCase("/mining")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"MINING"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Mining Stone & Ore");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Double Drops: "+ChatColor.GREEN+"Double the normal loot");
    	}
    	if(split[0].equalsIgnoreCase("/repair")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"REPAIR"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Repairing");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Repair: "+ChatColor.GREEN+"Repair Iron Tools & Armor");
			player.sendMessage(ChatColor.DARK_AQUA+"Diamond Repair (50+ SKILL): "+ChatColor.GREEN+"Repair Diamond Tools & Armor");
    	}
    	if(split[0].equalsIgnoreCase("/unarmed")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"UNARMED"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Attacking Monsters");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Disarm (Players): "+ChatColor.GREEN+"Drops the foes item held in hand");
			player.sendMessage(ChatColor.DARK_AQUA+"Damage+: "+ChatColor.GREEN+"Modifies Damage");
    	}
    	if(split[0].equalsIgnoreCase("/herbalism")){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"HERBALISM"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Harvesting Herbs");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Food+: "+ChatColor.GREEN+"Modifies health received from bread/stew");
			player.sendMessage(ChatColor.DARK_AQUA+"Double Drops (Wheat): "+ChatColor.GREEN+"Double the normal loot");
    	}
    	if(split[0].equalsIgnoreCase("/excavation")){

			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"-----[]"+ChatColor.GREEN+"EXCAVATION"+ChatColor.RED+"[]-----");
			player.sendMessage(ChatColor.DARK_GRAY+"XP GAIN: "+ChatColor.WHITE+"Digging and finding treasures");
			player.sendMessage(ChatColor.RED+"---[]"+ChatColor.GREEN+"EFFECTS"+ChatColor.RED+"[]---");
			player.sendMessage(ChatColor.DARK_AQUA+"Treasure Hunter: "+ChatColor.GREEN+"Ability to dig for treasure");
    	}
		if(split[0].equalsIgnoreCase("/"+mcLoadProperties.mcmmo)){
			event.setCancelled(true);
    		player.sendMessage(ChatColor.GRAY+"mcMMO is an RPG inspired plugin");
    		player.sendMessage(ChatColor.GRAY+"You can gain skills in several professions by");
    		player.sendMessage(ChatColor.GRAY+"doing things related to that profession.");
    		player.sendMessage(ChatColor.GRAY+"Mining for example will increase your mining XP.");
    		player.sendMessage(ChatColor.GRAY+"Wood Cutting will increase Wood Cutting, etc...");
    		player.sendMessage(ChatColor.GRAY+"Repairing is simple in mcMMO");
    		player.sendMessage(ChatColor.GRAY+"Say you want to repair an iron shovel");
    		player.sendMessage(ChatColor.GRAY+"start by making an anvil by combining 9 iron ingots");
    		player.sendMessage(ChatColor.GRAY+"on a workbench. Place the anvil and while holding the shovel");
    		player.sendMessage(ChatColor.GRAY+"right click the anvil to interact with it, If you have spare");
    		player.sendMessage(ChatColor.GRAY+"iron ingots in your inventory the item will be repaired.");
    		player.sendMessage(ChatColor.GRAY+"You cannot hurt other party members");
    		player.sendMessage(ChatColor.BLUE+"Set your own spawn with "+ChatColor.RED+"/myspawn");
    		player.sendMessage(ChatColor.GREEN+"Based on your skills you will get "+ChatColor.DARK_RED+"random procs "+ChatColor.GREEN+ "when");
    		player.sendMessage(ChatColor.GREEN+"using your profession, like "+ChatColor.DARK_RED+"double drops "+ChatColor.GREEN+"or "+ChatColor.DARK_RED+"better repairs");
    		player.sendMessage(ChatColor.GREEN+"Find out mcMMO commands with /mcc");
    		player.sendMessage(ChatColor.GREEN+"Appreciate the mod? ");
    		player.sendMessage(ChatColor.GREEN+"You can donate via paypal to"+ChatColor.DARK_RED+" nossr50@gmail.com");
    	}
    	if(split[0].equalsIgnoreCase("/"+mcLoadProperties.mcc)){
    		event.setCancelled(true);
    		player.sendMessage(ChatColor.GRAY+"mcMMO has a party system included");
    		player.sendMessage(ChatColor.GREEN+"~~Commands~~");
    		if(mcPermissions.getInstance().party(player)){
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.party+" <name> - to join a party");
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.party+" q - to quit a party");
    		}
    		if(mcPermissions.getInstance().partyTeleport(player))
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.ptp+" <name> - party teleport");
    		if(mcPermissions.getInstance().partyChat(player))
    		player.sendMessage(ChatColor.GRAY+"/p - toggles party chat");
    		player.sendMessage(ChatColor.GREEN+"/"+mcLoadProperties.stats+ChatColor.GRAY+" - Check current skill levels");
    		if(mcPermissions.getInstance().setMySpawn(player))
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.setmyspawn+" - Sets your spawn");
    		if(mcPermissions.getInstance().mySpawn(player)){
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.myspawn+" - travel to myspawn, clears inventory");
    		player.sendMessage(ChatColor.GREEN+"/"+mcLoadProperties.clearmyspawn+" - resets your myspawn to default spawn location");
    		}
    		if(mcPermissions.getInstance().whois(player) || player.isOp())
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.whois+" - view detailed info about a player (req op)");
    		player.sendMessage(ChatColor.GRAY+"/woodcutting - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/mining - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/repair - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/unarmed - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/herbalism - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/excavation - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/archery - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/swords - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/acrobatics - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/axes - Skill info");
    		player.sendMessage(ChatColor.GRAY+"/invite - Invite players to your party");
    		player.sendMessage(ChatColor.GRAY+"/accept - Accept an invite");
    		if(mcPermissions.getInstance().mmoedit(player))
    		player.sendMessage(ChatColor.GRAY+"/"+mcLoadProperties.mmoedit+" - Modify mcMMO skills of players/yourself");
    	}
    }
}
