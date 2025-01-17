package ftb.lib.api.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class WailaDataProvider implements IWailaDataProvider
{
	public final BasicWailaHandler handler;
	private static WailaDataAccessor dataAccessor = null;
	
	public WailaDataProvider(BasicWailaHandler h)
	{ handler = h; }
	
	private static WailaDataAccessor getData(IWailaDataAccessor i)
	{
		if(dataAccessor == null) dataAccessor = new WailaDataAccessor();
		dataAccessor.player = i.getPlayer();
		dataAccessor.world = i.getWorld();
		dataAccessor.position = i.getPosition();
		dataAccessor.tile = i.getTileEntity();
		dataAccessor.block = i.getBlock();
		dataAccessor.meta = i.getMetadata();
		dataAccessor.side = dataAccessor.position.sideHit;
		return dataAccessor;
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler config)
	{ return handler.getWailaStack(getData(data)); }
	
	@Override
	public List<String> getWailaHead(ItemStack is, List<String> l, IWailaDataAccessor data, IWailaConfigHandler config)
	{ return handler.getWailaHead(is, l, getData(data)); }
	
	@Override
	public List<String> getWailaBody(ItemStack is, List<String> l, IWailaDataAccessor data, IWailaConfigHandler config)
	{ return handler.getWailaBody(is, l, getData(data)); }
	
	@Override
	public List<String> getWailaTail(ItemStack is, List<String> l, IWailaDataAccessor data, IWailaConfigHandler config)
	{ return handler.getWailaTail(is, l, getData(data)); }
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP ep, TileEntity te, NBTTagCompound tag, World w, int x, int y, int z)
	{ return null; }
}