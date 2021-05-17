package cz.muni.fi.dto.rocket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.mission.MissionDTO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Rocket DTO.
 *
 * @author Tomas Bouma (469275)
 */

public class RocketDTO {
    private Long id;
    private String name;

    @JsonBackReference
    private MissionDTO mission;

    @JsonManagedReference
    private Set<ComponentDTO> requiredComponents = new HashSet<>();

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

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    public Set<ComponentDTO> getRequiredComponents() {
        return Collections.unmodifiableSet(requiredComponents);
    }

    public void setRequiredComponents(Set<ComponentDTO> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public void addRequiredComponent(ComponentDTO requiredComponent) {
        if (!requiredComponents.contains(requiredComponent)) {
            this.requiredComponents.add(requiredComponent);
            requiredComponent.setRocket(this);
        }
    }

    public void removeRequiredComponent(ComponentDTO requiredComponent){
        if (requiredComponents.contains(requiredComponent)) {
            this.requiredComponents.remove(requiredComponent);
            requiredComponent.setRocket(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RocketDTO)) return false;
        RocketDTO other = (RocketDTO) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "RocketDTO {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mission=" + mission +
                ", requiredComponents=" + requiredComponents +
                '}';
    }
}
