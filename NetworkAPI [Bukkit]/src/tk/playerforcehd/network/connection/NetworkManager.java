package tk.playerforcehd.network.connection;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import tk.playerforcehd.network.Main;
import tk.playerforcehd.network.api.NetworkAPI;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class NetworkManager implements PluginMessageListener {

	/**
	 * The main instance of the NetworkAPI
	 */
	private Main thePlugin;
	
	/**
	 * The name of this manager
	 */
	@SuppressWarnings("unused")
	private String name;
	
	/**
	 * The channel where this manager is listen
	 */
	private String channel;
	
	/**
	 * The API Instance
	 */
	@SuppressWarnings("unused")
	private NetworkAPI theAPI;
	
	/**
	 * Creates a new Network Manager,  needed for communication
	 * @param main the main instance of the NetworkAPI
	 * @param name the name of this Manager
	 * @param channel the channel where this class is listen
	 * @param networkAPI the API
	 */
	public NetworkManager(Main thePlugin, String name, String channel, NetworkAPI theNetworkAPI){
		this.thePlugin = thePlugin;
		this.name = name;
		this.channel = channel;
		this.theAPI = theNetworkAPI;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this.thePlugin, this.channel);
		Bukkit.getMessenger().registerIncomingPluginChannel(this.thePlugin, this.channel, this);
	}
	
	/**
	 * Send a plugin mesage to a player
	 * @param subchannel the subchannel where the message get send
	 * @param messages 
	 */
	public void sendMessageToProxy(String subchannel, String... messages){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF(subchannel);
		for(String write : messages){
			stream.writeUTF(write);
		}
		Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(thePlugin, "BungeeCord", stream.toByteArray());
	}

	/**
	 * This is listen to the incomming pluginmessages
	 */
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(arg2));
		String subchannel = "";
		try {
			subchannel = stream.readUTF();
		} catch (IOException e) {
		}
		onRecievePluginMessage(subchannel, stream);
		onRecievePluginMessage(arg1, subchannel, stream);
		this.thePlugin.getTheNetworkCommandManager().exec(subchannel, stream);
	}
	
	/**
	 * Get fired when a plugin message cames in
	 * @param subchannel the subchannnel where the message cames in
	 * @param dataInputStream the DataInputStream, can read with stream.readUTF();
	 */
	public void onRecievePluginMessage(String subchannel, DataInputStream dataInputStream){}
	
	/**
	 * Get fired when a plugin message cames in
	 * @param player the player who get the message
	 * @param subchannel the subchannel where the message cames in
	 * @param dataInputStream the DataInputStream, can read with stream.readUTF();
	 */
	public void onRecievePluginMessage(Player player, String subchannel, DataInputStream dataInputStream){}

	/**
	 * Creates a new DataInputStream to read the data from a byte[]
	 * @param data the data which get converted to a input stream
	 * @return a new input stream, based on the byte[] data
	 */
	public DataInputStream createDataInputStream(byte[] data){
		return new DataInputStream(new ByteArrayInputStream(data));
	}
	
	/**
	 * Read the next String from the byte message
	 * @param dataInputStream the stream who get readed
	 * @return the String from the last byte
	 * @throws IOException if anything went wrong
	 */
	public String readNextString(DataInputStream dataInputStream) throws IOException{
		return dataInputStream.readUTF();
	}
	
	/**
	 * Read the next int from the byte message
	 * @param dataInputStream the stream who get readed
	 * @return the int from the last byte
	 * @throws IOException if anything went wrong
	 */
	public int readNextInt(DataInputStream dataInputStream) throws IOException{
		return dataInputStream.readInt();
	}
	
	/**
	 * Read the next boolean from the byte message
	 * @param dataInputStream the stream who get readed
	 * @return the boolean from the last byte
	 * @throws IOException if anything went wrong
	 */
	public boolean readNextBoolean(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readBoolean();
	}
	
	/**
	 * Executer a global broadcast with a permission who recieve it over the network.
	 * this can be used to broadcast global things with a plugin
	 * @param whoExecuteIt the player who send the plugin message
	 * @param permission the needed permission to recieve the message
	 * @param message the message which get send
	 */
	public void executeBroadcast(Player whoExecuteIt, String permission, String message){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF("broadcast");
		stream.writeUTF("Permission:" + permission);
		stream.writeUTF(message);
		whoExecuteIt.sendPluginMessage(this.thePlugin, "BungeeCord", stream.toByteArray());
	}
	
	/**
	 * Executer a global broadcast with a permission who recieve it over the network.
	 * this can be used to broadcast global things with a plugin
	 * @param whoExecuteIt the player who send the plugin message
	 * @param message the message which get send
	 */
	public void executeBroadcast(Player whoExecuteIt, String message){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF("broadcast");
		stream.writeUTF(message);
		whoExecuteIt.sendPluginMessage(this.thePlugin, "BungeeCord", stream.toByteArray());
	}
	
	/**
	 * Execute a command on the proxy
	 * @param command the command which get runned
	 */
	public void executeCommand_Proxy(String command){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF("executeCommand");
		stream.writeUTF("bungee");
		stream.writeUTF(command);
		Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(this.thePlugin, "BungeeCord", stream.toByteArray());
	}
	
	/**
	 * Execute a command in the console of a speciefied server
	 * @param command the command which get executed
	 * @param serverName the name of the server the the command get executed
	 */
	public void executeCommand_Server(String command, String serverName){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF("executeCommand");
		stream.writeUTF("server_" + serverName);
		stream.writeUTF(command);
		Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(this.thePlugin, "BungeeCord", stream.toByteArray());
	}
	
	/**
	 * Let's a player execute a command on his current server
	 * @param command the command which get executes
	 * @param player the player who executes it
	 */
	public void executeCommand_Player(String command, Player player){
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF("executeCommand");
		stream.writeUTF(player.getName());
		stream.writeUTF(command);
		Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(this.thePlugin, "BungeeCord", stream.toByteArray());
	}
}
