package ru.ensemplix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Abuse {
	
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    private static final Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);

	private static List<String> words = new ArrayList<String>();

	private static boolean isAbuse(String word) {
		for (String abuse : words) {
			if (word.equalsIgnoreCase(abuse)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAbuse(String text) {
		for (String word : text.split(" ")) {
			if (Abuse.isAbuse(word)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsYoutube(String text) {
		for (String word : text.split(" ")) {
			if (word.contains("youtube.com")) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsIp(String text) {
		for (String word : text.split(" ")) {
			if (pattern.matcher(word).matches()) {
				return true;
			}
		}
		return false;		
	}

	static {
		words.add("хуй");
		words.add("ебал");
		words.add("ебали");
		words.add("ебет");
		words.add("ебется");
		words.add("ебеться");
		words.add("ебут");
		words.add("ебешь");
		words.add("ебеш");
		words.add("ебете");
		words.add("ебите");
		words.add("ебись");
		words.add("ебитесь");
		words.add("ебуться");
		words.add("ебутся");
		words.add("ебем");
		words.add("ебучи");
		words.add("ебуча");
		words.add("ебучк");
		words.add("ебаш");
		words.add("ебу");
		words.add("наебу");
		words.add("ебанись");
		words.add("fuck");
		words.add("заебу");
		words.add("отьебу");
		words.add("выебу");
		words.add("еби");
		words.add("наеби");
		words.add("заеби");
		words.add("отьеби");
		words.add("выеби");
		words.add("уеби");
		words.add("вьеби");
		words.add("ебай");
		words.add("уебище");
		words.add("ебенит");
		words.add("ебеня");
		words.add("еблан");
		words.add("уебан");
		words.add("долбоеб");
		words.add("далбаеб");
		words.add("ебарь");
		words.add("ебло");
		words.add("поебень");
		words.add("хуесос");
		words.add("бляд");
		words.add("блять");
		words.add("блеадь");
		words.add("блеать");
		words.add("бля");
		words.add("епта");
		words.add("йопта");
		words.add("сука");
		words.add("суки");
		words.add("суку");
		words.add("сукой");
		words.add("суками");
		words.add("суке");
		words.add("сцука");
		words.add("сцуко");
		words.add("сучий");
		words.add("сучие");
		words.add("сучара");
		words.add("сучары");
		words.add("сучару");
		words.add("сучаре");
		words.add("сучарам");
		words.add("сучарах");
		words.add("сучар");
		words.add("сучарой");
		words.add("сучарами");
		words.add("сучка");
		words.add("сучку");
		words.add("сучек");
		words.add("сучки");
		words.add("сучке");
		words.add("сучкам");
		words.add("сучкой");
		words.add("сучками");
		words.add("сучках");
		words.add("сцучка");
		words.add("сцучко");
		words.add("хуй");
		words.add("хуи");
		words.add("хуям");
		words.add("хуем");
		words.add("хуев");
		words.add("хуя");
		words.add("хую");
		words.add("хуями");
		words.add("хуе");
		words.add("хуях");
		words.add("хуевы");
		words.add("хуевый");
		words.add("хуевые");
		words.add("хуево");
		words.add("нихуя");
		words.add("нахуй");
		words.add("похуй");
		words.add("дохуя");
		words.add("хуяк");
		words.add("хуяч");
		words.add("однохуйственно");
		words.add("однохуйствено");
		words.add("пох");
		words.add("говно");
		words.add("гавна");
		words.add("говна");
		words.add("пидор");
		words.add("пидар");
		words.add("пизда");
		words.add("пидарас");
		words.add("хуесос");
		words.add("хуесосы");
		words.add("хуесоска");
		words.add("мудак");
		words.add("пидорас");
		words.add("манда");
		words.add("wtf");
		words.add("suck");
		words.add("shit");
		words.add("cock");
		words.add("fuck");
		words.add("ёпт");
		words.add("епт");
		words.add("ебать");
		words.add("ебет");
		words.add("трахает");
		words.add("идиот");
		words.add("дебил");
		words.add("даун");
		words.add("пизда");
	}

}
