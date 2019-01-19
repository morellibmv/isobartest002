package io.brunovargas.isobar.cache;

import io.brunovargas.isobar.dto.Artist;

public class ArtistDataCache {

	private static final ArtistDataCache instance = new ArtistDataCache();
	private static CacheManager<String, Artist> cache;
	
	
	public static ArtistDataCache getInstance(){
		return instance;
	}
	
	ArtistDataCache(){
		cache = new CacheManager<String, Artist>(10000, 10000, 50);
	}
	
	public synchronized void put(Artist artist){
		String key = artist.getId();
		if(key != null){
			cache.put(key, artist);
		}
	}
	
	public Artist get(String id){
		return cache.get(id);
	}
}
