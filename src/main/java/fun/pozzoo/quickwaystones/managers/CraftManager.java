package fun.pozzoo.quickwaystones.managers;

import fun.pozzoo.quickwaystones.QuickWaystones;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftManager {
    public void registerRecipes() {
        ShapedRecipe recipeLodestone = new ShapedRecipe(new NamespacedKey(QuickWaystones.getInstance(), "waypoint"), new ItemStack(Material.LODESTONE));
        recipeLodestone.shape("SSS", "SES", "SSS");
        recipeLodestone.setIngredient('S', Material.CHISELED_STONE_BRICKS);
        recipeLodestone.setIngredient('E', Material.REDSTONE_BLOCK);


        ShapedRecipe recipeNametag = new ShapedRecipe(new NamespacedKey(QuickWaystones.getInstance(), "nametag"), new ItemStack(Material.NAME_TAG));
        recipeNametag.shape("  S", " L ", "L  ");
        recipeNametag.setIngredient('S', Material.STRING);
        recipeNametag.setIngredient('L', Material.LEATHER);


        QuickWaystones.getInstance().getServer().addRecipe(recipeLodestone);
        QuickWaystones.getInstance().getServer().addRecipe(recipeNametag);
    }
}
