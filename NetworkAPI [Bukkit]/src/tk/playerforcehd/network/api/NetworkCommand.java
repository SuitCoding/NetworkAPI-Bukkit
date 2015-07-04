package tk.playerforcehd.network.api;

import java.io.DataInputStream;
import java.io.IOException;

import tk.playerforcehd.network.interfaces.ICommand;

abstract public class NetworkCommand implements ICommand {

	/**
	 * The channel where this command is listen
	 */
	private String subchannel;
	
	/**
	 * The last command input stream
	 */
	private DataInputStream dataInputStream;
	
	/**
	 * Registers the command
	 * @param subchannel In which command is this?
	 */
	public NetworkCommand(String subchannel){
		this.subchannel = subchannel;
	}

	/**
	 * Returns the subchannel of the command
	 * @return the subchannel
	 */
	public String getSubchannel() {
		return subchannel;
	}

	/**
	 * Returns the DataInputStream which can be read
	 * @return the DataInputStream
	 */
	public DataInputStream getDataInputStream() {
		return dataInputStream;
	}
	
	/**
	 * The work space where the developer can co anything with the arguments from the stream
	 * @param dataInputStream the stream which can be read
	 */
	abstract public void onRun(DataInputStream dataInputStream);
	
	/**
	 * Get called of a command cames in (command = subchannel)
	 * @param dataInputStream the arguments
	 */
	public void execute(DataInputStream dataInputStream){
		this.dataInputStream = dataInputStream;
		onRun(this.dataInputStream);
	}
	
	/**
	 * Read the next String from the byte message
	 * @param dataInputStream the stream who get readed
	 * @return the String from the last byte
	 * @throws IOException if anything went wrong
	 */
	public String readNextString(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readUTF();
	}
	
	/**
	 * Read the next int from the byte message
	 * @param dataInputStream the stream who get readed
	 * @return the int from the last byte
	 * @throws IOException if anything went wrong
	 */
	public int readNextInt(DataInputStream dataInputStream) throws IOException {
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
}
