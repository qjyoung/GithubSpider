package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Utils {

	/**
	 * 根据url获取Document对象
	 * @param url 小说章节url
	 * @return Document对象
	 */
	public static Document getDocument(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * get 下载链接
	 * @param doc
	 * @param pw
	 * @return
	 */
	public static List<String> getDownloadURl(Document doc, PrintWriter pw) {
		Elements elementsByClass = doc.getElementsByClass("download");
		List<String> urls = new ArrayList<>();
		for (Element element : elementsByClass) {
			String downloadURL = element.toString();
			// System.out.println(downloadURL);
			String reg = "<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>";

			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(downloadURL);
			while (matcher.find()) {
				String href = matcher.group(1);
				urls.add(href);
				pw.println("href---" + href);
				System.out.println("href---" + href);
			}
		}
		return urls;
	}

	/**
	 * get 下载链接 标题
	 * @param doc
	 * @param pw
	 * @return
	 */

	public static List<String[]> getDownloadURlAndTitle(Document doc, PrintWriter pw) {
		List<String[]> urls = new ArrayList<>();
		Elements articles = doc.getElementsByTag("article");
		for (Element article : articles) {
			String[] pair = new String[2];
			Elements title = article.getElementsByClass("entry-title");
			Elements downloadURL = article.getElementsByClass("download");
			// 将可能出现的非法字符替换 作为文件名
			String fileName = title.text().replace("/", "_");
			pair[0] = fileName;
			pw.println("fileName---" + fileName);
			System.out.println("fileName---" + fileName);
			String reg = "<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>";
			// 提取url
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(downloadURL.toString());
			while (matcher.find()) {
				String href = matcher.group(1);
				pair[1] = href;
				pw.println("url---" + href);
				System.out.println("href---" + href);
			}
			urls.add(pair);
		}
		return urls;
	}

	/**
	 * 从a标签中提取href属性 attribute
	 * 
	 * @param doc
	 * @return 下一章Url
	 */
	public static String getNextUrl(Document doc) {
		Element ul = doc.select("ul").first();
		String regex = "<li><a href=\"(.*?)\">下一页<\\/a><\\/li>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ul.toString());
		Document nextDoc = null;
		if (matcher.find()) {
			nextDoc = Jsoup.parse(matcher.group());
			Element href = nextDoc.select("a").first();
			return "http://www.bxwx.org/b/5/5131/" + href.attr("href");
		} else {
			return null;
		}
	}

	/**
	 * 根据小说的Url获取一个Article对象
	 * @param url
	 * @return
	 */
	// public static Article getArticle(String url) {
	// Article article = new Article();
	// article.setUrl(url);
	// Document doc = getDocument(url);
	// article.setId(getId(url));
	// article.setTitle(getTitle(doc));
	// article.setNextUrl(getNextUrl(doc));
	// article.setContent(getContent(doc));
	// return article;
	// }
}
