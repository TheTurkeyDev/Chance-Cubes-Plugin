package chanceCubes.rewards.rewardparts;

import org.bukkit.Sound;

public class SoundPart {

    public static String[] elements = new String[]{"sound:S", "delay:I", "serverWide:B", "range:I"};
    private boolean atPlayersLocation = false;
    private int delay = 0;
    private int pitch = 1;
    private int range = 16;
    private boolean serverWide = false;
    private Sound sound;
    private int volume = 1;

    public SoundPart(Sound sound) {
        this.sound = sound;
    }

    public int getDelay() {
        return delay;
    }

    public SoundPart setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getRange() {
        return range;
    }

    public SoundPart setRange(int range) {
        this.range = range;
        return this;
    }

    public Sound getSound() {
        return sound;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isServerWide() {
        return serverWide;
    }

    public SoundPart setServerWide(boolean serverWide) {
        this.serverWide = serverWide;
        return this;
    }

    public boolean playAtPlayersLocation() {
        return atPlayersLocation;
    }

    public SoundPart setAtPlayersLocation(boolean atPlayersLocation) {
        this.atPlayersLocation = atPlayersLocation;
        return this;
    }
}
