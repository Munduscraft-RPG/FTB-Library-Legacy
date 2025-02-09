package ftb.lib;

import ftb.lib.mod.FTBLibMod;
import latmod.lib.ByteCount;
import latmod.lib.ByteIOStream;
import latmod.lib.LMListUtils;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class LMNBTUtils
{
	public static String[] getMapKeys(NBTTagCompound tag)
	{
		if(tag == null || tag.hasNoTags()) return new String[0];
		return LMListUtils.toStringArray(tag.func_150296_c());
	}
	
	public static Map<String, NBTBase> toMap(NBTTagCompound tag)
	{
		Map<String, NBTBase> map = new HashMap<String, NBTBase>();
		for(Object s : tag.func_150296_c())
			map.put(s.toString(), tag.getTag(s.toString()));
		return map;
	}
	
	public static Set<Map.Entry<String, NBTBase>> entrySet(NBTTagCompound tag)
	{
		Set<Map.Entry<String, NBTBase>> l = new HashSet<>();
		if(tag == null || tag.hasNoTags()) return l;
		
		for(Object s : tag.func_150296_c())
		{
			l.add(new AbstractMap.SimpleEntry<>(s.toString(), tag.getTag(s.toString())));
		}
		
		return l;
	}
	
	public static <E extends NBTBase> Map<String, E> toMapWithType(NBTTagCompound tag)
	{
		HashMap<String, E> map = new HashMap<String, E>();
		if(tag == null || tag.hasNoTags()) return map;
		for(Object s : tag.func_150296_c())
			map.put(s.toString(), (E) tag.getTag(s.toString()));
		return map;
	}
	
	public static Exception writeMap(File f, NBTTagCompound tag)
	{
		try { CompressedStreamTools.write(tag, f); }
		catch(Exception e) { return e; }
		return null;
	}
	
	public static NBTTagCompound readMap(File f)
	{
		if(f == null || !f.exists()) return null;
		try { return CompressedStreamTools.read(f); }
		catch(Exception e)
		{
			if(!(e instanceof java.io.EOFException)) e.printStackTrace();
			FTBLibMod.logger.info("Possibly corrupted / old file. Trying the old method");
			
			try
			{
				FileInputStream is = new FileInputStream(f);
				byte[] b = new byte[is.available()];
				is.read(b);
				is.close();
				return CompressedStreamTools.func_152457_a(b, NBTSizeTracker.field_152451_a);
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean areTagsEqual(NBTTagCompound tag1, NBTTagCompound tag2)
	{
		if(tag1 == null && tag2 == null) return true;
		if(tag1 != null && tag2 == null) return false;
		if(tag1 == null && tag2 != null) return false;
		return tag1.equals(tag2);
	}
	
	public static void toStringList(List<String> l, NBTTagList tag)
	{
		l.clear();
		for(int i = 0; i < tag.tagCount(); i++)
			l.add(tag.getStringTagAt(i));
	}
	
	public static List<String> toStringList(NBTTagList tag)
	{
		ArrayList<String> l = new ArrayList<String>();
		toStringList(l, tag);
		return l;
	}
	
	public static NBTTagList fromStringList(List<String> l)
	{
		NBTTagList tag = new NBTTagList();
		for(int i = 0; i < l.size(); i++)
			tag.appendTag(new NBTTagString(l.get(i)));
		return tag;
	}
	
	public static NBTTagCompound readTag(ByteIOStream data)
	{
		try
		{
			byte[] abyte = data.readByteArray(ByteCount.INT);
			
			if(abyte == null) return null;
			else if(abyte.length == 0) return new NBTTagCompound();
			else return CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static void writeTag(ByteIOStream data, NBTTagCompound tag)
	{
		try
		{
			byte[] abyte;
			
			if(tag == null) abyte = null;
			else if(tag.hasNoTags()) abyte = new byte[0];
			else abyte = CompressedStreamTools.compress(tag);
			
			data.writeByteArray(abyte, ByteCount.INT);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	//TODO: Finish me
	public static Object generateTree(NBTBase base)
	{
		if(base == null) return null;
		
		switch(base.getId())
		{
			case Constants.NBT.TAG_END:
				return null;
			case Constants.NBT.TAG_LIST:
			{
				NBTTagList tagList = (NBTTagList) base;
				List<Object> list = new ArrayList<>();
				if(tagList.tagCount() == 0) return list;
				tagList = (NBTTagList) tagList.copy();
				
				for(int i = tagList.tagCount() - 1; i >= 0; i--)
				{
					list.add(tagList.removeTag(i));
				}
				
				return list;
			}
			case Constants.NBT.TAG_COMPOUND:
			{
				NBTTagCompound tagCompound = (NBTTagCompound) base;
				Map<String, Object> map = new HashMap<>();
				if(tagCompound.hasNoTags()) return map;
				
				for(Object s : tagCompound.func_150296_c())
				{
					map.put(s.toString(), generateTree(tagCompound.getTag(s.toString())));
				}
				
				return map;
			}
			default:
				return base;
		}
	}
}