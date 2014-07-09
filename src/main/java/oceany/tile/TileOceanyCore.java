package oceany.tile;

import java.util.List;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import oceany.api.IOceanyBlock;
import oceany.blocks.BlockOceanyUpgrade;
import oceany.blocks.ModBlocks;
import oceany.libs.EntityHelper;
import oceany.libs.ItemUtils;
import oceany.potion.ModPotions;

public class TileOceanyCore extends ModTileBase
{
	public int energy;
	public int tier;
	public boolean[] upgrades = new boolean[BlockOceanyUpgrade.maxUpgrades];
	
	@Deprecated // TODO Make Oceany Core load chunks (3x3 around it)
	public boolean isChunkLoading;
	
	// also checks for upgrades when cool
	private int cooldown_tier_check;
	
	@Override
	public void updateEntity()
	{
		if (!isInOcean())
			return;
		
		markDirty();
		
		if (energy > getMaxEnergyFromTier(tier))
		{
			energy = getMaxEnergyFromTier(tier);
		}
		
		if (energy < getMaxEnergyFromTier(tier))
		{
			energy += getEnergyPerTick();
		}
		
		if (cooldown_tier_check > 0)
		{
			cooldown_tier_check--;
		}
		else
		{
			tier = getTier();
			upgrades = getUpgrades();
			if (isChunkLoading)
			{
				//
			}
			cooldown_tier_check = 80;
		}
		
		for (int i = 0; i < upgrades.length; i++)
		{
			if (upgrades[i])
			{
				tickUpgrade(i);
			}
		}
	}
	
	public TileOceanyCore()
	{
		energy = 0;
		tier = 1;
		cooldown_tier_check = 0;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		energy = tag.getInteger("Energy");
		tier = tag.getInteger("Tier");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("Energy", energy);
		tag.setInteger("Tier", tier);
	}
	
	@Override
	public void onDataPacketCustom(NetworkManager net, S35PacketUpdateTileEntity packet) {}
	
	public int getMaxEnergyFromTier(int tier)
	{
		switch (tier)
		{
		case 1:
			return 6000;
		case 2:
			return 21600;
		case 3:
			return 72000;
		case 4:
			return 288000;
		}
		return 0;
	}
	
	public int getEnergyPerTick()
	{
		int perTick = 0;
		
		switch (tier)
		{
		case 2:
			perTick += 1;
			break;
		case 3:
			perTick += 2;
			break;
		case 4:
			perTick += 4;
			break;
		}
		
		return perTick;
	}
	
	public boolean isInOcean()
	{
		boolean biomeValid = BiomeDictionary.isBiomeOfType(worldObj.getBiomeGenForCoords(xCoord, zCoord), Type.WATER);
		boolean deep = true;
		for (int i = yCoord; i < yCoord + 10; i++)
		{
			Block block = worldObj.getBlock(xCoord, i, zCoord);
			if (!(block instanceof IOceanyBlock) && !ItemUtils.compare(block, Blocks.water) && !ItemUtils.compare(block, Blocks.flowing_water) && i > yCoord + BlockOceanyUpgrade.maxUpgrades + 1)
			{
				deep = false;
				break;
			}
		}
		return biomeValid && deep;
	}
	
	public int getTier()
	{
		boolean tier2 = false;
		boolean tier3 = false;
		boolean tier4 = false;
		
		if (ItemUtils.compare(worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1), ModBlocks.tentacle_block)
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1), ModBlocks.tentacle_block)
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1), ModBlocks.tentacle_block)
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1), ModBlocks.tentacle_block))
		{
			tier2 = true;
		}
		if (ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord, zCoord - 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord + 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord + 1, zCoord - 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord + 3, yCoord, zCoord + 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord + 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord + 3, yCoord + 1, zCoord + 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord + 3, yCoord, zCoord - 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord + 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord + 3, yCoord + 1, zCoord - 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord, zCoord + 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord + 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord + 1, zCoord + 3) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord + 2, zCoord - 3), Blocks.obsidian)
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord + 2, zCoord + 3), Blocks.obsidian)
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord + 2, zCoord - 3), Blocks.obsidian)
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord + 2, zCoord + 3), Blocks.obsidian))
		{
			tier3 = true;
		}
		if (ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord - 1, zCoord - 2), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord - 1, zCoord - 1), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord - 1, zCoord), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord - 1, zCoord + 1), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 3, yCoord - 1, zCoord + 2), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 2, yCoord - 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord, yCoord - 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 2, yCoord - 1, zCoord - 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord - 1, zCoord - 2), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord - 1, zCoord - 1), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord - 1, zCoord), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord - 1, zCoord + 1), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 3, yCoord - 1, zCoord + 2), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 2, yCoord - 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord, yCoord - 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord + 2, yCoord - 1, zCoord + 3), ModBlocks.tentacle_block) && worldObj.getBlockMetadata(xCoord - 3, yCoord - 1, zCoord - 2) == 1
		 && ItemUtils.compare(worldObj.getBlock(xCoord, 63, zCoord), Blocks.beacon))
		{
			tier4 = true;
		}
		
		if (!tier2 && !tier3 && !tier4)
			return 1;
		else if (tier2 && !tier3 && !tier4)
			return 2;
		else if (tier2 && tier3 && !tier4)
			return 3;
		else if (tier2 && tier3 && tier4)
			return 4;
		else
			return 1;
	}
	
	public boolean[] getUpgrades()
	{
		boolean[] foundUpgrades = new boolean[upgrades.length];
		for (int i = 0; i < foundUpgrades.length; i++)
		{
			foundUpgrades[i] = false;
		}
		
		for (int i = yCoord + 1; i < yCoord + 1 + foundUpgrades.length; i++)
		{
			Block block = worldObj.getBlock(xCoord, i, zCoord);
			if (ItemUtils.compare(block, ModBlocks.oceany_upgrade))
			{
				foundUpgrades[worldObj.getBlockMetadata(xCoord, i, zCoord)] = true;
			}
		}
		return foundUpgrades;
	}
	
	private void tickUpgrade(int id)
	{
		int usage = BlockOceanyUpgrade.energyUsage[id];
		
		if (id == 0) // Breath in Depth
		{
			List<Entity> list = EntityHelper.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, getActionRadiusFromTier(tier));
			if (!list.isEmpty())
			{
				for (Entity entity : list)
				{
					if (entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && entity.isInWater() && consumeEnergy(usage))
					{
						((EntityLivingBase)entity).setAir(300);
					}
				}
			}
		}
		else if (id == 1) // Bright Sight
		{
			List<Entity> list = EntityHelper.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, getActionRadiusFromTier(tier));
			if (!list.isEmpty())
			{
				for (Entity entity : list)
				{
					if (entity instanceof EntityPlayer && entity.isInWater() && consumeEnergy(usage))
					{
						((EntityPlayer)entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 20, 0, true));
					}
				}
			}
		}
		else if (id == 2) // Creative
		{
			energy = getMaxEnergyFromTier(tier);
		}
		else if (id == 3) // Speed of Squid
		{
			List<Entity> list = EntityHelper.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, getActionRadiusFromTier(tier));
			if (!list.isEmpty())
			{
				for (Entity entity : list)
				{
					if (entity instanceof EntityPlayer && entity.isInWater() && !((EntityPlayer)entity).capabilities.isCreativeMode && consumeEnergy(usage))
					{
						((EntityPlayer)entity).addPotionEffect(new PotionEffect(ModPotions.speed_of_squid.id, 8, 0, true));
					}
				}
			}
		}
	}
	
	public boolean consumeEnergy(int energy)
	{
		if (this.energy >= energy)
		{
			this.energy -= energy;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean giveEnergy(int energy)
	{
		if (getMaxEnergyFromTier(tier) - this.energy >= energy)
		{
			this.energy += energy;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getActionRadiusFromTier(int tier)
	{
		switch (tier)
		{
		case 1:
			return 7;  // 1x1 chunks
		case 2:
			return 23; // 3x3 chunks
		case 3:
			return 39; // 4x4 chunks
		case 4:
			return 55; // 5x5 chunks
		}
		return 0;
	}
}