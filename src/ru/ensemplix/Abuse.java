package ru.ensemplix;

import java.util.regex.Pattern;

import ru.ensemplix.api.IsGroupApi;

public class Abuse {
	
    private static final String IPADDRESS_PATTERN = "(?:.*)\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(?:\\:\\d{1,5})?(?:.*)";
    
    private static final Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
    
    private String text;
    
    public Abuse(String text) {
    	this.text = text;
    }

	private boolean isAbuse(String word) {
		for (String abuse : AbuseWords.words) {
			if (word.equalsIgnoreCase(abuse)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAbuse() {
		for (String word : text.split(" ")) {
			if (isAbuse(word)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsYoutube() {
		for (String word : text.split(" ")) {
			if (word.contains("youtube.com")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsVkGroupURL() throws Exception {
		for (String word : text.split(" ")) {
			if (word.contains("vk.com")
					&& !word.equalsIgnoreCase("vk.com/ensemplix")) {
				word = word.replaceAll("http://", "");
				String[] args = word.split("/");
				if (args.length >= 2) {
					IsGroupApi groupApi = new IsGroupApi();
					groupApi.setId(args[1]);
					groupApi.execute();
					if (groupApi.isGroup()) {
						return true;
					}
					
					
				}								
			}
		}
		return false;
	}

	public boolean containsIp() {
		for (String word : text.split(" ")) {
			if (pattern.matcher(word).matches()) {
				return true;
			}
		}
		return false;
	}

}
