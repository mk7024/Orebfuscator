package net.imprex.orebfuscator.inventory;

import org.bukkit.event.Listener;

import net.imprex.orebfuscator.Orebfuscator;

public class InventoryManager implements Listener {

	private final Orebfuscator orebfuscator;

	public InventoryManager(Orebfuscator orebfuscator) {
		this.orebfuscator = orebfuscator;
	}
}