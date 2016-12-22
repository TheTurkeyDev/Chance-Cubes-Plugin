package chanceCubes.rewards.rewardparts;

import org.bukkit.potion.PotionEffect;

public class PotionPart {

    public static String[] elements = new String[]{"potionid:I", "duration:I", "delay:I"};
    private int delay = 0;
    private PotionEffect effect;

    public PotionPart(PotionEffect effect) {
        this.effect = effect;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public PotionEffect getEffect() {
        return effect;
    }
}