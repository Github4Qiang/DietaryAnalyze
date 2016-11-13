package scuse.com.dietaryanalyze001.logic.spider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import scuse.com.dietaryanalyze001.logic.bean.MealDetailBean;
import scuse.com.dietaryanalyze001.logic.bean.MealSearchBean;

public class PSHaoDou extends ParserSearch {

    public PSHaoDou(Document document) {
        super(document);
    }

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        Document document = downloader.download("http://www.haodou.com/s?wd=红烧肉&tp=recipe");
        PSHaoDou parser = new PSHaoDou(document);
        System.out.println("title " + parser.getSearchList());
    }

    @Override
    public String getTitle(Element element) {
        return element.select("p.name > a").first().text();
    }

    @Override
    public String getImg(Element element) {
        return element.select("span.img > a > img").first().attr("src");
    }

    @Override
    public String getDetailURL(Element element) {
        return element.select("span.img > a").first().attr("href");
    }

    @Override
    public ArrayList<MealDetailBean> getSearchList() {
        Elements elements = getDocument().select("div.area_main ul#the-list li");
        ArrayList<MealDetailBean> hashList = new ArrayList<>();
        for (Element element : elements) {
            MealDetailBean mdb = new MealDetailBean();
            mdb.setMealName(getTitle(element));
            mdb.setImgUrl(getImg(element));
            mdb.setDetailUrl(getDetailURL(element));
            hashList.add(mdb);
        }
        return hashList;
    }

}
