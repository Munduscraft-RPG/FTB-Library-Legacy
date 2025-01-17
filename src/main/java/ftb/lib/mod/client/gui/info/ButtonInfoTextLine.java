package ftb.lib.mod.client.gui.info;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.MouseButton;
import ftb.lib.api.gui.widgets.ButtonLM;
import ftb.lib.api.info.lines.InfoTextLine;
import net.minecraft.util.IChatComponent;

import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
@SideOnly(Side.CLIENT)
public class ButtonInfoTextLine extends ButtonLM
{
	public final GuiInfo guiInfo;
	public List<String> text;
	
	public ButtonInfoTextLine(GuiInfo g, InfoTextLine l)
	{
		super(g, 0, g.panelText.height, 0, 0);
		guiInfo = g;
		
		if(l != null)
		{
			IChatComponent c = l.getText();
			
			if(c != null)
			{
				text = guiInfo.getFontRenderer().listFormattedStringToWidth(c.getFormattedText(), g.panelText.width);
				if(text.isEmpty()) text = null;
			}
		}
		
		if(text != null)
		{
			if(text.size() > 1) width = g.panelText.width;
			else width = g.getFontRenderer().getStringWidth(text.get(0));
			height = 10 * text.size();
		}
		else
		{
			width = 0;
			height = 11;
		}
	}
	
	@Override
	public void addMouseOverText(List<String> l)
	{
	}
	
	@Override
	public void onClicked(MouseButton button)
	{
	}
	
	@Override
	public void renderWidget()
	{
		int ay = getAY();
		int ax = getAX();
		
		if(text != null)
		{
			for(int i = 0; i < text.size(); i++)
			{
				guiInfo.getFontRenderer().drawString(text.get(i), ax, ay + i * 10 + 1, guiInfo.colorText);
			}
		}
	}
}
