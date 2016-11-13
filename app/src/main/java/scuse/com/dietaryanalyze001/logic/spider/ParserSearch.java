package scuse.com.dietaryanalyze001.logic.spider;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import scuse.com.dietaryanalyze001.logic.bean.MealDetailBean;
import scuse.com.dietaryanalyze001.logic.bean.MealSearchBean;

abstract public class ParserSearch extends Parser {

	public ParserSearch(Document document) {
		super(document);
	}

	public abstract String getTitle(Element element);

	public abstract String getImg(Element element);

	public abstract String getDetailURL(Element element);

	public abstract ArrayList<MealDetailBean> getSearchList();

}
