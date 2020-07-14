package POJO;

import javax.persistence.*;
import java.util.Objects;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
@Entity
@Table(name = "event_location", schema = "ql_hoinghi", catalog = "")
public class EventLocationEntity {
    private String name;
    private String address;
    private int capacity;

    @Id
    @Column(name = "Name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Address", nullable = false, length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLocationEntity that = (EventLocationEntity) o;
        return capacity == that.capacity &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, capacity);
    }
}
