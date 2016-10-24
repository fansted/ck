package lab1;

import java.util.*;
import java.util.regex.*;
import java.lang.*;

public class Lab1 { // 匹配规则

	static Pattern pattern1 = Pattern
			.compile("^[1-9a-z](\\*[1-9a-z])*(\\+[1-9a-z](\\*[1-9a-z])*)*");
	static Pattern pattern2 = Pattern
			.compile("^[!](simplify)(\\s[a-z]\\=[]0-9]+)+");
	static Pattern pattern3 = Pattern.compile("^[!](d/d)\\s*[a-z]");

	public static void main(String[] args) {
		int flag = 0;
		String[] str = null;
		do {
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();

			s = s.trim(); // 去除字符串开头和结尾的空格
			Matcher matcher1 = pattern1.matcher(s);
			Matcher matcher2 = pattern2.matcher(s);
			Matcher matcher3 = pattern3.matcher(s);
			if (matcher1.matches()) {
				System.out.println(s); // 输入的是表达式，直接输出
				str = s.split("\\+");
				flag = 1;
			} else if (matcher2.matches() && flag == 1) {
				String[] str1 = new String[str.length];
				for (int m = 0; m < str.length; m++) {
					str1[m] = str[m];
				}
				String[] str2 = s.split(" ");
				int i, f = 0;
				for (i = 1; i < str2.length; i++) {
					if (char_in_not(str1, str2[i].substring(0, 1)) == 1) {
						f = 1;
						for (int j = 0; j < str1.length; j++) {
							str1[j] = str1[j].replace(str2[i].substring(0, 1),
									str2[i].substring(2, str2[i].length()));
						}
					}
				}
				if (f == 0) {
					System.out.println("Error,no variable!");
				} else {
					String ou = string_simp(str1);
					System.out.println(ou);
				}
			} else if (matcher3.matches() && flag == 1) {
				String[] str1 = new String[str.length];
				for (int m = 0; m < str.length; m++) {
					str1[m] = str[m];
				}
				int j = 4;
				String x;
				while (s.charAt(j) == ' ') {
					j++;
				}
				x = s.substring(j, j + 1);
				if (char_in_not(str1, x) == -1) {
					System.out.println("Error,no variable!");
				} else {
					// 每一项分别求导
					for (j = 0; j < str1.length; j++) {
						if (str1[j].indexOf(x) == -1) {
							str1[j] = "0";
						} else {
							int k, num = 0;
							String[] str2 = str1[j].split("\\*");
							for (k = 0; k < str2.length; k++) {
								if (str2[k].charAt(0) == x.charAt(0)) {
									num++;
								}
							}
							str1[j] = str1[j].replaceFirst(x,
									(String) (num + ""));

						}
					}

					// str1 = string_simp(str1);

					String out = string_simp(str1);
					// for(i=1;i<str1.length;i++)
					// {
					// out = out + '+'+ str1[i];
					// }

					System.out.println(out);
				}
			} else {
				System.out.println("Input Error!");
			}
		} while (true);
	}

	// 化简多项式
	public static String string_simp(String[] strx) {
		String[] str1;
		int i, j, sum = 0;

		for (i = 0; i < strx.length; i++) {
			int accum = 1;
			str1 = strx[i].split("\\*");
			for (j = 0; j < str1.length; j++) {

				if (!(str1[j].charAt(0) >= 'a' && str1[j].charAt(0) <= 'z')) {
					accum = accum * Integer.parseInt(str1[j]);
				}

			}
			if (accum != 1) {
				strx[i] = (String) (accum + "");

				for (j = 0; j < str1.length; j++) {
					if (str1[j].charAt(0) >= 'a' && str1[j].charAt(0) <= 'z') {
						strx[i] = strx[i] + "*" + str1[j];
					}

				}
			}
		}
		String s = strx[0];
		for (i = 1; i < strx.length; i++) {

			s = s + "+" + strx[i];
		}
		return s;
	}

	public static int char_in_not(String[] stry, String ch) {
		int i, flag = -1;
		for (i = 0; i < stry.length; i++) {
			if (stry[i].indexOf(ch) != -1) {
				flag = 1;
			}
		}
		return flag;
	}
}