package net.imprex.orebfuscator;

public class OrebfuscatorInventory extends Orebfuscator {

	public OrebfuscatorInventory() {
	}

	@Override
	public void onEnable() {
		super.onEnable();
		if (!this.enabled) {
			return;
		}

		try {
		} catch(Exception e) {
			this.enableFailed(e);
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
