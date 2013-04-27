package uk.co.minebook.mod.items;

import java.awt.List;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Profile extends Item {

	public Profile(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("Minebook Profile");
	}

	
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){ //Called when an item is right clicked on a block
		return false;
	}

	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){ //Additional info (eg. the names of music discs)
		l.add("This staff is magical!");
	}
	
	public EnumRarity getRarity(ItemStack is){ //The colour of the item name (eg. with golden apples)
		return EnumRarity.epic;
	}

	@Override
	public int getDamageVsEntity(Entity e){ //The amount of damage dealt to an entity when using this item as a weapon
		return 0;
	}

	public boolean hasEffect(ItemStack is){ //Adds an effect to the item (eg. golden apples)
		return true;
	}

}
