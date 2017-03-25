package ru.nergal.domain;

import javax.persistence.*;

@Entity
@Table(schema="DVD", name="R_TAKEN_ITEMS")
@SequenceGenerator(name=TakenItem.SEQUENCE_NAME, sequenceName=TakenItem.SEQUENCE_NAME, schema="DVD", allocationSize = 20)
public class TakenItem {
    public static final String SEQUENCE_NAME = "SEQ_R_TAKEN_ITEMS";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=TakenItem.SEQUENCE_NAME)
    @Column(name="item_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="disk_id")
    private Disk disk;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User tenant;

    public long getId() {
    	return this.id;
    }

    public Disk getDisk() {
    	return this.disk;
    }

    public void setDisk(Disk rentedDisk) {
    	this.disk = rentedDisk;
    }

    public User getTenant() {
    	return this.tenant;
    }

    public void setTenant(User newTenant) {
    	this.tenant = newTenant;
    }
}