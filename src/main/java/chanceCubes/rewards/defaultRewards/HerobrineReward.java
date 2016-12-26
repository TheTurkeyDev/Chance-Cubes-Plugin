package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class HerobrineReward implements IChanceCubeReward {

    private String[] leaveSayings = {"I will be back for you.", "Another day, another time.", "No, you are not ready for my wrath.", "Perhaps tomorrow you will be worthy of my challenge", "I sense that I am needed else where. You escape..... For now....", "If only you were worth my time."};
    private String[] staySayings = {"Today is the day.", "May the other world have mercy on your soul.", "MUWAHAHAHAHAHAHAH", "Time to feast!!", "How fast can your run boy!", "It's a shame this will end so quickly for you.", "My presence alone will be your end"};

    @Override
    public int getChanceValue() {
        return -60;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Herobrine";
    }

    private void schedule(final Location location, final Player player, final int stage, final boolean staying) {
        RewardsUtil.scheduleTask(() -> update(location, player, stage, staying), 40);
    }

    @Override
    public void trigger(Location location, Player player) {
        update(location, player, 0, new Random().nextInt(5) == 1);
    }

    private void update(Location location, Player player, int stage, boolean staying) {
        switch (stage) {
            case 0: {
                RewardsUtil.sendMessageToAllPlayers(location.getWorld(), ChatColor.YELLOW + "Herobrine joined the game.");
                break;
            }
            case 1: {
                if (staying)
                    RewardsUtil.sendMessageToAllPlayers(location.getWorld(), "<Herobrine> " + staySayings[new Random().nextInt(staySayings.length)]);
                else
                    RewardsUtil.sendMessageToAllPlayers(location.getWorld(), "<Herobrine> " + leaveSayings[new Random().nextInt(leaveSayings.length)]);

                break;
            }
            case 2: {
                if (staying) {
                    RewardsUtil.placeBlock(Material.AIR, new MaterialData(Material.AIR), location.clone().add(0, 1, 0));
                    String command = "summon Zombie ~ ~1 ~ {CustomName:\"Herobrine\",CustomNameVisible:1,IsVillager:0,IsBaby:0,CanBreakDoors:1,ArmorItems:[{id:diamond_boots,Count:1,tag:{Unbreakable:1,ench:[{id:0,lvl:5}]}},{id:diamond_leggings,Count:1,tag:{Unbreakable:1,ench:[{id:0,lvl:5}]}},{id:diamond_chestplate,Count:1,tag:{Unbreakable:1,ench:[{id:0,lvl:5}]}},{id:diamond_helmet,Count:1,tag:{Unbreakable:1,ench:[{id:0,lvl:5}]}}],HandItems:[{id:diamond_sword,Count:1,tag:{Unbreakable:1,display:{Name:\"Wrath of Herobrine\"},ench:[{id:16,lvl:5}]}},{}],ArmorDropChances:[0.0F,0.0F,0.0F,0.0F],HandDropChances:[2.0F,0.085F],Attributes:[{Name:generic.maxHealth,Base:500}],Health:500.0f,Glowing:1b}";
                    Bukkit.dispatchCommand((CommandSender) CCubesCore.instance(), command);
                    //TODO still need to work on custom achievements
                    //player.addStat(CCubesAchievements.herobrine);
                }
                else
                    RewardsUtil.sendMessageToAllPlayers(location.getWorld(), ChatColor.YELLOW + "Herobrine left the game.");

                break;
            }
        }

        stage++;

        if (stage < 3)
            schedule(location, player, stage, staying);
    }
}
