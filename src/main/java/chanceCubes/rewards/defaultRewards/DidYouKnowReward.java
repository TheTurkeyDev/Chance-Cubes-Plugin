package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class DidYouKnowReward implements IChanceCubeReward {

    private List<String> dyk = new ArrayList<>();
    private Random random = new Random();

    public DidYouKnowReward() {
        dyk.add("The nuke reward that says\"May death rain upon them\" is a reference to the Essentials Bukkit plugin.");
        dyk.add("The real reason his name is pickles is because a user from Wyld's Twitch chat suggested the reward.");
        dyk.add("Funwayguy created the original D20 model and animation.");
        dyk.add("Glenn is NOT a reference to the TV show \"The Walking Dead\", but is instead a reference to the streamer Sevadus.");
        dyk.add("");
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Did_You_Know";
    }

    @Override
    public void trigger(Location location, Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor("Chance Cubes");
        meta.setPages("Did you know?\n" + dyk.get(random.nextInt(dyk.size())));
        meta.setTitle("Did you Know?");
        book.setItemMeta(meta);
        Item item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
        item.setItemStack(book);
    }
}
