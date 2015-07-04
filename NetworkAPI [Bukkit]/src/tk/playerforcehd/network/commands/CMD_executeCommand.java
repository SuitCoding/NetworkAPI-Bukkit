package tk.playerforcehd.network.commands;

import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import tk.playerforcehd.network.api.NetworkCommand;

public class CMD_executeCommand extends NetworkCommand {

	/**
	 * Registers the command, and how
	 */
	public CMD_executeCommand() {
		super("executeCommand");
	}

	@Override
	public void onRun(DataInputStream dataInputStream) {
		String type = "";
		try {
			type = dataInputStream.readUTF();
		} catch (IOException e) {
		}
		if(type.startsWith("Server:")){
			Bukkit.getConsoleSender().sendMessage(type.replace("Server:", ""));
		} else {
			String[] compos = type.split(":");
			Player p = Bukkit.getPlayer(compos[0]);
			p.chat(compos[1]);
		}
	}

}
