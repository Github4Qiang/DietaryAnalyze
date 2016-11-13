package scuse.com.dietaryanalyze001.logic.spider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

import scuse.com.dietaryanalyze001.logic.bean.MealDetailBean;

public class PDHaoDou extends ParserDetails {

    public PDHaoDou(Document document) {
        super(document);
    }

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        Document document = downloader.download("http://www.haodou.com/recipe/1088325/");
        PDHaoDou parser = new PDHaoDou(document);
        System.out.println("title " + parser.getTitle());
        System.out.println("img " + parser.getImg());
        System.out.println("component " + parser.getComponent());
    }

    @Override
    public MealDetailBean fillMDB(MealDetailBean mdb) {
        mdb.setImgUrl(getImg());
        mdb.setMealName(getTitle());
        mdb.setMealComponent(getComponent());
        return mdb;
    }

    @Override
    public String getTitle() {
        Element element = getDocument().select("div.rec_con_lar > div.large_info h1").first();
        return element.text();
    }

    @Override
    public String getImg() {
        Element element = getDocument().select("div.rec_con_lar > div.large > img").first();
        return element.attr("src");
    }

    @Override
    public HashMap<String, String> getComponent() {
        Elements mgrEles = getDocument().select("div.concrete #recipe_ingt .ingtmgr");
        HashMap<String, String> component = new HashMap<>();
        for (Element element : mgrEles) {
            component.put(element.select("p.name > a").first().text(), element.select("span.amount").text());
        }
        Elements burEles = getDocument().select("div.concrete #recipe_ingt .ingtbur");
        for (Element element : burEles) {
            component.put(element.select("p.name").first().text(), element.select("span.amount").text());
        }
        return component;
    }

}
