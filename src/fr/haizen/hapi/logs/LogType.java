package fr.haizen.hapi.logs;

import lombok.Getter;

public enum LogType {

	SUCCESS("§a"),
	INFO("§7"),
	WARNING("§6"),
	ERROR("§c"),
	;

	private @Getter String color;

	LogType(String color) {
		this.color = color;
	}
}

