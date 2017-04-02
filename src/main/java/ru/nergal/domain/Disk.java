package ru.nergal.domain;

import javax.persistence.*;

@Entity
@Table(schema="DVD", name="DISKS")
@SequenceGenerator(name=Disk.SEQUENCE_NAME, sequenceName=Disk.SEQUENCE_NAME, schema="DVD", allocationSize = 20)
public class Disk {
    public static final String SEQUENCE_NAME = "SEQ_DISKS";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=Disk.SEQUENCE_NAME)
    @Column(name="disk_id")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name="disk_owner_id")
    private User owner;

    @OneToOne(mappedBy = "disk")
    private TakenItem taken;

    public long getId() {
    	return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public User getOwner() {
    	return this.owner;
    }

    public TakenItem getTaken() {
        return taken;
    }

    public void setTaken(TakenItem taken) {
        this.taken = taken;
    }

    public Disk() {};

    public Disk(Movie movie, User owner) {
        this.movie = movie;
        this.owner = owner;
    }
}