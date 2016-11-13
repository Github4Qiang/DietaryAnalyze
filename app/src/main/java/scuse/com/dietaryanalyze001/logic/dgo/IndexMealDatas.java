package scuse.com.dietaryanalyze001.logic.dgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.logic.utils.StringUtils;

public class IndexMealDatas {

    private static final String[] INDEX = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private HashMap<String, ArrayList<Dishes>> hashDatas;
    private ArrayList<Object> arrayDatas;

    public IndexMealDatas() {
        hashDatas = new HashMap<>();
        arrayDatas = new ArrayList<>();
    }

    public boolean add(Dishes data) {
        boolean res = insertData(data);
        notifyDataChanged();
        return res;
    }

    private boolean insertData(Dishes data) {
        String bigFirstLetter = StringUtils.getBigFirstLetter(data.getName());
        if (hashDatas.containsKey(bigFirstLetter)) {
            ArrayList<Dishes> letterDatas = hashDatas.get(bigFirstLetter);
            letterDatas.add(data);
            return false;
        } else {
            ArrayList<Dishes> letterDatas = new ArrayList<>();
            letterDatas.add(data);
            hashDatas.put(bigFirstLetter, letterDatas);
            return true;
        }
    }

    public void addAll(List<Dishes> datas) {
        for (Dishes meal : datas) {
            insertData(meal);
        }
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        arrayDatas.clear();
        for (String c : INDEX) {
            ArrayList<Dishes> timeDatas = hashDatas.get(c);
            if (timeDatas != null) {
                arrayDatas.add(c);
                arrayDatas.addAll(timeDatas);
            }
        }
    }

    public boolean isTitle(int position) {
        if (arrayDatas.get(position) instanceof String) {
            return true;
        }
        return false;
    }

    public int getClosestTitle(String s) {
        int res = 0;
        for (int i = 0; i < arrayDatas.size(); i++) {
            if (arrayDatas.get(i) instanceof String) {
                String title = (String) arrayDatas.get(i);
                if (s.charAt(0) == title.charAt(0)) {
                    return i;
                } else if (s.charAt(0) < title.charAt(0)) {
                    return res;
                } else {
                    res = i;
                }
            }
        }
        return res;
    }

    public HashMap<String, ArrayList<Dishes>> getHashDatas() {
        return hashDatas;
    }

    public void setHashDatas(HashMap<String, ArrayList<Dishes>> hashDatas) {
        this.hashDatas = hashDatas;
    }

    public ArrayList<Object> getArrayDatas() {
        return arrayDatas;
    }

    public void setArrayDatas(ArrayList<Object> arrayDatas) {
        this.arrayDatas = arrayDatas;
    }

    @Override
    public String toString() {
        return "IndexMealDatas{" +
                "arrayDatas=" + arrayDatas + "\n" +
                ", hashDatas=" + hashDatas + "\n" +
                '}';
    }
}
