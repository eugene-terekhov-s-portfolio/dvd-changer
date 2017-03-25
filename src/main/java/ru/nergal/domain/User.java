package ru.nergal.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema="DVD", name="C_USERS")
@SequenceGenerator(name=User.SEQUENCE_NAME, sequenceName=User.SEQUENCE_NAME, schema="DVD", allocationSize = 20)
public class User {
    public static final String SEQUENCE_NAME = "SEQ_C_USERS";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=User.SEQUENCE_NAME)
    @Column(name="user_id")
    private long id;
    
    @Column(name="user_name")
    private String name;
    
    @OneToMany(mappedBy="owner")
    private Set<Disk> disks;

    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }

    public Set<Disk> getDisks() {
        return this.disks;
    }
}
