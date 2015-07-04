package tk.playerforcehd.network.api;

import java.util.Collection;

import tk.playerforcehd.network.Main;
import tk.playerforcehd.network.connection.NetworkManager;

public class NetworkAPI {

	/**
	 * The main instance of the NetworkAPI
	 */
	private Main thePlugin;
	
	/**
	 * Instance of the API
	 */
	public static NetworkAPI networkAPI;
	
	/**
	 * Registers the API Class, so it can be used if you call getNetworkAPI();
	 * @param thePlugin the main instance of the NetworkAPI
	 */
	@SuppressWarnings("static-access")
	public NetworkAPI(Main thePlugin){
		this.thePlugin = thePlugin;
		this.networkAPI = this;
	}

	/**
	 * Get the API to work with it
	 * @return the API
	 */
	public static NetworkAPI getNetworkAPI() {
		return networkAPI;
	}
	
	/**
	 * Returns a list of all registred NetworkManagers
	 * @return a list of NetworkManagers
	 */
	public Collection<NetworkManager> getNetworkManagers(){
		return this.thePlugin.getTheNetworkManagers();
	}
	
	/**
	 * Creates a new Network manager, used to handle the messages
	 * @param name the name of the manager
	 * @param channel the channel of the manager (like BungeeCord)
	 * @return a new NetworkManager
	 */
	public NetworkManager addNetworkManager(String name, String channel){
		NetworkManager manager = new NetworkManager(this.thePlugin, name, channel, this);
		this.thePlugin.getTheNetworkManagers().add(manager);
		return manager;
	}
	
	/**
	 * Register a new NetworkCommand
	 * @param networkCommand the command which you like to register
	 */
	public void addCommand(NetworkCommand networkCommand){
		this.thePlugin.getTheNetworkCommandManager().addCommand(networkCommand);
	}
	
	/**
	 * Removes a registred NetworkCommand
	 * @param networkCommand the command which you like to remove
	 */
	public void removeCommand(NetworkCommand networkCommand){
		this.thePlugin.getTheNetworkCommandManager().removeCommand(networkCommand);
	}
}
