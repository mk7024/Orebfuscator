package net.imprex.orebfuscator.config;

import java.nio.file.Path;

import net.imprex.orebfuscator.util.ChunkPosition;

public interface CacheConfig {

	boolean enabled();
	void enabled(boolean enabled);

	Path baseDirectory();
	void baseDirectory(Path path);

	Path regionFile(ChunkPosition chunkPosition);

	int maximumOpenRegionFiles();
	void maximumOpenRegionFiles(int count);

	long deleteRegionFilesAfterAccess();
	void deleteRegionFilesAfterAccess(long expire);

	int maximumSize();
	void maximumSize(int size);

	long expireAfterAccess();
	void expireAfterAccess(long expire);
}
