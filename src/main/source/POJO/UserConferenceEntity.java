package POJO;

import javax.persistence.*;
import java.util.Objects;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
@Entity
@Table(name = "user_conference", schema = "ql_hoinghi", catalog = "")
@IdClass(UserConferenceEntityPK.class)
public class UserConferenceEntity {
    private int userId;
    private int conferenceId;
    private byte approved;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "conference_id", nullable = false)
    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Basic
    @Column(name = "approved", nullable = false)
    public byte getApproved() {
        return approved;
    }

    public void setApproved(byte approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserConferenceEntity that = (UserConferenceEntity) o;
        return userId == that.userId &&
                conferenceId == that.conferenceId &&
                approved == that.approved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, conferenceId, approved);
    }
}
