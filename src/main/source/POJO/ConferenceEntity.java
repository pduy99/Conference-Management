package POJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
@Entity(name = "conference")
@Table(name = "conference", schema = "ql_hoinghi", catalog = "")
public class ConferenceEntity {
    private int id;
    private String shortDescription;
    private String detailDescription;
    private Timestamp time;
    private String place;
    private String name;
    private int capacity;
    private String image;
    private Set<UserEntity> users = new HashSet<UserEntity>(0);

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "short_description", nullable = true, length = 200)
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "detail_description", nullable = true, length = 500)
    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "place", nullable = false, length = 200)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "Image", nullable = true, length = 1024)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "conferences")
    public Set<UserEntity> getUsers(){
        return this.users;
    }

    public void setUsers(Set<UserEntity> users){
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceEntity that = (ConferenceEntity) o;
        return id == that.id &&
                capacity == that.capacity &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(detailDescription, that.detailDescription) &&
                Objects.equals(time, that.time) &&
                Objects.equals(place, that.place) &&
                Objects.equals(name, that.name) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortDescription, detailDescription, time, place, name, capacity, image);
    }

    //Custom Constructor
    public ConferenceEntity(String name, Timestamp time, String place, int capacity){
        this.name = name;
        this.time = time;
        this.place = place;
        this.capacity = capacity;
    }

    public ConferenceEntity(){};
}
