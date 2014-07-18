package oceany.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemBottomlessBucket extends ModItemBase
{
	public ItemBottomlessBucket()
	{
		super();
		setUnlocalizedName("bottomless_bucket");
		setMaxStackSize(1);
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("Right-click to suck a block of");
		list.add("liquid into the void.");
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("Suck It Up!");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player)
	{
		MovingObjectPosition obj = getMovingObjectPositionFromPlayer(world, player, true);
		if (obj != null && obj.typeOfHit == MovingObjectType.BLOCK)
		{
			if (world.canMineBlock(player, obj.blockX, obj.blockY, obj.blockZ)
			 && player.canPlayerEdit(obj.blockX, obj.blockY, obj.blockZ, obj.sideHit, stack)
			 && world.getBlock(obj.blockX, obj.blockY, obj.blockZ).getMaterial().isLiquid())
			{
				world.setBlockToAir(obj.blockX, obj.blockY, obj.blockZ);
			}
		}
		return stack;
	}
}