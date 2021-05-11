package cz.muni.fi.dto.component;

import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.enums.ComponentType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Object used when creating a Component
 *
 * @author Martin Kostka
 */
public class CreateComponentDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    private boolean readyToUse;
    private ComponentType type;
    private ZonedDateTime readyDate;
    private MissionDTO mission;
    private RocketDTO rocket;

    public boolean isReadyToUse() {
        return readyToUse;
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

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public ZonedDateTime getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(ZonedDateTime readyDate) {
        this.readyDate = readyDate;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    public RocketDTO getRocket() {
        return rocket;
    }

    public void setRocket(RocketDTO rocket) {
        this.rocket = rocket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateComponentDTO that = (CreateComponentDTO) o;
        return getName().equals(that.getName()) && getType() == that.getType() && getReadyDate().equals(that.getReadyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getReadyDate());
    }
}
