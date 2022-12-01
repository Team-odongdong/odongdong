package com.graduate.odondong.util.operationTime;

import java.util.regex.Pattern;

public class FormattingOpenTime {

	public static void main(String[] args) {
		System.out.println(changeOpenTime("0:00~15:00"));
		System.out.println(changeOpenTime("0:00-15:00"));
		System.out.println(changeOpenTime("gdgdgd7:00~9:00"));
		System.out.println(changeOpenTime("23시"));
		System.out.println(changeOpenTime("1시"));
		System.out.println(changeOpenTime("12시"));
		System.out.println(changeOpenTime("씨1시"));
		System.out.println(changeOpenTime("23"));
		System.out.println(changeOpenTime("232390235"));
		System.out.println(changeOpenTime("24시간"));
	}

	public static String changeOpenTime(String rawData) {
		String pattern1 = "^(\\d{1,2}:\\d{2})([-~∼])(\\d{1,2}:\\d{2})";
		String pattern2 = "^\\d{1,2}([시,시간]*)";
		String pattern3 = "^";
		if(Pattern.matches(pattern1, rawData)) {
			return getNormalOpenTimeFormat(rawData);
		}
		if(Pattern.matches(pattern2, rawData)) {
			return getHourOpenTimeFormat(rawData);
		}
		return rawData;
	}

	private static String getHourOpenTimeFormat(String rawData) {
		String replaceAll = rawData.replaceAll("([시,시간])", "");
		return replaceAll + "시간";
	}

	private static String getNormalOpenTimeFormat(String rawData) {
		String replaceAll = rawData.replaceAll("([-~∼])", "~");
		String[] split = replaceAll.split("~");
		String[] string1 = split[0].split(":");
		String[] string2 = split[1].split(":");
		return sizeOneToTwo(string1) + "~" + sizeOneToTwo(string2);
	}

	private static String sizeOneToTwo(String[] strings) {
		if(strings[0].length() == 1) {
			return "0" + strings[0] + ":" + strings[1];
		}
		return strings[0] + ":" + strings[1];
	}
}
