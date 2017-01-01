package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.ParticlePart;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParticleEffectRewardType extends BaseRewardType<ParticlePart> {

    public ParticleEffectRewardType(ParticlePart... effects) {
        super(effects);
    }

    @Override
    public void trigger(ParticlePart part, Location location, Player player) {
        location.getWorld().spawnParticle(part.getParticle(), location.clone().add(Math.random(), Math.random(), Math.random()), 1);
    }
}
