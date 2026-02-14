/*
 * This class was created by <kuzuanpa>. It is a part of kGT6NEIAddon.
 * Get the Source Code in github:
 * https://github.com/kuzuanpa/kGT6NEIAddon
 *
 * kGT6NEIAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kGT6NEIAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.kGT6NEIAddon;

import cn.kuzuanpa.kGT6NEIAddon.nei.CrucibleNEIHandler;
import cn.kuzuanpa.kGT6NEIAddon.nei.MaterialShapeNEIHandler;
import cn.kuzuanpa.kGT6NEIAddon.nei.NEIIMCSender;
import codechicken.nei.api.API;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import gregapi.api.Abstract_Mod;
import gregapi.api.Abstract_Proxy;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ModData;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;

@Mod(modid = kGT6NEIAddon.MOD_ID, version = kGT6NEIAddon.VERSION)
public class kGT6NEIAddon extends Abstract_Mod {
    public static final String MOD_ID = "kgt6neiaddon";
    public static final String MOD_NAME = "kGT6NEIAddon";
    public static final String VERSION = "1.1.1";
    public static ModData MOD_DATA = new ModData("kgt6neiaddon", "kGT6NEIAddon");

    @SidedProxy(clientSide = "cn.kuzuanpa.kGT6NEIAddon.clientProxy",
            serverSide = "cn.kuzuanpa.kGT6NEIAddon.commonProxy")
    public static Abstract_Proxy PROXY;

    public String getModID() {
        return "kgt6neiaddon";
    }

    public String getModName() {
        return "kGT6NEIAddon";
    }

    public String getModNameForLog() {
        return "kGT6NEIAddon";
    }

    public Abstract_Proxy getProxy() {
        return PROXY;
    }

    // Do not change these 7 Functions. Just keep them this way.
    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPreLoad(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {
        onModPreInit(aEvent);
    }


    @cpw.mods.fml.common.Mod.EventHandler
    public final void onLoad(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
        onModInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPostLoad(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
        onModPostInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarting(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        onModServerStarting(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarted(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        onModServerStarted(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopping(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        onModServerStopping(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopped(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        onModServerStopped(aEvent);
    }

    @Override
    public void onModPreInit2(FMLPreInitializationEvent aEvent) {
    }

    @Override
    public void onModInit2(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
    }

    @Override
    public void onModFinalInit(FMLInitializationEvent aEvent) {
        super.onModFinalInit(aEvent);

        API.registerRecipeHandler(new CrucibleNEIHandler());
        API.registerRecipeHandler(new MaterialShapeNEIHandler());


        API.registerUsageHandler(new CrucibleNEIHandler());
        API.registerUsageHandler(new MaterialShapeNEIHandler());

        new NEIIMCSender().run();
    }
    @Override
    public void onModPostInit2(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
    }

    @Override
    public void onModServerStarting2(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        // Insert your ServerStarting Code here and not above
    }

    @Override
    public void onModServerStarted2(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        // Insert your ServerStarted Code here and not above
    }

    @Override
    public void onModServerStopping2(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        // Insert your ServerStopping Code here and not above
    }

    @Override
    public void onModServerStopped2(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        // Insert your ServerStopped Code here and not above
    }
}
