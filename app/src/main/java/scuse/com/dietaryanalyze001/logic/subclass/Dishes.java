package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scuse.com.dietaryanalyze001.ui.adapter.SearchListAdapter;

/**
 * Created by Polylanger on 2016/9/26.
 */
@AVClassName("Dishes")
public class Dishes extends AVObject {

    public static final String TAG = "Dishes";
    public static final String DISH_NAME = "name";
    public static final String DISH_IMG_URL = "imgUrl";
    public static final String DISH_DETAIL_URL = "detailUrl";
    public static final String DISH_INGT_MGR = "ingtMgr";
    public static final String DISH_INGT_BUR = "ingtBur";
    public static final String DISH_NTRT_AMOUNT = "ntrtAmount";
    public static final String DISH_UNIT_AMOUNT = "unitAmount";
    public static final String DISH_IMG_FILE = "imgFile";

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public Dishes(Parcel in) {
        super(in);
    }

    public Dishes() {

    }

    public String getName() {
        return this.getString(DISH_NAME);
    }

    public String getImgURL() {
        return this.getString(DISH_IMG_URL);
    }

    public HashMap<String, String> getIngtBur() {
        HashMap<String, String> component = new HashMap<>();
        component = new Gson().fromJson(this.getString(DISH_INGT_BUR), component.getClass());
        return component;
    }

    public HashMap<String, String> getIngtMgr() {
        HashMap<String, String> component = new HashMap<>();
        component = new Gson().fromJson(this.getString(DISH_INGT_MGR), component.getClass());
        return component;
    }

    public AVRelation getNtrtAmtRelations() {
        return this.getRelation(DISH_NTRT_AMOUNT);
    }

    public double getUnitAmount() {
        return this.getInt(DISH_UNIT_AMOUNT);
    }

    public String getImgFile() {
        return this.getAVFile(DISH_IMG_FILE).getUrl();
    }

    /**
     * 获得所有食材的名字（ingtMgr1,ingtMgr2;ingtBur1,ingtBur2.）
     *
     * @return 所有食材（主料+辅料）组成的字符串
     */
    public String getAllIngtsName() {
        String allIngtsName = "";
        for (String name : this.getIngtMgr().keySet()) {
            allIngtsName += (name + ", ");
        }
        allIngtsName = allIngtsName.substring(0, allIngtsName.length() - 2);
        allIngtsName += "; ";
        for (String name : this.getIngtBur().keySet()) {
            allIngtsName += (name + ", ");
        }
        allIngtsName = allIngtsName.substring(0, allIngtsName.length() - 2);
        allIngtsName += ".";
        return allIngtsName;
    }

    public static void FuzzyQuery(String title, FindCallback<Dishes> callback) {
        // 对查询内容进行分词，经过 OR 操作后在 Dishes 数据表中查询
        ArrayList<AVQuery<Dishes>> queries = new ArrayList<>();
        // 构建 OR 查询列表
        List<Term> terms = BaseAnalysis.parse(title).getTerms();
        for (Term term : terms) {
            AVQuery<Dishes> query = new AVQuery<>("Dishes");
            query.whereContains(Dishes.DISH_NAME, term.getName());
            queries.add(query);
        }
        // 利用 OR 列表构建查询
        AVQuery<Dishes> query = AVQuery.or(queries);
        query.findInBackground(callback);
    }
}
