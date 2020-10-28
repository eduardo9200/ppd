package br.edu.ifce.dashboard;

public class Commands {

	public static final String DEFAULT_LOCAL_SERVER 	= "localhost";
	public static final String DEFAULT_IP_SERVER		= "127.0.0.1";
	
	public static final String DEFAULT_COMMAND			= "cmd:";
	public static final String DEFAULT_MESSAGE_COMMAND 	= "msg:";
	
	public static final String COMMAND_EXIT 			= "cmd:exit";
	
	@Deprecated
	public static final String COMMAND_LIST_PLAYERS		= "cmd:list-players";
	
	public static final String COMMAND_MOVE_SEEDS		= "cmd:move-seeds";
	public static final String COMMAND_RESET_GAME		= "cmd:reset";
	public static final String COMMAND_UNDO				= "cmd:undo";
	public static final String COMMAND_REDO				= "cmd:redo";
	public static final String COMMAND_GIVE_UP			= "cmd:give-up";
	
}
