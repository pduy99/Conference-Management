package POJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "conference", schema = "ql_hoinghi", catalog = "")
public class ConferenceEntity {
    private int idConference;
    private String shortDescription;
    private String detailDescription;
    private Timestamp time;
    private String place;
    private String name;
    private int capacity;
    private String image;

    @Id
    @Column(name = "idConference", nullable = false)
    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    @Basic
    @Column(name = "Short_Description", nullable = true, length = 200)
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "Detail_Description", nullable = true, length = 500)
    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Basic
    @Column(name = "Time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "Place", nullable = false, length = 200)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "Image", nullable = false, length = 1024)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceEntity that = (ConferenceEntity) o;
        return idConference == that.idConference &&
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
        return Objects.hash(idConference, shortDescription, detailDescription, time, place, name, capacity, image);
    }
}
