package test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**download
 * @author QJY
 * @time 2017年8月17日
 */
public class Download implements Runnable {
	private String _url;
	private String title;
	private String target_save_path;
	private PrintWriter pw;
	private PrintWriter pw_err;

	Download(String[] urlPair, String target_save_path, PrintWriter pw, PrintWriter pw_err) {
		_url = urlPair[1].startsWith("http") == true ? urlPair[1] : "http://" + urlPair[1];
		title = urlPair[0];
		this.pw = pw;
		this.pw_err = pw_err;
		this.target_save_path = target_save_path;
	}

	@Override
	public void run() {
		URL url;
		InputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		String fileName = title + "_" + _url.substring(_url.lastIndexOf("/") + 1);
		try {

			url = new URL(_url);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10 * 1000);
			urlConn.setReadTimeout(10 * 1000);
			urlConn.setRequestProperty("connection", "keep-alive");

			fis = urlConn.getInputStream();

			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(target_save_path + fileName);
			byte b[] = new byte[1024 * 1024];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				fos.write(b, 0, len);
			}
			String fileDownloadPrompt = fileName + " --- download success, thread--->>"
					+ Thread.currentThread().getName();
			// 输出到文件
			pw.println(fileDownloadPrompt);
			System.out.println(fileDownloadPrompt);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			String error = "error when download ---" + title + "---" + _url;
			pw.println(error);
			pw_err.println(error);
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
