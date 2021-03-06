package cz.muni.fi.entity;

import cz.muni.fi.enums.ComponentType;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by tdtom167
 *
 * @author tdtom167
 */
@Entity
public class Component implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean readyToUse;

    @Column(unique = true)
    @NotNull
    private String name;


    private ComponentType type;

    private ZonedDateTime readyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rocket_id")
    private Rocket rocket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isReadyToUse() {
        return readyToUse;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public void setReadyToUse(boolean readyToUse) {
        this.readyToUse = readyToUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(ZonedDateTime readyDate) {
        this.readyDate = readyDate;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component other = (Component) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }


    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", readyToUse=" + readyToUse +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", readyDate=" + readyDate +
                ", mission=" + mission +
                ", rocket=" + rocket +
                '}';
    }
}
