package net.imprex.orebfuscator.config;

import java.util.List;

public interface WorldConfig {

	boolean enabled();
	
	List<String> worlds();

	List<Integer> randomBlocks();

	int randomBlockId();
}
