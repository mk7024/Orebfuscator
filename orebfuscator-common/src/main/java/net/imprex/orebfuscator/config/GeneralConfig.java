package net.imprex.orebfuscator.config;

public interface GeneralConfig {

	boolean updateOnBlockDamage();
	void updateOnBlockDamage(boolean enabled);

	boolean bypassNotification();
	void bypassNotification(boolean enabled);

	int initialRadius();
	void initialRadius(int radius);

	int updateRadius();
	void updateRadius(int radius);

	int proximityHiderRunnerSize();
	void proximityHiderRunnerSize(int size);
}