package fun.pozzoo.quickwaystones.managers;

import fun.pozzoo.quickwaystones.QuickWaystones;
import fun.pozzoo.quickwaystones.data.WaystoneData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class DataManager {
    private File file;
    private YamlConfiguration config;
    private YamlConfiguration configOverwrite;
    private Set<String> keys;

    public DataManager() {
        keys = new HashSet<>();
        checkFile();
    }

    private void checkFile() {
        file = new File(QuickWaystones.getInstance().getDataFolder(), "waystones.yml");

        if (!file.exists()) {
            QuickWaystones.getInstance().getLogger().info("Creating waystones.yml");
            QuickWaystones.getInstance().saveResource("waystones.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);

        if (config.getKeys(true).isEmpty()) return;

        keys = Objects.requireNonNull(config.getConfigurationSection("Waystones.")).getKeys(false);
    }

    public void loadWaystonesData() {
        try {
            config.load(file);

            for (String key : keys) {
                Location location = new Location(
                        Bukkit.getWorld(config.getString("Waystones." + key + ".location.world")),
                        config.getDouble("Waystones." + key + ".location.x"),
                        config.getDouble("Waystones." + key + ".location.y"),
                        config.getDouble("Waystones." + key + ".location.z"),
                        (float) config.getDouble("Waystones." + key + ".location.yaw"),
                        (float) config.getDouble("Waystones." + key + ".location.pitch")
                );
                WaystoneData waystoneData = new WaystoneData(key, location, config.getString("Waystones." + key + ".owner"));
                QuickWaystones.getWaystonesMap().put(waystoneData.getLocation(), waystoneData);
            }
        } catch (InvalidConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveWaystoneData(Collection<WaystoneData> waystones) {
        configOverwrite = new YamlConfiguration();

        for (WaystoneData waystone : waystones) {
            Location location = waystone.getLocation();
            configOverwrite.set("Waystones." + waystone.getName() + ".location.world", location.getWorld().getName());
            configOverwrite.set("Waystones." + waystone.getName() + ".location.x", location.getX());
            configOverwrite.set("Waystones." + waystone.getName() + ".location.y", location.getY());
            configOverwrite.set("Waystones." + waystone.getName() + ".location.z", location.getZ());
            configOverwrite.set("Waystones." + waystone.getName() + ".location.yaw", location.getYaw());
            configOverwrite.set("Waystones." + waystone.getName() + ".location.pitch", location.getPitch());
            configOverwrite.set("Waystones." + waystone.getName() + ".owner", waystone.getOwner());
        }

        save();
    }

    public void save() {
        QuickWaystones.getInstance().saveResource("waystones.yml", true);

        try {
            configOverwrite.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
