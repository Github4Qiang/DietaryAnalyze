package scuse.com.dietaryanalyze001.logic.dgo;

import java.util.ArrayList;
import java.util.HashMap;

import scuse.com.dietaryanalyze001.logic.bean.DiaryMealBean;

public class DiaryDatas {

    public static final String[] GET_TIME = {"早餐", "上午加餐", "午餐", "下午加餐", "晚餐",
            "夜宵"};

    ArrayList<Object> arrayDatas;
    HashMap<String, ArrayList<DiaryMealBean>> hashDatas;

    public DiaryDatas() {
        this.arrayDatas = new ArrayList<>();
        this.hashDatas = new HashMap<>();
    }

//    public static void main(String[] args) {
//        Downloader downloader = new Downloader();
//        Document document = downloader.download("http://www.haodou.com/s?wd=红烧肉&tp=recipe");
//        PSHaoDou ps = new PSHaoDou(document);
//        ArrayList<DiaryDishes> mealDetails = ps.getSearchList();
//        for (DiaryDishes mdb : mealDetails) {
//            downloader = new Downloader();
//            document = downloader.download(mdb.getDetailUrl());
//            PDHaoDou pd = new PDHaoDou(document);
//            mdb = pd.fillMDB(mdb);
//        }
//        DiaryDatas dd = new DiaryDatas();
//        for (DiaryDishes mdb : mealDetails) {
//            dd.insertData(new DiaryMealBean(GET_TIME[((int) (Math.random() * 100)) % 6], mdb));
//        }
//        System.out.print(dd.getArrayDatas());
//    }

    /**
     * Insert an item data to DiaryDatas.
     *
     * @param data
     * @return true: insert two view; false: insert one view.
     */
    public boolean insertData(DiaryMealBean data) {
        if (hashDatas.containsKey(data.getTime())) {
            ArrayList<DiaryMealBean> timeDatas = hashDatas.get(data.getTime());
            timeDatas.add(data);
            changeArrayDatas();
            return false;
        } else {
            ArrayList<DiaryMealBean> timeDatas = new ArrayList<>();
            timeDatas.add(data);
            hashDatas.put(data.getTime(), timeDatas);
            changeArrayDatas();
            return true;
        }
    }

    private void changeArrayDatas() {
        arrayDatas.clear();
        for (String s : GET_TIME) {
            ArrayList<DiaryMealBean> timeDatas = hashDatas.get(s);
            if (timeDatas != null) {
                arrayDatas.add(s);
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

    public ArrayList<Object> getArrayDatas() {
        return arrayDatas;
    }

    public void setArrayDatas(ArrayList<Object> arrayDatas) {
        this.arrayDatas = arrayDatas;
    }

    public HashMap<String, ArrayList<DiaryMealBean>> getHashDatas() {
        return hashDatas;
    }

    public void setHashDatas(HashMap<String, ArrayList<DiaryMealBean>> hashDatas) {
        this.hashDatas = hashDatas;
    }

    @Override
    public String toString() {
        String str = "";
        for (String key : hashDatas.keySet()) {
            str += (key + ": ");
            for (DiaryMealBean bean : hashDatas.get(key)) {
                str += bean.getDiaryDish().getDish().getName() + "; ";
            }
        }
        return str;
    }

}
