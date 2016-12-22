package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.CCubesDamageSource;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class QuestionsReward implements IChanceCubeReward, Listener {

    private Map<Player, String> inQuestion = new HashMap<>();
    private List<SimpleEntry<String, String>> questionsAndAnswers = new ArrayList<>();

    public QuestionsReward() {
        this.addQuestionAnswer("What is the username of the creator of Chance Cubes?", "Turkey -or- Turkey2349");
        this.addQuestionAnswer("How many sides does the sparkly, shiny, colorful, spinny Chance Cube have?", "20");
        this.addQuestionAnswer("What is 9 + 10", "19 -or- 21");
        this.addQuestionAnswer("What year was minecraft officially released", "2011");
    }

    public void addQuestionAnswer(String q, String a) {
        questionsAndAnswers.add(new SimpleEntry<>(q, a));
    }

    @Override
    public int getChanceValue() {
        return -30;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Question";
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (inQuestion.containsKey(player)) {
            String answer = event.getMessage();
            boolean correct = false;
            for (String s : inQuestion.get(player).split("-or-"))
                if (s.trim().equalsIgnoreCase(answer.trim()))
                    correct = true;

            this.timeUp(player, correct);
            event.setCancelled(true);
        }
    }

    private void timeUp(Player player, boolean correct) {
        if (!inQuestion.containsKey(player))
            return;

        if (correct) {
            player.sendMessage("Correct!");
        }
        else {
            player.sendMessage("Incorrect! The answer was " + this.inQuestion.get(player));
            player.getWorld().createExplosion(player.getLocation(), 1.0F);
            ((CraftPlayer) player).getHandle().damageEntity(CCubesDamageSource.questionfail, Float.MAX_VALUE);
        }

        inQuestion.remove(player);

    }

    @Override
    public void trigger(Location location, final Player player) {
        if (inQuestion.containsKey(player))
            return;

        int question = new Random().nextInt(questionsAndAnswers.size());

        player.sendMessage(questionsAndAnswers.get(question).getKey());
        player.sendMessage("You have 45 seconds to answer! (Answer is not case sensitive)");

        inQuestion.put(player, questionsAndAnswers.get(question).getValue());

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> timeUp(player, false), 100);
    }
}