package POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @created on 7/14/2020
 * @author: Helios - 1712018
 */
public class UserConferenceEntityPK implements Serializable {
    private int userId;
    private int conferenceId;

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "conference_id", nullable = false)
    @Id
    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserConferenceEntityPK that = (UserConferenceEntityPK) o;
        return userId == that.userId &&
                conferenceId == that.conferenceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, conferenceId);
    }
}
