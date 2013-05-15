package djraus.ch.Compendium.model;

public class Goal {
    private String title;
    private int prizePool;
    private String description;

    public Goal(String title, int prizePool, String description) {
        this.title = title;
        this.prizePool = prizePool;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getPrizePool() {
        return prizePool;
    }

    public String getDescription() {
        return description;
    }
}
