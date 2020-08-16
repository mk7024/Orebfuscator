package net.imprex.orebfuscator.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import net.imprex.orebfuscator.NmsInstance;
import net.imprex.orebfuscator.util.OFCLogger;
import net.imprex.orebfuscator.util.WeightedRandom;

public class OrebfuscatorWorldConfig implements WorldConfig {	

	private boolean enabled;
	private final List<String> worlds = new ArrayList<>();
	private final Set<Material> hiddenBlocks = new HashSet<>();

	private final Map<Material, Integer> randomBlocks = new HashMap<>();
	private final List<Integer> randomBlockIds = new ArrayList<>();
	private final WeightedRandom<Integer> randomMaterials = new WeightedRandom<>();

	protected void initialize() {
		this.randomMaterials.clear();
		for (Entry<Material, Integer> entry : this.randomBlocks.entrySet()) {
			int blockId = NmsInstance.getMaterialIds(entry.getKey()).iterator().next();
			this.randomMaterials.add(entry.getValue(), blockId);
			this.randomBlockIds.add(blockId);
		}
	}

	protected void serialize(ConfigurationSection section) {
		this.enabled = section.getBoolean("enabled", true);

		List<String> worldNameList = section.getStringList("worlds");
		if (worldNameList == null || worldNameList.isEmpty()) {
			this.failSerialize(
					String.format("config section '%s.worlds' is missing or empty", section.getCurrentPath()));
			return;
		}
		this.worlds.clear();
		this.worlds.addAll(worldNameList);

		this.serializeMaterialSet(section, this.hiddenBlocks, "hiddenBlocks");
		if (this.hiddenBlocks.isEmpty()) {
			this.failSerialize(String.format("config section '%s' is missing 'hiddenBlocks'",
					section.getCurrentPath()));
			return;
		}

		ConfigParser.serializeRandomMaterialList(section, this.randomBlocks, "randomBlocks");
		if (this.randomBlocks.isEmpty()) {
			this.failSerialize(
					String.format("config section '%s.randomBlocks' is missing or empty", section.getCurrentPath()));
		}
	}

	public void deserialize(ConfigurationSection section) {
		section.set("enabled", this.enabled);
		section.set("worlds", this.worlds);

		// TODO deserialize hiddenBlocks and randomBlocks
	}

	private void serializeMaterialSet(ConfigurationSection section, Set<Material> materials, String path) {
		materials.clear();

		List<String> materialNames = section.getStringList(path);
		if (materialNames == null || materialNames.isEmpty()) {
			return;
		}

		for (String name : materialNames) {
			Optional<Material> material = NmsInstance.getMaterialByName(name);

			if (!material.isPresent()) {
				OFCLogger.warn(String.format("config section '%s.%s' contains unknown block '%s'",
						section.getCurrentPath(), path, name));
				continue;
			}

			materials.add(material.get());
		}
	}

	private void failSerialize(String message) {
		this.enabled = false;
		OFCLogger.warn(message);
	}

	@Override
	public boolean enabled() {
		return this.enabled;
	}

	@Override
	public void enabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public List<String> worlds() {
		return Collections.unmodifiableList(this.worlds);
	}

	@Override
	public void worldsSet(List<String> worlds) {
		for (Iterator<String> iterator = this.worlds.iterator(); iterator.hasNext(); ) {
			String world = iterator.next();

			if (worlds.contains(world)) {
				worlds.remove(world);
			} else {
				iterator.remove();
			}
		}
		this.worlds.addAll(worlds);
	}

	@Override
	public void worldsAdd(String world) {
		this.worlds.add(world);
	}

	@Override
	public void worldsRemove(String world) {
		this.worlds.remove(world);
	}

	@Override
	public Set<Material> hiddenBlocks() {
		return Collections.unmodifiableSet(this.hiddenBlocks);
	}

	@Override
	public void hiddenBlocksSet(List<Material> materials) {
		for (Iterator<Material> iterator = this.hiddenBlocks.iterator(); iterator.hasNext(); ) {
			Material material = iterator.next();

			if (materials.contains(material)) {
				materials.remove(material);
			} else {
				iterator.remove();
			}
		}
		this.hiddenBlocks.addAll(materials);
	}

	@Override
	public void hiddenBlocksAdd(Material material) {
		this.hiddenBlocks.add(material);
	}

	@Override
	public void hiddenBlocksRemove(Material material) {
		this.hiddenBlocks.remove(material);
	}

	@Override
	public Set<Material> randomBlocks() {
		return Collections.unmodifiableSet(this.randomBlocks.keySet());
	}

	@Override
	public void randomBlocksSet(List<Material> materials) {
		for (Iterator<Material> iterator = this.randomBlocks.keySet().iterator(); iterator.hasNext(); ) {
			Material material = iterator.next();

			if (materials.contains(material)) {
				materials.remove(material);
			} else {
				iterator.remove();
			}
		}
		for (Material material : materials) {
			this.randomBlocks.put(material, NmsInstance.getMaterialIds(material).iterator().next());
		}
		this.initialize();
	}

	@Override
	public void randomBlocksAdd(Material material) {
		this.randomBlocks.put(material, NmsInstance.getMaterialIds(material).iterator().next());
		this.initialize();
	}

	@Override
	public void randomBlocksRemove(Material material) {
		this.randomBlocks.remove(material);
		this.initialize();
	}

	@Override
	public List<Integer> randomBlockIds() {
		return this.randomBlockIds;
	}

	@Override
	public int randomBlockId() {
		return this.randomMaterials.next();
	}
}
