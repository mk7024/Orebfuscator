package net.imprex.orebfuscator.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.World;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;

import net.imprex.orebfuscator.chunk.ChunkCapabilities;

public class HeightAccessor {

	private static final Map<World, HeightAccessor> ACCESSOR_LOOKUP = new ConcurrentHashMap<>();

	public static HeightAccessor get(World world) {
		return ACCESSOR_LOOKUP.computeIfAbsent(world, HeightAccessor::new);
	}

	private static final MethodAccessor WORLD_GET_HEIGHT = getWorldMethod("getHeight"); // TODO actual name in bukkit api once 1.17 is released
	private static final MethodAccessor WORLD_GET_MIN_BUILD_HEIGHT = getWorldMethod("getMinBuildHeight"); // TODO actual name in bukkit api once 1.17 is released

	private static MethodAccessor getWorldMethod(String methodName) {
		if (ChunkCapabilities.hasDynamicHeight()) {
			return Accessors.getMethodAccessor(World.class, methodName);
		}
		return null;
	}

	private final int height;
	private final int minBuildHeight;

	private HeightAccessor(World world) {
		if (ChunkCapabilities.hasDynamicHeight()) {
			this.height = (int) WORLD_GET_HEIGHT.invoke(world);
			this.minBuildHeight = (int) WORLD_GET_MIN_BUILD_HEIGHT.invoke(world);
		} else {
			this.height = 256;
			this.minBuildHeight = 0;
		}
	}

	public int getHeight() {
		return this.height;
	}

	public int getMinBuildHeight() {
		return this.minBuildHeight;
	}

	public int getMaxBuildHeight() {
		return this.minBuildHeight + this.height;
	}

	public int getSectionCount() {
		return this.getMaxSection() - this.getMinSection();
	}

	public int getMinSection() {
		return SectionPosition.blockToSectionCoord(this.getMinBuildHeight());
	}

	public int getMaxSection() {
		return SectionPosition.blockToSectionCoord(this.getMaxBuildHeight() - 1) + 1;
	}

	public int getSectionIndex(int y) {
		return SectionPosition.blockToSectionCoord(y) - getMinSection();
	}
}
