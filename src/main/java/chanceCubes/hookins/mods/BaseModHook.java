package chanceCubes.hookins.mods;

public abstract class BaseModHook {

    public String modId;

    private boolean enabled = false;

    public BaseModHook(String modId) {
        this.modId = modId;
        this.enabled = true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract void loadRewards();
}
