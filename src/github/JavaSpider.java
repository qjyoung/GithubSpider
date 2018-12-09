package github;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;

public class JavaSpider {
	// https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93&p=2-->
	// https://github.com/search?o=desc&p=2&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93-->
	// https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93&p=30
	// D:\_jQuery\JavaSpider
	public static void main(String[] args) throws IOException {
		System.out.println("start...");
		// --------------------------Spider---------------------------------------
		// String logPath = "D:\\_jQuery\\Github\\JavaSpider\\log.txt";
		// String errLogPath = "D:\\_jQuery\\Github\\JavaSpider\\log_err.txt";
		// String savePath = "D:\\_jQuery\\Github\\JavaSpider\\";
		// String url =
		// "https://github.com/search?o=desc&q=java+spider&s=stars&type=Repositories&utf8=%E2%9C%93&p=";
		// -------------------------JavaWeb--------------------------------------
		// JavaWeb
		String logPath = "D:\\_jQuery\\Github\\JavaWeb\\log.txt";
		String errLogPath = "D:\\_jQuery\\Github\\JavaWeb\\log_err.txt";
		String savePath = "D:\\_jQuery\\Github\\JavaWeb\\";
		// https://github.com/search?o=desc&q=javaweb&s=stars&type=Repositories&utf8=%E2%9C%93&p=1
		String url = "https://github.com/search?o=desc&q=javaweb&s=stars&type=Repositories&utf8=%E2%9C%93&p=";
		// flush auto
		// 追加 append
		PrintWriter pw = new PrintWriter(new FileWriter(logPath, true), true);
		PrintWriter pw_err = new PrintWriter(new FileWriter(errLogPath, true), true);

		ExecutorService es = Executors.newCachedThreadPool();
		String originPath = savePath;
		// savePath += 1 + "\\";
		int p = 9;
		while (++p <= 10) {
			// 每3页放一个文件夹🕷
			if (p == 1 || (p - 1) % 3 == 0) {
				savePath = originPath + (p / 3 + 1) + "\\";
				File file = new File(savePath);
				if (!file.exists())
					file.mkdir();
			}
			Document document = Utils.getDocument(url + p);
			// System.out.println(url + p);
			List<String[]> pairs = Utils.getDownloadURlAndTitle(document, pw);
			for (String[] pair : pairs) {
				es.execute(new Download(pair, savePath, pw, pw_err));
			}
		}
		es.shutdown();
		es.isTerminated();
	}
}