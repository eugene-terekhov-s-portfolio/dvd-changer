package ru.nergal.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema="DVD", name="USERS")
@SequenceGenerator(name=User.SEQUENCE_NAME, sequenceName=User.SEQUENCE_NAME, schema="DVD", allocationSize = 20)
public class User {
    public static final String SEQUENCE_NAME = "SEQ_USERS";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=User.SEQUENCE_NAME)
    @Column(name="user_id")
    private long id;
    
    @Column(name="user_name")
    private String name;
    
    @OneToMany(mappedBy="owner")
    private Set<Disk> disks;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private int enabled;

    @ElementCollection(targetClass = UserRoles.class)
    @CollectionTable(schema="DVD", name="M_USER_ROLES")
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private List<UserRoles> userRoles;


    public User(String userName, String login, String password) {
        this.name = userName;
        this.login = login;
        this.password = password;
        this.enabled = 1;
        this.userRoles = new ArrayList<UserRoles>();
        this.userRoles.add(UserRoles.USER);
    }

    public User() {

    }

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

    public String getLogin() {
        return login;
    }
}
