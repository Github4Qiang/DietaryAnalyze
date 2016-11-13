package scuse.com.dietaryanalyze001.logic.spider;

import org.jsoup.nodes.Document;

public abstract class Parser {

	private Document document;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Parser(Document document) {
		this.document = document;
	}
}
