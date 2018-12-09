/*package test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;

public class DownloadTestOld {
	public static void main(String[] args) throws URISyntaxException, IOException {
		// String path = "http://www.html5tricks.com/download/jquery-3d-image-gallary.rar";
		System.out.println("start...");
		PrintWriter pw = new PrintWriter(new FileWriter("D:\\_jQuery\\log.txt"), true);
		// -----------------------------------------------------
		ExecutorService es = Executors.newCachedThreadPool();
		Document document;
		// = Utils.getDocument("http://www.html5tricks.com/category/jquery-plugin/page/37");
		// System.out.println(document);
		int page = 6;
		while (page < 37) {
			document = Utils.getDocument("http://www.html5tricks.com/category/jquery-plugin/page/" + page);
			String pageLine = "----------------" + page++ + "------------------------";
			pw.println(pageLine);
			System.out.println(pageLine);
			if (document == null) {
				String errorPage = "----------------error when get page------------------------";
				pw.println(errorPage);
				System.out.println(errorPage);
				continue;
			}
			List<String> urls = Utils.getDownloadURl(document, pw);
			for (String url : urls) {
				if (!url.startsWith("http")) {
					url = "http://" + url;
				}
				es.execute(new Download(url, pw));
			}
		}
	}
}

class Download implements Runnable {
	private String _url;
	private PrintWriter pw;

	Download(String url, PrintWriter pw) {
		this._url = url;
		this.pw = pw;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL url;
		InputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		String fileName = _url.substring(_url.lastIndexOf("/") + 1);
		try {
			url = new URL(_url);
			System.out.println(url);
			fis = url.openConnection().getInputStream();
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream("D:\\_jQuery\\" + fileName);
			byte b[] = new byte[1024 * 1024];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				fos.write(b, 0, len);
				// System.out.println("---" + len);
			}
			String fileDownloadPrompt = fileName + " --- download success, thread--->>"
					+ Thread.currentThread().getName();
			// 输出到文件
			pw.println(fileDownloadPrompt);
			System.out.println(fileDownloadPrompt);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
*/