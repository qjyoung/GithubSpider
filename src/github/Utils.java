package github;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Utils {

	// public static Pattern FILE_PATTERN = Pattern.compile("[\\\\/:*?\"<>|]");
	public static Pattern FILE_PATTERN = Pattern.compile("[<em></em>]|[\\\\/:*?\"<>|]");

	public static String fileNameFilter(String filename) {
		return filename == null ? null : FILE_PATTERN.matcher(filename).replaceAll("");
	}

	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("D:\\_jQuery\\Github\\JavaSpider\\1\\A lit distributd Java spidr frawork");
		boolean delete = file.delete();
		System.out.println(delete);
		String url = "https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93&p=";
		// String testStr = "A lit distr<em></em>ibutd <em>Java</em> spidr frawork :-).zip ";
		// System.out.println(fileNameFilter(testStr));
		// getDownloadURlAndTitle(getDocument(url + 1), null);
	}

	/**
	<!--https://github.com/search?o=desc&p=2&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	
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

	/*
	 * 
	<!--https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	<!--https://github.com/search?o=desc&p=2&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	
	<!--https://github.com/search?utf8=%E2%9C%93&q=jQuery&type=-->
	https://github.com/KKys/ZhiHuSpider
	https://github.com/KKys/ZhiHuSpider/archive/master.zip
	
	<ul class="repo-list">
	
	<div class="repo-list-item d-flex flex-justify-start py-4 public source">
		<div class="col-8 pr-3">
			<h3>
	  <a href="/KKys/ZhiHuSpider" class="v-align-middle">KKys/ZhiHu<em>Spider</em></a>
	</h3>
	<!------------------------------------------------------------------------------------------------->
			<p class="col-9 d-inline-block text-gray mb-2 pr-4">
				<em>Java</em>无框架实现爬取知乎用户信息、图片和知乎推荐内容并下载到本地或数据库中
			</p>
			<p class="f6 text-gray mb-0 mt-2">
				Updated
				<relative-time datetime="2017-01-21T05:18:37Z">Jan 21, 2017</relative-time>
			</p>
		</div>
		<div class="d-table-cell col-2 text-gray pt-2">
			<span class="repo-language-color ml-0" style="background-color:#b07219;"></span> Java
		</div>
		<div class="col-2 text-right pt-1 pr-3 pt-2">
			<a class="muted-link" href="KKys/ZhiHuSpider/stargazers">
				<svg aria-label="star" class="octicon octicon-star" height="16" role="img" version="1.1" viewBox="0 0 14 16" width="14">
					<path fill-rule="evenodd" d="M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74z" />
				</svg>
				286
			</a>
		</div>
	</div> 
	 */

	/**
	 * get 下载链接 标题
	 * @param doc
	 * @param pw
	 * @return
	 * 
	<!--https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	<!--https://github.com/search?o=desc&p=2&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	
	<!--https://github.com/search?utf8=%E2%9C%93&q=jQuery&type=-->
	 * /wycm/zhihu-crawler/archive/2.0.zip
	 */
	public static List<String[]> getDownloadURlAndTitle(Document doc, PrintWriter pw) {
		/*
		<div class="WB_media_wrap clearfix">  
		//jsoup中选择器中某一个元素的多个条件之间不要加空格，加了空格下一个条件就变成子元素的条件了  
		Elements links = doc.select("div.WB_media_wrap.clearfix"); 
		 */
		List<String[]> urls = new ArrayList<>();//
		// Elements items = doc.getElementsByClass("repo-list-item d-flex flex-justify-start py-4
		// public source");
		Elements items = doc.select("div.repo-list-item.d-flex.flex-justify-start.py-4.public.source");
		// System.out.println(items);
		pw.println();
		for (Element item : items) {
			String[] pair = new String[2];
			Elements title = item.select("p.col-9.d-inline-block.text-gray.mb-2.pr-4");
			// 将可能出现的非法字符替换 作为文件名
			String fileName = fileNameFilter(title.text());
			Elements a = item.getElementsByClass("v-align-middle");
			String a1_href = a.attr("href");
			// https://github.com/KKys/ZhiHuSpider/archive/master.zip
			pair[0] = fileName + ".zip";
			// btn btn-outline get-repo-btn
			// 跳转到另一界面找准确的url
			Elements a2 = getDocument("https://github.com" + a1_href).select("a.btn.btn-outline.get-repo-btn");
			String a2_href = a2.attr("href");
			pair[1] = "https://github.com" + a2_href;

			if (pw != null) {
				pw.println("fileName---" + fileName + "---url---" + pair[1]);
			}
			System.out.println("fileName---" + fileName);
			System.out.println(pair[1]);
			System.out.println("---------------------");
			// 添加到集合
			urls.add(pair);
		}
		pw.println();
		return urls;
	}

	/**
	 * get 下载链接 标题
	 * @param doc
	 * @param pw
	 * @return
	 */
	public static List<String[]> getDownloadURlAndTitleOld(Document doc, PrintWriter pw) {
		/*<div class="WB_media_wrap clearfix">  
		//jsoup中选择器中某一个元素的多个条件之间不要加空格，加了空格下一个条件就变成子元素的条件了  
		Elements links = doc.select("div.WB_media_wrap.clearfix");  */
		List<String[]> urls = new ArrayList<>();//
		// Elements items = doc.getElementsByClass("repo-list-item d-flex flex-justify-start py-4
		// public source");
		Elements items = doc.select("div.repo-list-item.d-flex.flex-justify-start.py-4.public.source");
		// System.out.println(items);
		for (Element item : items) {
			String[] pair = new String[2];
			Elements title = item.select("p.col-9.d-inline-block.text-gray.mb-2.pr-4");
			// 将可能出现的非法字符替换 作为文件名
			String fileName = fileNameFilter(title.text());
			Elements a = item.getElementsByClass("v-align-middle");
			String href = a.attr("href");

			// https://github.com/KKys/ZhiHuSpider/archive/master.zip
			pair[0] = fileName + ".zip";
			pair[1] = "https://github.com" + href + "/archive/master.zip";
			pw.println("fileName---" + fileName + "---href---" + href);
			System.out.println("fileName---" + fileName);
			System.out.println("href---" + href);
			// 添加到集合
			urls.add(pair);
		}
		System.out.println(urls);
		return urls;
	}
}
