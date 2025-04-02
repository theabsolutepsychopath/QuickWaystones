package fun.pozzoo.quickwaystones.events;

import fun.pozzoo.quickwaystones.QuickWaystones;
import fun.pozzoo.quickwaystones.data.WaystoneData;
import fun.pozzoo.quickwaystones.gui.WaystoneGUI;
import fun.pozzoo.quickwaystones.utils.StringUtils;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnPlayerInteract implements Listener {
    private final QuickWaystones plugin;

    private static final List<Vector> DIRECTIONS = Arrays.asList(
            new Vector(1, 0, 0),    // East
            new Vector(1, 0, 1),    // Southeast
            new Vector(0, 0, 1),    // South
            new Vector(-1, 0, 1),   // Southwest
            new Vector(-1, 0, 0),   // West
            new Vector(-1, 0, -1),  // Northwest
            new Vector(0, 0, -1),   // North
            new Vector(1, 0, -1)    // Northeast
    );

    private static final Map<Vector, String> DIRECTION_NAMES = new HashMap<Vector, String>() {{
        put(new Vector(1, 0, 0), "East");
        put(new Vector(1, 0, 1), "Southeast");
        put(new Vector(0, 0, 1), "South");
        put(new Vector(-1, 0, 1), "Southwest");
        put(new Vector(-1, 0, 0), "West");
        put(new Vector(-1, 0, -1), "Northwest");
        put(new Vector(0, 0, -1), "North");
        put(new Vector(1, 0, -1), "Northeast");
    }};

    public OnPlayerInteract(QuickWaystones plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.LODESTONE) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getItem() == null) {
            if (!QuickWaystones.getWaystonesMap().containsKey(block.getLocation())) {
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.sendMessage(StringUtils.formatString("<gold>" + "Successfully created a waystone! Please craft a name tag and give this point a name."));
                QuickWaystones.getWaystonesMap().put(block.getLocation(), new WaystoneData(block.getLocation(), player.getName()));
                return;
            }
            else {
                if (player.isSneaking()) {
                    WaystoneData waystone = QuickWaystones.getWaystonesMap().get(block.getLocation());
                    if (waystone != null && waystone.getOwner().equals(player.getName())) {
                        Vector currentDirection = waystone.getFacingDirection();
                        int currentIndex = DIRECTIONS.indexOf(currentDirection);
                        int nextIndex = (currentIndex + 1) % DIRECTIONS.size();
                        Vector nextDirection = DIRECTIONS.get(nextIndex);
                        waystone.setFacingDirection(nextDirection);
                        String directionName = DIRECTION_NAMES.get(nextDirection);
                        player.sendMessage(StringUtils.formatString("<gold>Waystone teleport facing direction has been updated to " + directionName + "!"));
                        return;
                    }
                }
                WaystoneGUI.runGUI(player);
            }
        }

        else if (event.getItem().getType() == Material.NAME_TAG) {
            if (QuickWaystones.getWaystonesMap().containsKey(block.getLocation())) {
                TextComponent textComponent = (TextComponent) event.getItem().getItemMeta().displayName();

                if (textComponent == null) return;

                QuickWaystones.getWaystonesMap().get(block.getLocation()).setName(textComponent.content());
                player.sendMessage(StringUtils.formatString("<gold>" + "Waystone name has been set to " + textComponent.content()));

            }
        }
        else {
            WaystoneGUI.runGUI(player);
        }
    }
}
