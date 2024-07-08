package me.tobiasliese;

import java.lang.foreign.MemorySegment;

import static org.systemd.SD_Bus_1.C_CHAR;

public class Util {
	public static String readCString(MemorySegment memorySegment) {
		char l;
		int i = 0;
		StringBuilder sb = new StringBuilder();
		do {
			l = (char) memorySegment.getAtIndex(C_CHAR, i);
			sb.append(l);
			i++;
		} while (l != '\0');
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
