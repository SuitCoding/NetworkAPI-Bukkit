package tk.playerforcehd.network.connection;

import java.io.DataInputStream;
import java.util.Collection;

import tk.playerforcehd.network.Main;
import tk.playerforcehd.network.api.NetworkCommand;

public class NetworkCommandManager {

	/**
	 * Saves the main instance
	 */
	private Main thePlugin;
	
	/**
	 * Creates a new instance of this
	 * @param thePlugin the main instance of the Plugin
	 */
	public NetworkCommandManager(Main thePlugin){
		this.thePlugin = thePlugin;
	}
	
	/**
	 * Registers a new Network Command
	 * @param networkCommand Network Command which get registred
	 */
	public void addCommand(NetworkCommand networkCommand){
		this.thePlugin.getTheNetworkCommands().add(networkCommand);
	}
	
	/**
	 * Removes a registred Network Command
	 * @param networkCommand Network Command which get removed
	 */
	public void removeCommand(NetworkCommand networkCommand){
		this.thePlugin.getTheNetworkCommands().remove(networkCommand);
	}
	
	/**
	 * Returns all registred Network Commands
	 * @return all registred Network Commands
	 */
	public Collection<NetworkCommand> getNetworkCommands(){
		return this.thePlugin.getTheNetworkCommands();
	}
	
	public void exec(String subchannel, DataInputStream stream){
		for(NetworkCommand command : getNetworkCommands()){
			if(command.getSubchannel().equalsIgnoreCase(subchannel)){
				command.execute(stream);
			}
		}
	}
}
