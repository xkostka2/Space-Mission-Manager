package cz.muni.fi.entity;

import com.sun.istack.NotNull;
import cz.muni.fi.enums.ComponentType;


import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by tdtom167
 *
 * @author tdtom167
 */
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean readyToUse;

    @Column(unique = true)
    @NotNull
    private String name;


    private ComponentType type;

    private ZonedDateTime readyDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private List<Mission> missions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "rocket_id")
    private List<Rocket> rockets;

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

    public List<Rocket> getRockets() {
        return rockets;
    }

    public void setRocket(List<Rocket> rockets) {
        this.rockets = rockets;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Component)) return false;

        Component that = (Component) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }


    @Override
    public String toString() {
        return '\n' + "Component:" +
                "Name-" + name;
    }
}