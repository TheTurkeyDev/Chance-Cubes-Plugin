package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.SoundPart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SoundRewardType extends BaseRewardType<SoundPart> {

    public SoundRewardType(SoundPart... sounds) {
        super(sounds);
    }

    @Override
    public void trigger(final SoundPart sound, Location location, final Player player) {
        if (sound.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> playSound(sound, location, player), sound.getDelay());
        else
            playSound(sound, location, player);
    }

    private void playSound(SoundPart sound, Location location , Player player) {
        if (sound.playAtPlayersLocation())
            player.playSound(player.getLocation(), sound.getSound(), sound.getVolume(), sound.getPitch());
        else
            player.playSound(location, sound.getSound(), sound.getVolume(), sound.getPitch());
    }
}
