package io.brunovargas.isobar.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Serializable{

	private static final long serialVersionUID = 4014869870827134879L;
	
	private String id;
	private String name;
	private String image;
	private String genre;
	private String biography;
	private Long numPlays;
	private List<String> albums;
	private List<List<Album>> albumList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Long getNumPlays() {
		return numPlays;
	}

	public void setNumPlays(Long numPlays) {
		this.numPlays = numPlays;
	}

	public List<String> getAlbums() {
		return albums;
	}

	public void setAlbums(List<String> albums) {
		this.albums = albums;
	}

	public List<List<Album>> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<List<Album>> albumList) {
		this.albumList = albumList;
	}
}
