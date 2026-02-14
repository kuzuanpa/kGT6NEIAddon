package cn.kuzuanpa.kGT6NEIAddon.util;

import gregapi.data.OP;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CodeUtil {

    public static ItemStack getMaterialItem(OreDictMaterial material) {
        ItemStack dust = OP.dust.mat(material,1);
        ItemStack tube = OP.chemtube.mat(material,1);
        ItemStack book = material.mDictionaryBook;
        return dust!=null?dust:tube!=null?tube:book!=null?book :new ItemStack(Items.paper,1);
    }
}
