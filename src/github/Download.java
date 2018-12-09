package github;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		title = urlPair[0];
		_url = urlPair[1];
		this.pw = pw;
		this.pw_err = pw_err;
		this.target_save_path = target_save_path;
	}

	/**
	 * 以当前时间命名文件
	 * @return
	 */
	public String getTimeString() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return format.format(date) + ".zip";
	}

	@Override
	public void run() {
		URL url;
		InputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		String fileName = title;
		try {

			url = new URL(_url);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10 * 1000);
			urlConn.setReadTimeout(10 * 1000);
			urlConn.setRequestProperty("connection", "keep-alive");

			fis = urlConn.getInputStream();

			bis = new BufferedInputStream(fis);
			if (fileName.equals("") || fileName == null) {
				fileName = getTimeString();
			}
			File file = new File(target_save_path, fileName);
			if (file.exists())
				// 如果文件已存在，用时间命名新文件
				fileName = getTimeString();
			file = new File(target_save_path, fileName);
			// fos = new FileOutputStream(target_save_path + fileName);
			// 以文件形式创建流
			fos = new FileOutputStream(file);
			byte b[] = new byte[10 * 1024 * 1024];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				System.out.println("----len---" + len);
				fos.write(b, 0, len);
			}
			System.out.println("------finishOne--------");
			String fileDownloadPrompt = fileName + " --- download success, thread--->>"
					+ Thread.currentThread().getName();
			// 输出到文件
			pw.println(fileDownloadPrompt);
			System.out.println(fileDownloadPrompt);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			String error = "error when download ---" + fileName + "---" + _url;// title-->>fileName
			pw.println(error);
			pw_err.println(error);
			System.out.println(error);
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
