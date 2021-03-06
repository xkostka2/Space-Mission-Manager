package cz.muni.fi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Rocket entity.
 *
 * @author Martin Hořelka (469003)
 */
@Entity
public class Rocket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Component> requiredComponents = new HashSet<>();

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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Set<Component> getRequiredComponents() {
        return Collections.unmodifiableSet(requiredComponents);
    }

    public void setRequiredComponents(Set<Component> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public void addRequiredComponent(Component requiredComponent) {
        if (!requiredComponents.contains(requiredComponent)) {
            this.requiredComponents.add(requiredComponent);
            requiredComponent.setRocket(this);
        }
    }

    public void removeRequiredComponent(Component requiredComponent){
        if (requiredComponents.contains(requiredComponent)) {
            this.requiredComponents.remove(requiredComponent);
            requiredComponent.setRocket(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rocket)) return false;
        Rocket other = (Rocket) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mission=" + mission +
                ", requiredComponents=" + requiredComponents +
                '}';
    }
}
