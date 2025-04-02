package fun.pozzoo.quickwaystones;

import fun.pozzoo.quickwaystones.data.WaystoneData;
import fun.pozzoo.quickwaystones.events.OnBlockBreak;
import fun.pozzoo.quickwaystones.events.OnPlayerInteract;
import fun.pozzoo.quickwaystones.managers.CraftManager;
import fun.pozzoo.quickwaystones.managers.DataManager;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class QuickWaystones extends JavaPlugin {
    private static QuickWaystones plugin;
    private DataManager dataManager;
    private static Map<Location, WaystoneData> waystonesMap;
    private static int lastWaystoneID = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        CraftManager craftManager = new CraftManager();
        craftManager.registerRecipes();

        new OnPlayerInteract(plugin);
        new OnBlockBreak(plugin);

        waystonesMap = new HashMap<>();

        dataManager = new DataManager();
        dataManager.loadWaystonesData();

        lastWaystoneID = waystonesMap.size();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dataManager.saveWaystoneData(waystonesMap.values());
    }

    public static QuickWaystones getInstance() {
        return plugin;
    }

    public static int getAndIncrementLastWaystoneID() {
        lastWaystoneID++;
        return lastWaystoneID;
    }

    public static Map<Location, WaystoneData> getWaystonesMap() {
        return waystonesMap;
    }
}
