package POJO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @created on 7/20/2020
 * @author: Helios - 1712018
 */
@Entity
@Table(name = "location", schema = "ql_hoinghi", catalog = "")
public class LocationEntity {
    private int id;
    private String name;
    private Integer capacity;
    private String address;
    private Set<ConferenceEntity> conferences = new HashSet<>();

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "capacity", nullable = true)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "location")
    public Set<ConferenceEntity> getConferences() {
        return conferences;
    }

    public void setConferences(Set<ConferenceEntity> conferences) {
        this.conferences = conferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, address);
    }
}
