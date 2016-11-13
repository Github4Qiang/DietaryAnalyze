package scuse.com.dietaryanalyze001.logic.spider;

import java.util.HashMap;

import org.jsoup.nodes.Document;

import scuse.com.dietaryanalyze001.logic.bean.MealDetailBean;

abstract class ParserDetails extends Parser {

	public ParserDetails(Document document) {
		super(document);
	}

	public abstract MealDetailBean fillMDB(MealDetailBean mdb);

	public abstract String getTitle();

	public abstract String getImg();

	public abstract HashMap<String, String> getComponent();

}
