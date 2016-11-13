package scuse.com.dietaryanalyze001.logic.assess;

import android.util.Log;

import scuse.com.dietaryanalyze001.logic.assess.kinds.AlcoholAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.CerealAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.ComponentAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.DairyAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.EggAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.FishAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.FruitsAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.MeatAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.OilAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.SaltAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.SoybeanAssess;
import scuse.com.dietaryanalyze001.logic.assess.kinds.VegetableAssess;
import scuse.com.dietaryanalyze001.logic.bean.DayDBIIntakes;

/**
 * Created by Polylanger on 2016/10/20.
 */
public class AssessFactory {

    private static final String TAG = "AssessFactory";

    private int energy;

    public AssessFactory(int energy) {
        this.energy = energy;
    }

    public ComponentAssess createComponent(String kind) {
        ComponentAssess component = null;
        switch (kind) {
            case DayDBIIntakes.CEREALS:
                component = new CerealAssess(energy);
                break;
            case DayDBIIntakes.VEGETABLE:
                component = new VegetableAssess(energy);
                break;
            case DayDBIIntakes.FRUITS:
                component = new FruitsAssess(energy);
                break;
            case DayDBIIntakes.DAIRY:
                component = new DairyAssess(energy);
                break;
            case DayDBIIntakes.SOYBEAN:
                component = new SoybeanAssess(energy);
                break;
            case DayDBIIntakes.MEAT:
                component = new MeatAssess(energy);
                break;
            case DayDBIIntakes.FISH:
                component = new FishAssess(energy);
                break;
            case DayDBIIntakes.EGG:
                component = new EggAssess(energy);
                break;
            case DayDBIIntakes.OIL:
                component = new OilAssess(energy);
                break;
            case DayDBIIntakes.SALT:
                component = new SaltAssess(energy);
                break;
            case DayDBIIntakes.ALCOHOL:
                component = new AlcoholAssess(energy);
                break;
            default:
                Log.w(TAG, "createComponent: Can't find kind: " + kind);
                break;
        }
        return component;
    }
}