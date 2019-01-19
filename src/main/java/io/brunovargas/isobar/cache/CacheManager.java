package io.brunovargas.isobar.cache;

import com.fasterxml.jackson.databind.util.LRUMap;

public class CacheManager<K, V> {

	private long timeToLive;
	private LRUMap<K, CacheVO<V>> cache;

	public CacheManager(long timeToLive, final long interval, int maxSize) {
		this.timeToLive = timeToLive * 1000;

		cache = new LRUMap<K, CacheVO<V>>(0, maxSize);

		if (this.timeToLive > 0 && interval > 0) {

			Thread t = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(interval * 1000);
						} catch (InterruptedException ex) {
						}
						cleanup();
					}
				}
			});

			t.setDaemon(true);
			t.start();
		}
	}

	public void put(K key, V value) {
		synchronized (cache) {
			cache.putIfAbsent(key, new CacheVO<V>(value));
		}
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		synchronized (cache) {
			CacheVO<V> c = (CacheVO<V>) cache.get(key);

			if (c == null)
				return null;
			else {
				c.lastAccessed = System.currentTimeMillis();
				return c.value;
			}
		}
	}

	public int size() {
		synchronized (cache) {
			return cache.size();
		}
	}

	@SuppressWarnings("unchecked")
	public void cleanup() {
		synchronized (cache) {
			cache.clear();
		}
	}
}
