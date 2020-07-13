package POJO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "ql_hoinghi", catalog = "")
public class UserEntity {
    private int iduser;
    private String name;
    private String userName;
    private String password;
    private int role;
    private String email;

    @Id
    @Column(name = "iduser", nullable = false)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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
    @Column(name = "User_name", nullable = false, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Role", nullable = false)
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Basic
    @Column(name = "Email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return iduser == that.iduser &&
                Objects.equals(name, that.name) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iduser, name, userName, password, role, email);
    }

    public UserEntity(){}

    /***
     * Custom constructor
     * @param displayName
     * @param username
     * @param password
     * @param mail
     */
    public UserEntity(String displayName, String username, String password, String mail){
        this.name = displayName;
        this.userName = username;
        this.password = password;
        this.email = mail;
    }

    public UserEntity(String displayName, int role){
        this.name = displayName;
        this.role = role;
    }
}
