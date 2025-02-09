package ftb.lib.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import latmod.lib.IntList;
import latmod.lib.LMListUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.List;

public class ConfigEntryStringList extends ConfigEntry
{
	public final List<String> defValue;
	private List<String> value;
	
	public ConfigEntryStringList(String id, List<String> def)
	{
		super(id);
		value = new ArrayList<>();
		defValue = new ArrayList<>();
		
		if(def != null && !def.isEmpty())
		{
			defValue.addAll(def);
			value.addAll(def);
		}
	}
	
	@Override
	public ConfigEntryType getConfigType()
	{ return ConfigEntryType.STRING_ARRAY; }
	
	@Override
	public int getColor()
	{ return 0xFFAA49; }
	
	public void set(List<String> o)
	{
		value.clear();
		
		if(o != null && !o.isEmpty())
		{
			value.addAll(o);
		}
	}
	
	@Override
	public final void func_152753_a(JsonElement o)
	{
		JsonArray a = o.getAsJsonArray();
		value.clear();
		for(int i = 0; i < a.size(); i++)
			value.add(a.get(i).getAsString());
		set(LMListUtils.clone(value));
	}
	
	@Override
	public final JsonElement getSerializableElement()
	{
		JsonArray a = new JsonArray();
		for(String aValue : getAsStringList())
		{
			a.add(new JsonPrimitive(aValue));
		}
		return a;
	}
	
	@Override
	public String getAsString()
	{ return getAsStringList().toString(); }
	
	@Override
	public boolean getAsBoolean()
	{ return !getAsStringList().isEmpty(); }
	
	@Override
	public IntList getAsIntList()
	{
		List<String> list = getAsStringList();
		IntList l = new IntList(list.size());
		for(int i = 0; i < list.size(); i++)
			l.add(Integer.parseInt(value.get(i)));
		return l;
	}
	
	@Override
	public List<String> getAsStringList()
	{ return value; }
	
	@Override
	public String getDefValueString()
	{ return defValue.toString(); }
	
	@Override
	public void writeToNBT(NBTTagCompound tag, boolean extended)
	{
		super.writeToNBT(tag, extended);
		
		List<String> list = getAsStringList();
		
		if(!list.isEmpty())
		{
			NBTTagList l = new NBTTagList();
			
			for(String s : list)
			{
				l.appendTag(new NBTTagString(s));
			}
			
			tag.setTag("V", l);
		}
		
		if(extended && !defValue.isEmpty())
		{
			NBTTagList l = new NBTTagList();
			
			for(String s : defValue)
			{
				l.appendTag(new NBTTagString(s));
			}
			
			tag.setTag("D", l);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag, boolean extended)
	{
		super.readFromNBT(tag, extended);
		
		NBTTagList list = (NBTTagList) tag.getTag("V");
		
		if(list != null)
		{
			List<String> l = new ArrayList<>(list.tagCount());
			
			for(int i = 0; i < list.tagCount(); i++)
			{
				l.add(list.getStringTagAt(i));
			}
			
			set(l);
		}
		else
		{
			set(null);
		}
		
		if(extended)
		{
			defValue.clear();
			
			list = (NBTTagList) tag.getTag("D");
			
			if(list != null)
			{
				List<String> l = new ArrayList<>(list.tagCount());
				
				for(int i = 0; i < list.tagCount(); i++)
				{
					defValue.add(list.getStringTagAt(i));
				}
			}
		}
	}
}