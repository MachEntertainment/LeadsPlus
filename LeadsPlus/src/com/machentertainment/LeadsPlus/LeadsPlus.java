package com.machentertainment.LeadsPlus;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LeadsPlus extends JavaPlugin{
	
	Boolean verbose;
	private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
    
    @Override
    public void onEnable() {
    	if(this.getConfig().getInt("Version") != 1){
    		this.saveDefaultConfig();
    		
    		getLogger().severe("Configuration is malformed or missing.  Saving default.");
    	}
    	getLogger().info("LeadsPlus is now starting.");
    	
    	if(this.getConfig().getBoolean("Verbose") == true) {
    		verbose = true;
    	}else{
    		verbose = false;
    	}
    	
    	//Vault
    		if (!setupEconomy() ) {
    	           log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
    	           getServer().getPluginManager().disablePlugin(this);
    	           return;
    	    }
    	    setupPermissions();
    	    setupChat();
    }
    
    @Override
    public void onDisable(){
    	getLogger().severe("LeadsPlus has been disabled!");
    	this.saveConfig();
    }
    
  //Vault
  	private boolean setupEconomy() {
          if (getServer().getPluginManager().getPlugin("Vault") == null) {
              return false;
          }
          RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
          if (rsp == null) {
              return false;
          }
          econ = rsp.getProvider();
          return econ != null;
      }

      private boolean setupChat() {
          RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
          chat = rsp.getProvider();
          return chat != null;
      }

      private boolean setupPermissions() {
          RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
          perms = rsp.getProvider();
          return perms != null;
      }
  	
  	//Preformated message sending.
  	public void sendPlayer(Player player, String message){
  		player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "RPLite" + ChatColor.GOLD + "]: " + ChatColor.GREEN + message);
  	}
  	
  	//Console Logging (Config based)
  	public void sendLog(String level, String Message){
  		
  		if(verbose = true){
  			if(level.equalsIgnoreCase("info")){
  				getLogger().info(Message);
  			}else{
  				getLogger().warning(Message);
  			}
  		}
  	}

}
