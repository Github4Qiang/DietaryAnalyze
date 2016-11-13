package scuse.com.dietaryanalyze001.logic.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {

	public Downloader() {
		super();
	}

	public static void main(String[] args) {
		Downloader downloader = new Downloader();
		System.out.println(downloader.download("http://www.haodou.com/s?wd=红烧肉&tp=recipe"));
	}

	public Document download(String sUrl) {
		try {
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0");
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				String html = "";
				String line = reader.readLine();
				while (line != null) {
					html += line;
					line = reader.readLine();
				}
				return Jsoup.parse(html);
			} else {
				System.out.println("conn.getResponseCode = " + conn.getResponseCode());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
