package chanceCubes.rewards.rewardparts;

import org.bukkit.Particle;

public class ParticlePart {

    public static String[] elements = new String[]{"particle:I", "x:I", "y:I", "z:I", "delay:I"};
    private int delay = 0;
    private Particle particle;

    public ParticlePart(Particle particle) {
        this.particle = particle;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Particle getParticle() {
        return particle;
    }
}
