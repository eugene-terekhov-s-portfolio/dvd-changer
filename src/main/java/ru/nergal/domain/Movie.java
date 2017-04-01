package ru.nergal.domain;

import javax.persistence.*;

@Entity
@Table(schema="DVD", name="MOVIES")
public class Movie {

	@Id
	@Column(name = "movie_id")
	private long id;

	@Column(name = "poster_path")
	private String poster;

	@Column(name = "title")
	private String title;

	@Column(name = "original_title")
	private String originalTitle;

	@Column(name = "overview")
	private String overview;

	@Column(name = "release_year")
	private int releaseYear;

	@Column(name = "VOTE_AVERAGE")
	private double rating;

	public String getPosterUrl() {
		return "https://image.tmdb.org/t/p/w92/" + this.poster;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public String getOriginalTitle() {
		return this.originalTitle;
	}

	public String getOverview() {
		return this.overview;
	}

	public int getReleaseYear() {
		return this.releaseYear;
	}

	public double getRating() {
		return this.rating;
	}

}

/*
( "MOVIE_ID" NUMBER PRIMARY KEY,
  "POSTER_PATH" VARCHAR(50 CHAR),
  "TITLE" VARCHAR(100 CHAR) NOT NULL,
  "ORIGINAL TITLE" VARCHAR(100 CHAR) NOT NULL,
  "OVERVIEW" VARCHAR (1000 CHAR) NOT NULL,
  "RELEASE_YEAR" NUMBER,
  "VOTE_AVERAGE" NUMBER
*/