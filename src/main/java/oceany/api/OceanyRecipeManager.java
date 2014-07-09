package oceany.api;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import oceany.Oceany;
import oceany.libs.ItemUtils;

public class OceanyRecipeManager
{
	public static class InfuserRecipeManager implements IRecipeManager
	{
		private static final HashMap<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();
		private static final HashMap<ItemStack, Integer> energies = new HashMap<ItemStack, Integer>();
		
		private static final InfuserRecipeManager instance = new InfuserRecipeManager();
		
		public static InfuserRecipeManager instance()
		{
			return instance;
		}
		
		@Override
		public void addRecipe(ItemStack input, ItemStack output, int energy)
		{
			input.stackSize = 1;
			output.stackSize = 1;
			recipes.put(input, output);
			energies.put(input, energy);
		}
		
		@Override
		public boolean recipeExists(ItemStack input)
		{
			Iterator<ItemStack> iinput = recipes.keySet().iterator();
			while (iinput.hasNext())
			{
				ItemStack that = iinput.next();
				if (ItemUtils.compare(that, input) && that.getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(that, input))
				{
					return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean recipeExistsFor(ItemStack output)
		{
			Iterator<ItemStack> ioutput = recipes.keySet().iterator();
			while (ioutput.hasNext())
			{
				ItemStack that = ioutput.next();
				if (ItemUtils.compare(that, output) && that.getItemDamage() == output.getItemDamage() && ItemStack.areItemStackTagsEqual(that, output))
				{
					return true;
				}
			}
			return false;
		}
		
		@Override
		public int getEnergyUsageFromInput(ItemStack input)
		{
			Iterator<ItemStack> iinput = recipes.keySet().iterator();
			while (iinput.hasNext())
			{
				ItemStack that = iinput.next();
				if (ItemUtils.compare(that, input) && that.getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(that, input))
				{
					return energies.get(that);
				}
			}
			Oceany.logger.error("No energy usage value found with given itemstack (input). Most likely it's add-on problem. Report to mod author!");
			Throwable t = new NoSuchFieldException("No energy usage value found with given itemstack (input)");
			t.printStackTrace();
			return 0;
		}
		
		@Override
		public ItemStack getOutputFromInput(ItemStack input)
		{
			Iterator<ItemStack> iinput = recipes.keySet().iterator();
			while (iinput.hasNext())
			{
				ItemStack that = iinput.next();
				if (ItemUtils.compare(that, input) && that.getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(that, input))
				{
					return recipes.get(that);
				}
			}
			Oceany.logger.error("No recipe found with given itemstack (input). Most likely it's add-on problem. Report to mod author!");
			Throwable t = new NoSuchFieldException("No recipe found with given itemstack (input)");
			t.printStackTrace();
			return null;
		}
	}
	
	public static interface IRecipeManager
	{
		public ItemStack getOutputFromInput(ItemStack input);
		
		public int getEnergyUsageFromInput(ItemStack input);
		
		public boolean recipeExists(ItemStack input);
		
		public boolean recipeExistsFor(ItemStack output);
		
		public void addRecipe(ItemStack input, ItemStack output, int energy);
	}
}