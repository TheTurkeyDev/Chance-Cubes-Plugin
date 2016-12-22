package chanceCubes.rewards.rewardparts;

public class ExperiencePart {

    public static String[] elements = new String[]{"experienceAmount:I", "delay:I", "numberOfOrbs:I"};

    private int amount;
    private int delay = 0;
    private int orbs = 1;

    public ExperiencePart(int amount) {
        this.amount = amount;
    }

    public ExperiencePart(int amount, int delay) {
        this.amount = amount;
        this.delay = delay;
    }

    public int getAmount() {
        return amount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getNumberofOrbs() {
        return orbs;
    }

    public ExperiencePart setNumberofOrbs(int orbs) {
        this.orbs = orbs;
        return this;
    }
}
