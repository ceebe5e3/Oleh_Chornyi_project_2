package chornyi.conferences.db.entity.details;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConferenceDetails {

    private long id;
    private String nameEN;
    private String nameUA;
    private String locationEN;
    private String locationUA;
    private LocalDateTime dateTime;

    private ConferenceDetails(Builder builder) {
        this.id = builder.id;
        this.nameEN = builder.nameEN;
        this.nameUA = builder.nameUA;
        this.locationEN = builder.locationEN;
        this.locationUA = builder.locationUA;
        this.dateTime = builder.dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    public String getLocationEN() {
        return locationEN;
    }

    public void setLocationEN(String locationEN) {
        this.locationEN = locationEN;
    }

    public String getLocationUA() {
        return locationUA;
    }

    public void setLocationUA(String locationUA) {
        this.locationUA = locationUA;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConferenceDetails that = (ConferenceDetails) object;
        return id == that.id &&
                nameEN.equals(that.nameEN) &&
                nameUA.equals(that.nameUA) &&
                locationEN.equals(that.locationEN) &&
                locationUA.equals(that.locationUA) &&
                dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameEN, nameUA, locationEN, locationUA, dateTime);
    }

    @Override
    public String toString() {
        return "ConferenceDetails{" +
                "id=" + id +
                ", nameEN='" + nameEN + '\'' +
                ", nameUA='" + nameUA + '\'' +
                ", locationEN='" + locationEN + '\'' +
                ", locationUA='" + locationUA + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static class Builder {

        private long id;
        private String nameEN;
        private String nameUA;
        private String locationEN;
        private String locationUA;
        private LocalDateTime dateTime;

        public ConferenceDetails build() {
            return new ConferenceDetails(this);
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setNameEN(String nameEN) {
            this.nameEN = nameEN;
            return this;
        }

        public Builder setNameUA(String nameUA) {
            this.nameUA = nameUA;
            return this;
        }

        public Builder setLocationEN(String locationEN) {
            this.locationEN = locationEN;
            return this;
        }

        public Builder setLocationUA(String locationUA) {
            this.locationUA = locationUA;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }
    }
}
