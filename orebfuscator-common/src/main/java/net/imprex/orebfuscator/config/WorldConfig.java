package net.imprex.orebfuscator.config;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;

public interface WorldConfig {

	boolean enabled();
	void enabled(boolean enabled);
	
	List<String> worlds();
	void worldsSet(List<String> worlds);
	void worldsAdd(String world);
	void worldsRemove(String world);

	Set<Material> hiddenBlocks();
	void hiddenBlocksSet(List<Material> materials);
	void hiddenBlocksAdd(Material material);
	void hiddenBlocksRemove(Material material);

	Set<Material> randomBlocks();
	void randomBlocksSet(List<Material> materials);
	void randomBlocksAdd(Material material);
	void randomBlocksRemove(Material material);

	List<Integer> randomBlockIds();

	int randomBlockId();
}
