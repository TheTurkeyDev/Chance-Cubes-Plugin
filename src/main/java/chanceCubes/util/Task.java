package chanceCubes.util;

public abstract class Task {

    public int delayLeft;
    public String name;

    public Task(String name, int delay) {
        this.name = name;
        this.delayLeft = delay;
    }

    public abstract void callback();

    public boolean tickTask() {
        this.delayLeft--;
        return this.delayLeft <= 0;
    }
}
