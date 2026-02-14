package cn.kuzuanpa.kGT6NEIAddon.nei;

import cpw.mods.fml.common.event.FMLInterModComms;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.IL;
import gregapi.data.MD;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.util.ST;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

public class NEIIMCSender implements Runnable{
    @Override
    public void run() {
        putCompactEntries();
        for (Recipe.RecipeMap tMap : Recipe.RecipeMap.RECIPE_MAP_LIST) if (tMap.mNEIAllowed) {
            sortMachines(tMap.mRecipeMachineList);

            String stackName = ST.regMeta(tMap.mRecipeMachineList.isEmpty() ? ST.make(Blocks.lit_furnace, 1, 0) : tMap.mRecipeMachineList.get(0));
            sendHandler(tMap.mNameNEI,getLeastPriority(stackName));

            for (ItemStack aStack : tMap.mRecipeMachineList) if (ST.valid(aStack)) {
                sendCatalyst(tMap.mNameNEI,ST.regMeta(aStack) ,getSortedPriority(aStack));
            }
        }
        mCatalystPriority = null;
        removeCompactEntries();
    }

    /**Because GT6 put these at PostInit, It's too late and IMCMessage won't be accepted by NEI.**/
    public void putCompactEntries(){
        RM.Trees     .mRecipeMachineList.add(IL.Bag_Loot_Sapling.get(1));
        RM.Hammer    .mRecipeMachineList.add(ToolsGT.sMetaTool.make(ToolsGT.HARDHAMMER));
        RM.Chisel    .mRecipeMachineList.add(ToolsGT.sMetaTool.make(ToolsGT.CHISEL));
        RM.Chisel    .mRecipeMachineList.add(ToolsGT.sMetaTool.make(ToolsGT.POCKET_CHISEL));
        RM.ToolHeads .mRecipeMachineList.add(ToolsGT.sMetaTool.make(ToolsGT.PICKAXE));
        RM.DidYouKnow.mRecipeMachineList.add(ToolsGT.sMetaTool.make(ToolsGT.MAGNIFYING_GLASS));

        RM.ByProductList.mRecipeMachineList.add(ST.make(Items.cauldron, 1, 0));
    }
    public void removeCompactEntries(){
        RM.Trees     .mRecipeMachineList.clear();
        RM.Hammer    .mRecipeMachineList.clear();
        RM.Chisel    .mRecipeMachineList.clear();
        RM.ToolHeads .mRecipeMachineList.clear();
        RM.DidYouKnow.mRecipeMachineList.clear();

        RM.ByProductList.mRecipeMachineList.clear();
    }

    protected HashMap<String, Integer> mCatalystPriority = new HashMap<>();

    public void sortMachines(List<ItemStack> stackList){
        mCatalystPriority = new HashMap<>();
        HashMap<String, Double> priorityTmp = new HashMap<>();
        for (ItemStack stack : stackList) {
            MultiTileEntityRegistry reg = MultiTileEntityRegistry.getRegistry(ST.id(stack));
            if(reg == null || reg.getClassContainer(stack) == null) continue;
            NBTTagCompound tag = reg.getClassContainer(stack).mParameters;
            if(tag == null)continue;

            if(tag.hasKey(NBT_INPUT_MAX))priorityTmp.put(ST.regMeta(stack), (double) tag.getLong(NBT_INPUT_MAX));
            else if(tag.hasKey(NBT_INPUT))priorityTmp.put(ST.regMeta(stack), (double) tag.getLong(NBT_INPUT));
            else if(tag.hasKey(NBT_OUTPUT_MAX))priorityTmp.put(ST.regMeta(stack), (double) tag.getLong(NBT_OUTPUT_MAX));
            else if(tag.hasKey(NBT_OUTPUT))priorityTmp.put(ST.regMeta(stack), (double) tag.getLong(NBT_OUTPUT));
            else if(tag.hasKey(NBT_MATERIAL))priorityTmp.put(ST.regMeta(stack), OreDictMaterial.get(tag.getString(NBT_MATERIAL)).mMeltingPoint * 1.25);
        }
        mCatalystPriority = sortMap(priorityTmp);
    }
    public static HashMap<String, Integer> sortMap(HashMap<String, Double> originalMap) {
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(originalMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> sortedIndices = new HashMap<>();

        int index = entryList.size();
        for (Map.Entry<String, Double> entry : entryList) {
            sortedIndices.put(entry.getKey(), index--);
        }

        return sortedIndices;
    }

    public String getLeastPriority(String orElse){
        if(mCatalystPriority==null||mCatalystPriority.isEmpty())return orElse;
        for (Map.Entry<String, Integer> entry : mCatalystPriority.entrySet()) {
            if(entry.getValue() == mCatalystPriority.size())return entry.getKey();
        }
        return orElse;
    }

    public int getSortedPriority(ItemStack stack){
        if(mCatalystPriority==null)return 0;
        Integer i = mCatalystPriority.get(ST.regMeta(stack));
        if(i == null)return 0;
        return i;
    }


    private static void sendHandler(String aName, String aBlock) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", MD.GT.mName);
        aNBT.setString("modId", MD.GT.mID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 135);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", 1);
        aNBT.setInteger("yShift", 0);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }
}
