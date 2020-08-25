package net.imprex.orebfuscator.config;

import java.util.List;

public interface ProximityConfig {

	boolean enabled();
	void enabled(boolean enabled);

	List<String> worlds();

	int distance();
	void distance(int distance);

	int distanceSquared();

	boolean useFastGazeCheck();
	void useFastGazeCheck(boolean fastGaze);

	List<Integer> randomBlocks();

	int randomBlockId();
}
