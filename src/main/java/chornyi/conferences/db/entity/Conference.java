package chornyi.conferences.db.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Conference entity
 */

public class Conference {

    private long id;
    private String name;
    private LocalDateTime dateTime;
    private String location;

    private Conference(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.dateTime = builder.dateTime;
        this.location = builder.location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Conference that = (Conference) object;
        return id == that.id &&
                name.equals(that.name) &&
                dateTime.equals(that.dateTime) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateTime, location);
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", location='" + location + '\'' +
                '}';
    }

    public static class Builder{
        private long id;
        private String name;
        private LocalDateTime dateTime;
        private String location;

        public Conference build() {
            return new Conference(this);
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }
    }
}
