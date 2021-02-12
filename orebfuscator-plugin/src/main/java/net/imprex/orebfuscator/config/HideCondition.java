package net.imprex.orebfuscator.config;

/**
 * Only use MSBs (16 bit) for HideCondition
 * 15 bit y | 1 bit above flag
 */
public class HideCondition {

	public static final int MATCH_ALL = HideCondition.create(0, true);

	public static int create(int y, boolean above) {
		return (y << 17 | (above ? 0x10000 : 0x0));
	}

	public static int remove(int hideCondition) {
		return hideCondition & 0xFFFF;
	}

	private static int extractHideCondition(int hideCondition) {
		return hideCondition & 0xFFFF0000;
	}

	public static boolean isMatchAll(int hideCondition) {
		return extractHideCondition(hideCondition) == MATCH_ALL;
	}

	public static boolean equals(int a, int b) {
		return extractHideCondition(a) == extractHideCondition(b);
	}

	public static boolean match(int hideCondition, int y) {
		int expectedY = getY(hideCondition);
		if (getAbove(hideCondition)) {
			return expectedY < y;
		} else {
			return expectedY > y;
		}
	}

	public static int getY(int hideCondition) {
		return hideCondition >> 17;
	}

	public static boolean getAbove(int hideCondition) {
		return (hideCondition & 0x10000) != 0;
	}
}
