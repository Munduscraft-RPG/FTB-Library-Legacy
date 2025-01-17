package ftb.lib.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import ftb.lib.api.IClickable;
import ftb.lib.api.MouseButton;
import latmod.lib.IntList;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class ConfigEntryCustom extends ConfigEntry implements IClickable
{
	private JsonElement value;
	
	public ConfigEntryCustom(String id)
	{
		super(id);
	}
	
	@Override
	public ConfigEntryType getConfigType()
	{ return ConfigEntryType.CUSTOM; }
	
	@Override
	public int getColor()
	{ return 0xFFAA00; }
	
	@Override
	public void func_152753_a(JsonElement o)
	{ value = o == JsonNull.INSTANCE ? null : o; }
	
	@Override
	public JsonElement getSerializableElement()
	{ return value == null ? JsonNull.INSTANCE : value; }
	
	@Override
	public ConfigGroup getAsGroup()
	{
		JsonElement e = getSerializableElement();
		if(e.isJsonNull()) return null;
		else if(e.isJsonObject())
		{
			ConfigGroup group = new ConfigGroup(getID());
			group.func_152753_a(e);
			return group;
		}
		return null;
	}
	
	@Override
	public String getAsString()
	{
		JsonElement e = getSerializableElement();
		return e.isJsonNull() ? ". . ." : String.valueOf(e);
	}
	
	@Override
	public List<String> getAsStringList()
	{
		JsonElement e = getSerializableElement();
		if(e.isJsonNull()) return new ArrayList<>();
		List<String> list = new ArrayList<>();
		for(JsonElement e1 : e.getAsJsonArray())
			list.add(e1.getAsString());
		return list;
	}
	
	@Override
	public boolean getAsBoolean()
	{
		JsonElement e = getSerializableElement();
		return !e.isJsonNull() && e.getAsBoolean();
	}
	
	@Override
	public int getAsInt()
	{
		JsonElement e = getSerializableElement();
		return e.isJsonNull() ? 0 : e.getAsInt();
	}
	
	@Override
	public double getAsDouble()
	{
		JsonElement e = getSerializableElement();
		return e.isJsonNull() ? 0D : e.getAsDouble();
	}
	
	@Override
	public IntList getAsIntList()
	{
		JsonElement e = getSerializableElement();
		if(e.isJsonNull()) return null;
		JsonArray a = e.getAsJsonArray();
		IntList l = new IntList(a.size());
		for(int i = 0; i < l.size(); i++)
			l.set(i, a.get(i).getAsInt());
		return l;
	}
	
	@Override
	public void onClicked(MouseButton button)
	{
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag, boolean extended)
	{
		super.writeToNBT(tag, extended);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag, boolean extended)
	{
		super.readFromNBT(tag, extended);
	}
}