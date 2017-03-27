package ru.nergal.domain;

import javax.persistence.*;

@Entity
@Table(schema="DVD", name="R_DISKS")
@SequenceGenerator(name=Disk.SEQUENCE_NAME, sequenceName=Disk.SEQUENCE_NAME, schema="DVD", allocationSize = 20)
public class Disk {
    public static final String SEQUENCE_NAME = "SEQ_R_DISKS";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=Disk.SEQUENCE_NAME)
    @Column(name="disk_id")
    private long id;
    
    @Column(name="movie_title")
    private String movieTitle;

    @ManyToOne()
    @JoinColumn(name="disk_owner_id")
    private User owner;

    @OneToOne(mappedBy = "disk")
    private TakenItem taken;

    public long getId() {
    	return this.id;
    }

    public String getMovieTitle() {
    	return this.movieTitle;
    }

    public void setMovieTitle(String newTitle) {
    	this.movieTitle = newTitle;
    }

    public User getOwner() {
    	return this.owner;
    }

    public void setOwner(User newOwner) {
    	this.owner = newOwner;
    }

    public TakenItem getTaken() {
        return taken;
    }

    public void setTaken(TakenItem taken) {
        this.taken = taken;
    }
}