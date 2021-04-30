package cz.muni.fi.dto.update;

import cz.muni.fi.dto.ComponentDTO;
import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.dto.RocketDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Rocket DTO.
 *
 * @author Tomas Bouma (469275)
 */

public class UpdateRocketDTO {

    private long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private MissionDTO mission;

    @NotNull
    @Size(min = 1)
    private Set<ComponentDTO> requiredComponents = new HashSet<>();

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
                ", name='" + name;
    }
}