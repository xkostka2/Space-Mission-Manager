package cz.muni.fi.entity;

import com.sun.istack.NotNull;
import cz.muni.fi.enums.MissionProgress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.Set;


/**
 * @author Martin Kazimir
 */

@Entity
public class Mission{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    private String destination;
    private MissionProgress missionProgress;

    @ManyToMany
    private Set<User> users;

    @OneToMany
    private Set<Rocket> rockets;

    @ManyToMany
    private Set<Component> components;

    private ZonedDateTime eta;
    private ZonedDateTime finishedDate;
    private ZonedDateTime startedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public MissionProgress getMissionProgress() {
        return missionProgress;
    }

    public void setMissionProgress(MissionProgress missionProgress) {
        this.missionProgress = missionProgress;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(Set<Rocket> rockets) {
        this.rockets = rockets;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public ZonedDateTime getEta() {
        return eta;
    }

    public void setEta(ZonedDateTime eta) {
        this.eta = eta;
    }

    public ZonedDateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(ZonedDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public ZonedDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(ZonedDateTime startedDate) {
        this.startedDate = startedDate;
    }
}