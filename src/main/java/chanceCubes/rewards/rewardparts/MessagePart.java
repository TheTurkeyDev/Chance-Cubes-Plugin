package chanceCubes.rewards.rewardparts;

public class MessagePart {

    public static String[] elements = new String[]{"message:S", "delay:I", "serverWide:B", "range:I"};
    private int delay = 0;
    private String message;
    private int range = 32;
    private boolean serverWide = false;

    public MessagePart(String message) {
        this.message = message;
    }

    public int getDelay() {
        return delay;
    }

    public MessagePart setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public int getRange() {
        return range;
    }

    public MessagePart setRange(int range) {
        this.range = range;
        return this;
    }

    public boolean isServerWide() {
        return serverWide;
    }

    public MessagePart setServerWide(boolean serverWide) {
        this.serverWide = serverWide;
        return this;
    }
}