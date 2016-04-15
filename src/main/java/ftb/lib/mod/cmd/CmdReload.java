package ftb.lib.mod.cmd;

import ftb.lib.FTBLib;
import ftb.lib.api.cmd.*;
import ftb.lib.mod.config.FTBLibConfigCmdNames;
import net.minecraft.command.*;

public class CmdReload extends CommandLM
{
	public CmdReload()
	{ super(FTBLibConfigCmdNames.reload.getAsString(), CommandLevel.OP); }
	
	public void processCommand(ICommandSender ics, String[] args) throws CommandException
	{
		FTBLib.reload(ics, true, args.length > 0 && args[0].equalsIgnoreCase("client"));
	}
}