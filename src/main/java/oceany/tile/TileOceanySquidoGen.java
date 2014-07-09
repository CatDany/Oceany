package oceany.tile;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import oceany.Config;
import oceany.damage.ModDamageSources;

public class TileOceanySquidoGen extends ModTileOceanyCoreDependant
{
	private boolean isAdvanced;
	private int cooldown;
	
	@Override
	public void tick(TETickType type)
	{
		if (type == TETickType.CORE_DEPENDANT)
		{
			if (cooldown > 0)
			{
				cooldown--;
			}
			else
			{
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(coreX - 8.5, coreY, coreZ - 8.5, coreX + 9.5, coreY + 7, coreZ + 9.5);
				List<EntitySquid> list = worldObj.getEntitiesWithinAABB(EntitySquid.class, aabb);
				TileOceanyCore tile = (TileOceanyCore)worldObj.getTileEntity(coreX, coreY, coreZ);
				if (!list.isEmpty() && tile.giveEnergy(Config.getInteger("settings.squidogen.energy_per_squid")))
				{
					EntitySquid squid = list.get(0);
					squid.attackEntityFrom(ModDamageSources.getDamageSquidoGen(worldObj, xCoord, yCoord, zCoord), 10);
				}
				cooldown = 80;
			}
		}
	}
	
	public TileOceanySquidoGen(boolean adv)
	{
		super();
		cooldown = 0;
		this.isAdvanced = adv;
	}
	
	public boolean isAdvanced()
	{
		return isAdvanced;
	}
	
	public void handleDrops(List<EntityItem> drops)
	{
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof ISidedInventory)
		{
			ISidedInventory tile = (ISidedInventory)worldObj.getTileEntity(xCoord, yCoord, zCoord);
			for (EntityItem i : drops)
			{
				ItemStack item = i.getEntityItem();
				EntityItem entity = new EntityItem(worldObj, xCoord + 0.5, yCoord - 0.5, zCoord + 0.5, item);
				worldObj.spawnEntityInWorld(entity);
			}
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {}
	
	@Override
	public void onDataPacketCustom(NetworkManager net, S35PacketUpdateTileEntity packet) {}
}