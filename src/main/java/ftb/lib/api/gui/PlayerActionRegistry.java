package ftb.lib.api.gui;

import ftb.lib.api.PlayerAction;
import ftb.lib.api.friends.ILMPlayer;
import latmod.lib.config.*;

import java.util.*;

/**
 * Created by LatvianModder on 15.01.2016.
 */
public class PlayerActionRegistry
{
	private static final HashMap<String, PlayerAction> map = new HashMap<>();
	
	public static final ConfigGroup configGroup = new ConfigGroup("sidebar_buttons");
	
	public static boolean enabled(String id)
	{ return !configGroup.entryMap.containsKey(id) || configGroup.getEntry(id).getAsBoolean(); }
	
	public static void add(final PlayerAction a)
	{
		if(a != null)
		{
			map.put(a.getID(), a);
			
			if(a.configDefault() != null)
			{
				ConfigEntryBool entry = new ConfigEntryBool(a.getID(), a.configDefault().booleanValue())
				{
					public String getFullID()
					{ return "player_action." + a.getID(); }
				};
				
				configGroup.entryMap.put(a.getID(), entry);
			}
		}
	}
	
	public static List<PlayerAction> getPlayerActions(PlayerAction.Type t, ILMPlayer self, ILMPlayer other, boolean sort)
	{
		ArrayList<PlayerAction> l = new ArrayList<>();
		
		for(PlayerAction a : map.values())
		{
			if(a.type.equalsType(t) && a.isVisibleFor(self, other))
			{
				if(a.configDefault() != null)
				{
					if(!enabled(a.getID())) continue;
				}
				
				l.add(a);
			}
		}
		
		if(sort) Collections.sort(l);
		return l;
	}
}
