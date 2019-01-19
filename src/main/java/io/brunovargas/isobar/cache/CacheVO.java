package io.brunovargas.isobar.cache;

class CacheVO<V> {
	public long lastAccessed = System.currentTimeMillis();
	public V value;

	protected CacheVO(V value) {
		this.value = value;
	}
}