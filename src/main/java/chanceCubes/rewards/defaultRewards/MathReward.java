package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.CCubesDamageSource;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MathReward implements IChanceCubeReward, Listener {

    private Map<Player, RewardInfo> inQuestion = new HashMap<>();

    @Override
    public int getChanceValue() {
        return -30;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Math";
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (inQuestion.containsKey(player)) {
            int answer = 0;
            try {
                answer = Integer.parseInt(event.getMessage());
            }
            catch (NumberFormatException e) {
                player.sendMessage("Incorrect!");
            }

            if (inQuestion.get(player).getAnswer() == answer)
                this.timeUp(player, true);
            else
                player.sendMessage("Incorrect!");
            
            event.setCancelled(true);
        }
    }

    private void timeUp(Player player, boolean correct) {
        if (!inQuestion.containsKey(player))
            return;

        RewardInfo info = inQuestion.get(player);
        if (correct) {
            player.sendMessage("Correct!");
        }
        else {
            player.getWorld().createExplosion(player.getLocation(), 1F);
            ((CraftPlayer) player).getHandle().damageEntity(CCubesDamageSource.mathfail, Float.MAX_VALUE);
        }

        for (Entity tnt : info.getTnt())
            tnt.remove();

        for (Location b : info.getBlocks()) {
            BlockState state = b.getBlock().getState();
            state.setType(Material.AIR);
            state.update(true);
        }

        inQuestion.remove(player);

    }

    @Override
    public void trigger(Location location, final Player player) {
        if (inQuestion.containsKey(player))
            return;

        Random random = new Random();
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);

        player.sendMessage("Quick, what's " + num1 + "+" + num2 + "?");

        Location playerPos = player.getLocation();
        List<Location> boxBlocks = new ArrayList<>();
        for (int xx = -2; xx < 3; xx++) {
            for (int zz = -2; zz < 3; zz++) {
                for (int yy = 1; yy < 5; yy++) {
                    if (xx == -2 || xx == 2 || zz == -2 || zz == 2 || yy == 1 || yy == 4) {
                        RewardsUtil.placeBlock(Material.BEDROCK, playerPos.clone().add(xx, yy, zz));
                        boxBlocks.add(new Location(playerPos.getWorld(), playerPos.getX() + xx, playerPos.getY() + yy, playerPos.getZ() + zz));
                    }
                    else if (((xx == -1 || xx == 1) && (zz == -1 || zz == 1) && yy == 2)) {
                        RewardsUtil.placeBlock(Material.GLOWSTONE, playerPos.clone().add(xx, yy, zz));
                        boxBlocks.add(new Location(playerPos.getWorld(), playerPos.getX() + xx, playerPos.getY() + yy, playerPos.getZ() + zz));
                    }
                }
            }
        }

        player.teleport(playerPos.clone().add(0, 2, 0));

        List<Entity> tnt = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Entity entity = player.getWorld().spawnEntity(playerPos.clone().add(0, 1, 0), EntityType.PRIMED_TNT);
            if (entity == null)
                continue;

            TNTPrimed tntEntity = (TNTPrimed) entity;
            tntEntity.setFuseTicks(140);
            player.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 1F, 1F);
        }

        inQuestion.put(player, new RewardInfo(num1 + num2, tnt, boxBlocks));

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> timeUp(player, false), 100);
    }

    private class RewardInfo {

        private int answer;
        private List<Location> blocks;
        private List<Entity> tnt;

        public RewardInfo(int answer, List<Entity> tnt, List<Location> blocks) {
            this.answer = answer;
            this.tnt = tnt;
            this.blocks = blocks;
        }

        public int getAnswer() {
            return answer;
        }

        public List<Location> getBlocks() {
            return blocks;
        }

        public List<Entity> getTnt() {
            return tnt;
        }
    }
}