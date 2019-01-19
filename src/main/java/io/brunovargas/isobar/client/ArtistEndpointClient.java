package io.brunovargas.isobar.client;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.brunovargas.isobar.dto.Artist;

@Component
public class ArtistEndpointClient {

	private static final ArtistEndpointClient instance = new ArtistEndpointClient();

	private String endpointUri;

	public static ArtistEndpointClient getInstance() {
		return instance;
	}

	ArtistEndpointClient() {
		this.endpointUri = "https://iws-recruiting-bands.herokuapp.com/api/full";
	}

	@Cacheable("artistCache")
	public List<Artist> get() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Artist>> response = restTemplate.exchange(this.endpointUri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Artist>>() {
				});
		List<Artist> artists = response.getBody();
		return artists;
	}

}
