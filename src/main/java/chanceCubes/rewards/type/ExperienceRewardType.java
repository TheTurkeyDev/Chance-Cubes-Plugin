package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.ExperiencePart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

public class ExperienceRewardType extends BaseRewardType<ExperiencePart> {

    public ExperienceRewardType(ExperiencePart... levels) {
        super(levels);
    }

    @Override
    public void trigger(final ExperiencePart levels, Location location, final Player player) {
        if (levels.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> triggerExperience(levels, location), levels.getDelay());
        else
            triggerExperience(levels, location);
    }

    public void triggerExperience(ExperiencePart levels, Location location) {
        for (int i = 0; i < levels.getNumberofOrbs(); i++) {
            ExperienceOrb ent = (ExperienceOrb) location.getWorld().spawnEntity(location, EntityType.EXPERIENCE_ORB);
            ent.setExperience(levels.getAmount() / levels.getNumberofOrbs());
        }
    }
}
