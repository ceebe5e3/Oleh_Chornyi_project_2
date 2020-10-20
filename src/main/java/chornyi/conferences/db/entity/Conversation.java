package chornyi.conferences.db.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Conversation entity
 */

public class Conversation {

    private long id;
    private String topic;
    private LocalDateTime dateTime;
    private long speakerId;

    private Conversation(Builder builder) {
        this.id = builder.id;
        this.topic = builder.topic;
        this.dateTime = builder.dateTime;
        this.speakerId = builder.speakerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(long speakerId) {
        this.speakerId = speakerId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Conversation that = (Conversation) object;
        return id == that.id &&
                speakerId == that.speakerId &&
                topic.equals(that.topic) &&
                dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, dateTime, speakerId);
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", dateTime=" + dateTime +
                ", speakerId=" + speakerId +
                '}';
    }

    public static class Builder {
        private long id;
        private String topic;
        private LocalDateTime dateTime;
        private long speakerId;

        public Conversation build() {
            return new Conversation(this);
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setSpeakerId(long speakerId) {
            this.speakerId = speakerId;
            return this;
        }
    }
}
