package flabs.siverbars.domain;

import java.util.Objects;

public final class SalesUser {

    private String userId;

    public SalesUser(String userId) {
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesUser salesUser = (SalesUser) o;
        return Objects.equals(getUserId(), salesUser.getUserId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "SalesUser{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
