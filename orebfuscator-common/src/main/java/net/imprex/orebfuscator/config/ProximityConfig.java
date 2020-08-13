package net.imprex.orebfuscator.config;

import java.util.List;

public interface ProximityConfig {

	boolean enabled();

	List<String> worlds();

	int distance();

	int distanceSquared();

	boolean useFastGazeCheck();

	List<Integer> randomBlocks();

	int randomBlockId();
}
