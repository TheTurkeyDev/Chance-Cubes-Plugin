package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

public class BookOfMemesReward implements IChanceCubeReward {

    private List<String> memes = new ArrayList<>();
    private Random random = new Random();

    public BookOfMemesReward() {
        memes.add("Sodium, atomic number 11, was first isolated by Peter Dager in 1807. A chemical component of salt, he named it Na in honor of the saltiest region on earth, North America.");
        memes.add("(╯°□°）╯︵ ┻━┻ \n ༼ᕗຈل͜ຈ༽ᕗ RAISE YOUR DONGERS ༼ᕗຈل͜ຈ༽ᕗ");
        memes.add("Darude- status \n ☐ Not Sandstorm \n ☑ Sandstorm");
        memes.add("( ͡° ͜ʖ ͡°) Every 60 seconds in Africa, a minute passes. Together we can stop this. Please spread the word ( ͡° ͜ʖ ͡°) ");
        memes.add("YESTERDAY YOU SAID TOMMOROW, Don't let your dreams be memes, Don't meme your dreams be beams, Jet fuel won't melt tomorrow's memes, DON'T LET YOUR STEEL MEMES BE JET DREAMS");
        memes.add("If the human body is 75% water, how can you be 100% salt?");
        memes.add(" ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ \n Sorry, I've dropped my bag of Doritos™ brand chips▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ► ▼ ◄ ◄ ▲▲ ► ▼ ◄▼ ◄ ◄ ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ► ▼ ◄ ▲ ►");
        memes.add("Hey Chat....... \n \n \n \n 123");
        memes.add("O-oooooooooo AAAAE-A-A-I-A-U- JO-oooooooooooo AAE-O-A-A-U-U-A- E-eee-ee-eee AAAAE-A-E-I-E-A-JO-ooo-oo-oo-oo EEEEO-A-AAA-AAAA \n O-oooooooooo AAAAE-A-A-I-A-U- JO-oooooooooooo AAE-O-A-A-U-U-A- E-eee-ee-eee AAAAE-A-E-I-E-A-JO-ooo-oo-oo-oo EEEEO-A-AAA-AAAA");
        memes.add("Did you just assume this books meme? #Triggered");
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Book_Of_Memes";
    }

    @Override
    public void trigger(Location location, Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor("Chance Cubes");
        meta.setGeneration(Generation.ORIGINAL);
        meta.setPages(Collections.singletonList(memes.get(random.nextInt(memes.size()))));
        meta.setTitle("Book of Memes");
        book.setItemMeta(meta);
        location.getWorld().dropItem(location, book);
    }
}
