package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {

	public static void main(String[] args) {
		String str1 = "<a href=\"http://www.zifangsky.cn/2015/10/hello-world/\" title=\"\" data-original-title=\"Hello World\">Hello World</a>";
		String str2 = "<a href=\"http://banzhuanboy.com/363.html\" class=\"post-feature\" \">123</a>";
		String str3 = " <a class=\"article-title\" href=\"/2015/12/17/Webstorm-Hotkeys-For-Mac/\">c</a>";
		String str4 = " <a rel=\"bookmark\" title=\"Permanent Link to  黑客组织‘SkidNP’涂改了Phantom Squad的网站首页\" href='12/hack-30127.htm'>黑</a>";
		String str5 = "<a href=\"http://www.imorlin.com/2015/12/24/1-3/\" title=\"\" data-original-title=\"2015圣诞节雪花代码[天猫+C店]\"> 2015圣诞节雪花代码[天猫+C店] <span class=\"label label-new entry-tag\">New</span> </a>";

		/*Pattern pattern = Pattern.compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
		Matcher matcher = pattern.matcher(str1);
		if (matcher.find()) {
			String link = matcher.group(1).trim();
			String title = matcher.group(3).trim();
			if (!link.startsWith("http")) {
				if (link.startsWith("/"))
					link = "http://www.zifangsky.cn" + link;
				else
					link = "http://www.zifangsky.cn" + link;
			}
			System.out.println("link: " + link);
			System.out.println("title: " + title);
		}
		
		String savePath = "D:\\_jQuery\\Github\\JavaSpider\\";
		savePath += 1 + "\\";
		System.out.println(savePath);
		System.out.println();*/
		double ceil = Math.ceil(Math.random() * 30);
		System.out.println(ceil);
	}
}/*
	1 选取了几个有代表性的a标签样式进行测试
	2 关于正则匹配模式”<a.*?href=[\”‘]?((https?://)?/?[^\”‘]+)[\”‘]?.*?>(.+)</a>“的说明：
	i)<a.*?href=    <a开头，中间紧跟着有0个或者多个字符，然后再跟着href= 
	ii)[\”‘]?((https?://)?/? 一个或者0个的” 或者 ‘ ，然后再跟着0个或者一个的http://或者https:// ，再跟着0个或者1个的 /
	iii)[^\”‘]+  表示1个以上的不包括’或者” 的任意字符
	iv)[\”‘]?表示链接后面的’或者” 当然也可能没有
	后面的可以根据前面的自己推理，就不解释了
	3 matcher.group(1)表示取出链接，也就是第二个()的内容（PS：第一个()表示的是整个正则表达式，默认省略了），在正则中是这一段规则：((https?://)?/?[^\”‘]+)
	4 matcher.group(3) 同理可知，对应的是这一段规则：(.+)
	5 对于代码中的http://www.zifangsky.cn ，这是由于部分链接使用了相对路径，比如说：href=’12/hack-30127.htm’ 。这时我们就需要加上它的域名，当然需要根据实际情况来加。这里我就随便乱加了
	本文出自 “zifangsky的个人博客” 博客，请务必保留此出处http://983836259.blog.51cto.com/7311475/1729171
	*/