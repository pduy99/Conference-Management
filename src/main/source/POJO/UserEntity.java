package POJO;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
@Entity
@Table(name = "user", schema = "ql_hoinghi", catalog = "")
public class UserEntity {
    private int id;
    private String displayName;
    private String username;
    private String password;
    private int role;
    private String email;
    private Byte blocked;
    private Set<ConferenceEntity> conferences = new HashSet<>(0);
    private String address;
    private Date birthday;
    private Date createDate;

    //Custom Constructor
    public UserEntity(String name, int role){
        this.displayName = name;
        this.role = role;
    }

    public UserEntity(String displayName, String username, String password, String mail){
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.email = mail;
        this.role = 1;
    }

    public UserEntity(String username, String displayName, String email, String address, Date birthday){
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }

    public UserEntity(){}

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "display_name", nullable = false, length = 100)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false, columnDefinition = "1")
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "blocked", nullable = true)
    public Byte getBlocked() {
        return blocked;
    }

    public void setBlocked(Byte blocked) {
        this.blocked = blocked;
    }

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_conference",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "conference_id")})
    public Set<ConferenceEntity> getConferences(){
        return this.conferences;
    }

    public void setConferences(Set<ConferenceEntity> conferences){
        this.conferences = conferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                role == that.role &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(blocked, that.blocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName, username, password, role, email, blocked);
    }

    @Basic
    @Column(name = "address", nullable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
