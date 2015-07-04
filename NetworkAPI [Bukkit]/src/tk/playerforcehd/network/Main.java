package tk.playerforcehd.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import tk.playerforcehd.network.api.NetworkAPI;
import tk.playerforcehd.network.api.NetworkCommand;
import tk.playerforcehd.network.commands.CMD_executeCommand;
import tk.playerforcehd.network.connection.NetworkCommandManager;
import tk.playerforcehd.network.connection.NetworkManager;

public class Main extends JavaPlugin {

	/**
	 * The main instance of the plugin!
	 */
	private Main thePlugin;
	
	/**
	 * The logger to log things to the console
	 */
	private Logger theLogger;
	
	/**
	 * Saves the NetworkManagers
	 */
	private Collection<NetworkManager> theNetworkManagers;
	
	/**
	 * The NetworkAPI instance, needed to acces the API
	 */
	private NetworkAPI theNetworkAPI;
	
	/**
	 *A collection of all registred Network Commands 
	 */
	private Collection<NetworkCommand> theNetworkCommands;
	
	/**
	 * A Network Command Manager to execute and register network commands
	 */
	private NetworkCommandManager theNetworkCommandManager;
	
	/**
	 * Get called when the plugin starts
	 */
	@Override
	public void onEnable(){
		this.thePlugin = this;
		this.theLogger = getLogger();
		
		theLogger.log(Level.INFO, "Please make sure that you have installed NetworkAPI on all your Bukkit/Spigot and the BungeeCord Server(s)!");
		theLogger.log(Level.INFO, "Registring Channel: BungeeCord...");
		this.getServer().getMessenger().registerOutgoingPluginChannel(this.thePlugin, "BungeeCord");
		theLogger.log(Level.INFO, "Registring Channel: NetworkAPI...");
		this.getServer().getMessenger().registerOutgoingPluginChannel(this.thePlugin, "NetworkAPI");
		theLogger.log(Level.INFO, "Registring API...");
		this.theNetworkAPI = new NetworkAPI(this);
		this.theNetworkManagers = new ArrayList<NetworkManager>();
		this.theNetworkCommandManager = new NetworkCommandManager(this);
		this.theNetworkCommands = new ArrayList<NetworkCommand>();
		registerInternalCommands();
		theLogger.log(Level.INFO, "Enabled...");
	}
	
	/**
	 * Get called when the plugin get disabled
	 */
	@Override
	public void onDisable(){
		theLogger.log(Level.INFO, "Disabled!");
	}

	/**
	 * Get the main instance of the Plugin
	 * @return the main instance
	 */
	public Main getThePlugin() {
		return thePlugin;
	}

	/**
	 * Get the logger to log things into the console
	 * @return the logger
	 */
	public Logger getTheLogger() {
		return theLogger;
	}

	/**
	 * Returns all registred NetworkManagers
	 * @return a list of network managers
	 */
	public Collection<NetworkManager> getTheNetworkManagers() {
		return theNetworkManagers;
	}

	/**
	 * Returns the API instance
	 * @return the API instance
	 */
	public NetworkAPI getTheNetworkAPI() {
		return theNetworkAPI;
	}

	/**
	 * Returns a list of all registred network commands
	 * @return a list of network commands
	 */
	public Collection<NetworkCommand> getTheNetworkCommands() {
		return theNetworkCommands;
	}

	/**
	 * Returns the manager to manage the registred NetworkCommands
	 * @return the NetworkManager
	 */
	public NetworkCommandManager getTheNetworkCommandManager() {
		return theNetworkCommandManager;
	}
	
	/**
	 * Registers all internal NetworkCommands
	 */
	private void registerInternalCommands(){
		this.theNetworkCommandManager.addCommand(new CMD_executeCommand());
	}
}
