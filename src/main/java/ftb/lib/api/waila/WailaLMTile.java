package ftb.lib.api.waila;

import ftb.lib.api.tile.IWailaTile;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WailaLMTile extends BasicWailaHandler
{
	public WailaLMTile(EventRegisterWaila e, WailaType... t)
	{ super(e, t); }
	
	@Override
	public ItemStack getWailaStack(WailaDataAccessor data)
	{
		if(data.tile != null && !data.tile.isInvalid() && data.tile instanceof IWailaTile.Stack)
			return ((IWailaTile.Stack) data.tile).getWailaStack(data);
		
		return null;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack is, List<String> l, WailaDataAccessor data)
	{
		if(data.tile != null && !data.tile.isInvalid() && data.tile instanceof IWailaTile.Head)
			((IWailaTile.Head) data.tile).addWailaHead(data, l);
		
		return l;
	}
	
	@Override
	public List<String> getWailaBody(ItemStack is, List<String> l, WailaDataAccessor data)
	{
		if(data.tile != null && !data.tile.isInvalid() && data.tile instanceof IWailaTile.Body)
			((IWailaTile.Body) data.tile).addWailaBody(data, l);
		
		return l;
	}
	
	@Override
	public List<String> getWailaTail(ItemStack is, List<String> l, WailaDataAccessor data)
	{
		if(data.tile != null && !data.tile.isInvalid() && data.tile instanceof IWailaTile.Tail)
			((IWailaTile.Tail) data.tile).addWailaTail(data, l);
		
		return l;
	}
}