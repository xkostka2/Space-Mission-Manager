package cz.muni.fi.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


/**
 * Rocket entity.
 *
 * @author Martin Hořelka (469003)
 */
@Entity
public class Rocket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "rocket")
    private Set<Component> requiredComponents;

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

    public Set<Component> getRequiredComponents() {
        return Collections.unmodifiableSet(requiredComponents);
    }

    public void setRequiredComponents(Set<Component> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket that = (Rocket) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", requiredComponents=" + requiredComponents +
                '}';
    }
}
