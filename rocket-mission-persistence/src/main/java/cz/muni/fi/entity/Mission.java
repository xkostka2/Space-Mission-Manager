package cz.muni.fi.entity;

import cz.muni.fi.enums.MissionProgress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Class representing mission entity.
 *
 * @author Martin Kazimir
 */

@Entity
public class Mission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    private String destination;

    @Enumerated(EnumType.STRING)
    private MissionProgress missionProgress;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Rocket> rockets = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Component> components = new HashSet<>();

    private ZonedDateTime eta;
    private ZonedDateTime finishedDate;
    private ZonedDateTime startedDate;

    @Column(length = 8192)
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
        return Collections.unmodifiableSet(users);
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.setMission(this);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setMission(null);
    }

    public Set<Rocket> getRockets() {
        return Collections.unmodifiableSet(rockets);
    }

    public void addRocket(Rocket rocket) {
        if (!rockets.contains(rocket)) {
            rockets.add(rocket);
            rocket.setMission(this);
        }
    }

    public void removeRocket(Rocket rocket) {
        rockets.remove(rocket);
        rocket.setMission(null);
    }

    public Set<Component> getComponents() {
        return Collections.unmodifiableSet(components);
    }

    public void addComponent(Component component) {
        if (!components.contains(component)) {
            components.add(component);
            component.setMission(this);
        }
    }

    public void removeComponent(Component component) {
        components.remove(component);
        component.setMission(null);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;
        Mission other = (Mission) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Mission{" +
                "name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", missionProgress=" + missionProgress +
                ", users=" + users +
                ", rockets=" + rockets +
                ", components=" + components +
                ", eta=" + eta +
                ", finishedDate=" + finishedDate +
                ", startedDate=" + startedDate +
                ", result='" + result + '\'' +
                '}';
    }
}