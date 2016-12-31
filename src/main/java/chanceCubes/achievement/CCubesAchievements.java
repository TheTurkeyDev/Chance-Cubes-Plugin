package chanceCubes.achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.ChatHoverable;
import net.minecraft.server.v1_10_R1.ChatHoverable.EnumHoverAction;
import net.minecraft.server.v1_10_R1.ChatModifier;
import net.minecraft.server.v1_10_R1.EnumChatFormat;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CCubesAchievements {

    //TODO add command to manually give and take achievements
    private static final Map<UUID, List<CCubesAchievement>> unlockedAchievements = new HashMap<>();
    public static CCubesAchievement giantChanceCube;
    public static CCubesAchievement chanceIcosahedron;
    public static CCubesAchievement herobrine;
    public static CCubesAchievement itsALie;
    public static CCubesAchievement lonelyDirt;
    public static CCubesAchievement wither;

    public static void loadAchievements() {
        chanceIcosahedron = new CCubesAchievement("chanceIcosahedron", "Disco Ball!", "Open a Chance Icosahedron");
        giantChanceCube = new CCubesAchievement("giantChanceCube", "BWWWWAAAAAMMMMM!", "Break a Giant Chance Cube");
        lonelyDirt = new CCubesAchievement("lonelyDirt", "Lonely Dirt", "Obtain the Lonely Piece of Dirt");
        wither = new CCubesAchievement("wither", "It's Real??", "Have the Kiwi wither actually spawn");
        herobrine = new CCubesAchievement("herobrine", "He's Real??", "Have the Herobrine actually spawn");
        itsALie = new CCubesAchievement("itsALie", "It's a Lie!", "Yes, the cake is a lie");
    }

    public static void award(Player player, CCubesAchievement achievement)
    {
        UUID uuid = player.getUniqueId();
        List<CCubesAchievement> list = unlockedAchievements.get(uuid);
        if (list == null)
            list = new ArrayList<>();

        if (list.contains(achievement))
            return;

        list.add(achievement);
        unlockedAchievements.put(uuid, list);
        Bukkit.getOnlinePlayers().forEach(p -> {
            ChatComponentText description = new ChatComponentText(achievement.getDescription());
            description.getChatModifier().setItalic(false);
            ChatComponentText achievementText = new ChatComponentText("Achievement\n");
            achievementText.getChatModifier().setItalic(true).setColor(EnumChatFormat.WHITE);
            achievementText.addSibling(description);
            ChatComponentText name = new ChatComponentText(achievement.getName() + "\n");
            name.getChatModifier().setColor(EnumChatFormat.GREEN);
            name.addSibling(achievementText);
            ChatModifier achievementMod = new ChatModifier();
            achievementMod.setColor(EnumChatFormat.GREEN).setChatHoverable(new ChatHoverable(EnumHoverAction.SHOW_TEXT, name));
            ChatComponentText achievementName = new ChatComponentText("[" + achievement.getName() + "]");
            achievementName.setChatModifier(achievementMod);
            IChatBaseComponent message = new ChatComponentText(player.getName() + " has just earned the achievement ").addSibling(achievementName);
            ((CraftPlayer) p).getHandle().sendMessage(message);
        });
    }
}