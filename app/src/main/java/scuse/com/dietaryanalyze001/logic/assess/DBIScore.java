package scuse.com.dietaryanalyze001.logic.assess;

import java.util.HashMap;

import scuse.com.dietaryanalyze001.logic.assess.kinds.ComponentAssess;
import scuse.com.dietaryanalyze001.logic.bean.DayDBIIntakes;

/**
 * Created by Polylanger on 2016/10/20.
 */
public class DBIScore {

    private static final String TAG = "DBIScore";
    private HashMap<String, Integer> kindScores = new HashMap<>();

    public DBIScore(DayDBIIntakes intakes, int energy) {
        for (String kind : DayDBIIntakes.KINDS) {
            int quantity = 0;
            if (intakes.getKinds().containsKey(kind)) {
                quantity = intakes.getKinds().get(kind).intValue();
            }
            AssessFactory factory = new AssessFactory(energy);
            ComponentAssess assess = factory.createComponent(kind);
            int grade = assess.assess(quantity);

            if (grade > 12) {
                grade = 12;
            } else if (grade < -12) {
                grade = -12;
            }

            kindScores.put(kind, grade);
        }
    }

    public HashMap<String, Integer> getKindScores() {
        return kindScores;
    }

    public int getTotalScore() {
        return getHBScore() + getLBScore();
    }

    public int getDQDistance() {
        return getHBScore() - getLBScore();
    }

    public int getHBScore() {
        int hbs = 0;
        for (String kind : DayDBIIntakes.KINDS) {
            if (kindScores.get(kind) > 0) {
                hbs += kindScores.get(kind);
            }
        }
        return hbs;
    }

    public int getLBScore() {
        int lbs = 0;
        for (String kind : DayDBIIntakes.KINDS) {
            if (kindScores.get(kind) < 0) {
                lbs += kindScores.get(kind);
            }
        }
        return lbs;
    }
}
