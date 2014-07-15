package oceany.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import oceany.api.OceanyRecipeManager.InfuserRecipeManager;
import cpw.mods.fml.common.registry.GameRegistry;
import danylibs.libs.ItemUtils;
import danylibs.libs.RotationUtils;

public class TileOceanyInfuser extends ModTileOceanyCoreDependant implements ISidedInventory
{
	public static final int usagePerTick = 3;
	
	public double process;

	@Deprecated // TODO Make Oceany Infuser eject stuff (now only manually)
	public boolean isEjecting;
	
	private static final int sizeInventory = 2;
	public ItemStack[] inventory = new ItemStack[sizeInventory];
	public Item lastInput;
	
	@Override
	public void tick(TETickType type)
	{
		if (type == TETickType.CORE_DEPENDANT)
		{
			if (inventory[0] != null && getOutputFromInput(inventory[0]) != null && ItemUtils.compare(inventory[0].getItem(), lastInput))
			{
				if (worldObj.getTileEntity(coreX, coreY, coreZ) instanceof TileOceanyCore)
				{
					if (!worldObj.isRemote)
					{
						if (process < 100)
						{
							TileOceanyCore tile = (TileOceanyCore)worldObj.getTileEntity(coreX, coreY, coreZ);
							if (tile.consumeEnergy(usagePerTick))
							{
								double cost = getEnergyCostFromInput(inventory[0]);
								double progress = usagePerTick / cost * 100;
								process = process + progress;
							}
						}
						else
						{
							ItemStack output = inventory[1] == null ? null : inventory[1].copy();
							if (output == null)
							{
								output = getOutputFromInput(inventory[0]);
								output.stackSize = 0;
							}
							if (ItemUtils.compare(output, getOutputFromInput(inventory[0])))
							{
								output.stackSize++;
								decrStackSize(0, 1);
								setInventorySlotContents(1, output);
								process = 0.00;
							}
						}
					}
				}
			}
			else
			{
				process = 0;
			}
			this.lastInput = inventory[0] == null ? Items.potato : inventory[0].getItem();
			markDirty();
		}
	}
	
	public TileOceanyInfuser()
	{
		super();
		process = 0.00;
		lastInput = Items.potato; // Potato for your convenience
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		process = tag.getDouble("Process");
		inventory = new ItemStack[getSizeInventory()];
		NBTTagList taglist = (NBTTagList)tag.getTag("Items");
		for (int i = 0; i < taglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length)
            {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
		
		NBTTagCompound lastInputData = tag.getCompoundTag("LastInput");
		boolean lastInputData_isBlock = lastInputData.getBoolean("isBlock");
		String lastInputData_modId = lastInputData.getString("modId");
		String lastInputData_itemName = lastInputData.getString("itemName");
		lastInput = lastInputData_isBlock ? Item.getItemFromBlock(GameRegistry.findBlock(lastInputData_modId, lastInputData_itemName)) : GameRegistry.findItem(lastInputData_modId, lastInputData_itemName);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setDouble("Process", process);
		NBTTagList taglist = new NBTTagList();
		
		for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(compound);
                taglist.appendTag(compound);
            }
        }
        tag.setTag("Items", taglist);
        
        NBTTagCompound lastInputData = new NBTTagCompound();
        boolean lastInputData_isBlock = Block.getBlockFromItem(lastInput) != Blocks.air;
        String lastInputData_modId = ItemUtils.getUniqueModName(lastInput);
        String lastInputData_itemName = ItemUtils.getUniqueItemName(lastInput);
        lastInputData.setBoolean("isBlock", lastInputData_isBlock);
        lastInputData.setString("modId"	, lastInputData_modId);
        lastInputData.setString("itemName", lastInputData_itemName);
        tag.setTag("LastInput", lastInputData);
	}
	
	@Override
	public void onDataPacketCustom(NetworkManager net, S35PacketUpdateTileEntity packet) {}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if (side == RotationUtils.getSideIDFromDirection(ForgeDirection.UP))
		{
			return new int[] {0};
		}
		else
		{
			return new int[] {1};
		}
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		if (slot == 0 && side == RotationUtils.getSideIDFromDirection(ForgeDirection.UP))
		{
			if (inventory[slot] == null || (ItemUtils.compare(inventory[slot], stack) && (getInventoryStackLimit() - inventory[slot].stackSize >= stack.stackSize)))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		if (slot == 1 && side != RotationUtils.getSideIDFromDirection(ForgeDirection.UP))
		{
			if (inventory[slot] == null)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void closeInventory() {}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack;
			
			if (this.inventory[slot].stackSize <= amount)
			{
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.inventory[slot].splitStack(amount);
				
				if (this.inventory[slot].stackSize == 0)
				{
					this.inventory[slot] = null;
				}
				
				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public String getInventoryName()
	{
		return "InventoryOceanyInfuser";
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public int getSizeInventory()
	{
		return sizeInventory;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (slot == 0 && getOutputFromInput(stack) != null)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
	}
	
	public ItemStack getOutputFromInput(ItemStack input)
	{
		if (InfuserRecipeManager.instance().recipeExists(input))
		{
			return InfuserRecipeManager.instance().getOutputFromInput(input);
		}
		else
		{
			return null;
		}
	}
	
	public int getEnergyCostFromInput(ItemStack input)
	{
		if (InfuserRecipeManager.instance().recipeExists(input))
		{
			return InfuserRecipeManager.instance().getEnergyUsageFromInput(input);
		}
		else
		{
			return 0;
		}
	}
}