package io.brunovargas.isobar.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.brunovargas.isobar.Application;
import io.brunovargas.isobar.cache.ArtistDataCache;
import io.brunovargas.isobar.client.ArtistEndpointClient;
import io.brunovargas.isobar.dto.Artist;
import io.brunovargas.isobar.utils.SortingOrder;

@RestController
@RequestMapping("/rest/artist")
public class ArtistService {

	private static final Logger log = LoggerFactory.getLogger(ArtistService.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Cacheable("artistCache")
	public List<Artist> getAll(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "sortBy", required = false) final String sortBy,
			@RequestParam(name = "sortOrder", required = false) final String sortOrder) throws Exception {
		ArtistEndpointClient artistClient = ArtistEndpointClient.getInstance();
		List<Artist> result = artistClient.get();

		Stream<Artist> streamArtist = result.stream();
		if (name != null) {
			streamArtist = result.stream()
					.filter(artist -> artist.getName().toUpperCase().contains(name.toUpperCase()));
		}
		if (sortBy != null) {
			final Method sortingMethod = Artist.class.getMethod("get" + StringUtils.capitalize(sortBy));
			final Function functionGetter = artist -> {
				try {
					return sortingMethod.invoke(artist);
				} catch (Exception e) {
					log.error("Erro na chamada ao método de referencia", e);
					return null;
				}
			};

			final Comparator<Artist> sortingComparator = SortingOrder.DESC.equals(sortOrder.toUpperCase())
					? Comparator.comparing(functionGetter) : Comparator.comparing(functionGetter).reversed();
			streamArtist = streamArtist.sorted(sortingComparator);
		}

		result = streamArtist.collect(Collectors.toList());
		result.forEach(artist -> ArtistDataCache.getInstance().put(artist));

		return result;
	}

	@RequestMapping(value = "/{artistId}", method = RequestMethod.GET)
	public Artist get(@PathVariable("artistId") String id) throws Exception {
		ArtistEndpointClient artistClient = ArtistEndpointClient.getInstance();

		Artist result = ArtistDataCache.getInstance().get(id);

		if (result == null) {
			List<Artist> data = artistClient.get();
			result = data.stream().filter(artist -> artist.getId().equals(id)).findFirst().orElse(null);
		}

		return result;
	}

}
