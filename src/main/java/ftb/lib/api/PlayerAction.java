package ftb.lib.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.TextureCoords;
import ftb.lib.api.client.FTBLibClient;
import ftb.lib.api.friends.ILMPlayer;
import ftb.lib.api.gui.GuiLM;
import latmod.lib.util.FinalIDObject;

import java.util.List;

public abstract class PlayerAction extends FinalIDObject
{
	public enum Type
	{
		SELF,
		OTHER,
		BOTH;
		
		public boolean self()
		{ return this == SELF || this == BOTH; }
		
		public boolean other()
		{ return this == OTHER || this == BOTH; }
		
		public boolean equalsType(Type t)
		{
			if(t == null || t == BOTH) return true;
			else if(t == SELF) return self();
			else return other();
		}
	}
	
	public final Type type;
	public final int priority;
	public final TextureCoords icon;
	public final LangKey langKey;
	
	public PlayerAction(Type t, String id, int p, TextureCoords c)
	{
		super(id);
		type = (t == null) ? Type.SELF : t;
		priority = p;
		icon = c;
		langKey = new LangKey("player_action." + id);
	}
	
	public abstract void onClicked(ILMPlayer self, ILMPlayer other);
	
	@SideOnly(Side.CLIENT)
	public String getDisplayName()
	{ return langKey.format(); }
	
	public void addMouseOverText(List<String> l) { }
	
	public void render(int ax, int ay, double z)
	{
		FTBLibClient.setTexture(icon);
		GuiLM.drawTexturedRectD(ax, ay, z, 16, 16, icon.minU, icon.minV, icon.maxU, icon.maxV);
	}
	
	public void postRender(int ax, int ay, double z)
	{
	}
	
	@Override
	public int compareTo(Object o)
	{
		int i = Integer.compare(((PlayerAction) o).priority, priority);
		return (i == 0) ? super.compareTo(o) : i;
	}
	
	public boolean isVisibleFor(ILMPlayer self, ILMPlayer other)
	{ return true; }
	
	public Boolean configDefault()
	{ return null; }
}