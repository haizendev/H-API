package fr.haizen.hapi.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;

public class KickHeader {

	private List<String> messages;

	public List<String> getMessages() {
		return this.messages;
	}

	public KickHeader(String... msg) {
		this.messages = new ArrayList<>();
		apprend(msg);
	}

	public KickHeader apprend(String... msg) {
		this.messages.addAll(Arrays.asList(msg));
		return this;
	}

	public String build() {
		String result = "";
		for (int i = 0; i < this.messages.size(); i++) {
			if (i == this.messages.size() - 1) {
				result = String.valueOf(result) + (String)this.messages.get(i) + ChatColor.RESET;
			} else {
				result = String.valueOf(result) + (String)this.messages.get(i) + "\n" + ChatColor.RESET;
			} 
		} 
		return ChatColor.translateAlternateColorCodes('&', result);
	}
}
