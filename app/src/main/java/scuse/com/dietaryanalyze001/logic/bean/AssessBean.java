package scuse.com.dietaryanalyze001.logic.bean;

public class AssessBean {
    private int quantity;
    private int score;

    public AssessBean(int quantity, int score) {
        this.quantity = quantity;
        this.score = score;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getScore() {
        return score;
    }
}
