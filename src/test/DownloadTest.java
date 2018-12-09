package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;

public class DownloadTest {
	public static final String HTML_URL = "http://www.html5tricks.com/category/html5-demo/page/";
	public static final String JQUERY_URL = "http://www.html5tricks.com/category/jquery-plugin/page/";

	public static String JQUERY_SAVE_PATH = "D:\\_jQuery\\JQUERY\\";
	public static String JQUERY_LOG_PATH = "D:\\_jQuery\\JQUERY\\log.txt";
	public static String JQUERY_LOG_ERROR_PATH = "D:\\_jQuery\\JQUERY\\log_err.txt";

	public static String HTML_SAVE_PATH = "D:\\_jQuery\\HTML\\";
	public static String HTML_LOG_PATH = "D:\\_jQuery\\HTML\\log.txt";
	public static String HTML_LOG_ERROR_PATH = "D:\\_jQuery\\HTML\\log_err.txt";

	// jQuery

	// public static String TARGET_PATH = JQUERY_SAVE_PATH;
	// public static String TARGET_LOG_PATH = JQUERY_LOG_PATH;
	// public static String TARGET_LOG_ERROR_PATH = JQUERY_LOG_ERROR_PATH;
	// public static String TARGET_URL = JQUERY_URL;

	// HTML

	public static String TARGET_SAVE_PATH = HTML_SAVE_PATH;
	public static String TARGET_LOG_PATH = HTML_LOG_PATH;
	public static String TARGET_LOG_ERROR_PATH = HTML_LOG_ERROR_PATH;
	public static String TARGET_URL = HTML_URL;

	public static void main(String[] args) throws URISyntaxException, IOException {
		System.out.println("start...");
		PrintWriter pw = new PrintWriter(new FileWriter(TARGET_LOG_PATH), true);
		PrintWriter pw_err = new PrintWriter(new FileWriter(TARGET_LOG_ERROR_PATH), true);
		// -----------------------------------------------------
		ExecutorService es = Executors.newCachedThreadPool();
		Document document;
		int page = 25;
		String originPath = TARGET_SAVE_PATH;
		// TARGET_SAVE_PATH = TARGET_SAVE_PATH + 4 + "\\";
		while (++page <= 26) {
			if (page % 10 == 0 || page == 1) {
				TARGET_SAVE_PATH = originPath + (page / 10 + 1) + "\\";
				File file = new File(TARGET_SAVE_PATH);
				if (!file.exists())
					file.mkdir();
			}
			document = Utils.getDocument(TARGET_URL + page);
			String pageLine = "----------------" + page + "------------------------";
			pw.println(pageLine);
			System.out.println(pageLine);
			if (document == null) {
				String errorPage = "----------------error when get page " + page + "-----------------------";
				pw.println(errorPage);
				pw_err.println(errorPage);
				System.out.println(errorPage);
				continue;
			}
			List<String[]> urls = Utils.getDownloadURlAndTitle(document, pw);
			for (String[] urlPair : urls) {
				// 这里要考虑到同步问题，应该将存储路径传给线程 保存为线程私有变量
				// 不然线程在拿到TARGET_PATH值之前 其中静态值就已经被修改了，所以赢在主线程传当时的TARGET_PATH值给线程
				es.execute(new Download(urlPair, TARGET_SAVE_PATH, pw, pw_err));
			}
		}
	}
}
