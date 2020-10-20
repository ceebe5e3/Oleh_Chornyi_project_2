package chornyi.conferences.db.entity.details;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConversationDetails {

    private long id;
    private String topicEN;
    private String topicUA;
    private LocalDateTime dateTime;
    private long speakerId;

    private ConversationDetails(Builder builder) {
        this.id = builder.id;
        this.topicEN = builder.topicEN;
        this.topicUA = builder.topicUA;
        this.dateTime = builder.dateTime;
        this.speakerId = builder.speakerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopicEN() {
        return topicEN;
    }

    public void setTopicEN(String topicEN) {
        this.topicEN = topicEN;
    }

    public String getTopicUA() {
        return topicUA;
    }

    public void setTopicUA(String topicUA) {
        this.topicUA = topicUA;
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
        ConversationDetails that = (ConversationDetails) object;
        return id == that.id &&
                speakerId == that.speakerId &&
                topicEN.equals(that.topicEN) &&
                topicUA.equals(that.topicUA) &&
                dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicEN, topicUA, dateTime, speakerId);
    }

    @Override
    public String toString() {
        return "ConversationDetails{" +
                "id=" + id +
                ", topicEN='" + topicEN + '\'' +
                ", topicUA='" + topicUA + '\'' +
                ", dateTime=" + dateTime +
                ", speakerId=" + speakerId +
                '}';
    }

    public static class Builder {

        private long id;
        private String topicEN;
        private String topicUA;
        private LocalDateTime dateTime;
        private long speakerId;

        public ConversationDetails build() {
            return new ConversationDetails(this);
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTopicEN(String topicEN) {
            this.topicEN = topicEN;
            return this;
        }

        public Builder setTopicUA(String topicUA) {
            this.topicUA = topicUA;
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
