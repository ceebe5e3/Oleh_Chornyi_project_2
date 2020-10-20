package chornyi.conferences.db.entity.details;

import chornyi.conferences.db.entity.Role;

import java.util.Objects;

public class UserDetails {

    private int id;
    private String login;
    private String password;
    private String firstNameEN;
    private String firstNameUA;
    private String lastNameEN;
    private String lastNameUA;
    private String email;
    private Role role;

    private UserDetails(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.firstNameEN = builder.firstNameEN;
        this.firstNameUA = builder.firstNameUA;
        this.lastNameEN = builder.lastNameEN;
        this.lastNameUA = builder.lastNameUA;
        this.email = builder.email;
        this.role = builder.role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstNameEN() {
        return firstNameEN;
    }

    public void setFirstNameEN(String firstNameEN) {
        this.firstNameEN = firstNameEN;
    }

    public String getFirstNameUA() {
        return firstNameUA;
    }

    public void setFirstNameUA(String firstNameUA) {
        this.firstNameUA = firstNameUA;
    }

    public String getLastNameEN() {
        return lastNameEN;
    }

    public void setLastNameEN(String lastNameEN) {
        this.lastNameEN = lastNameEN;
    }

    public String getLastNameUA() {
        return lastNameUA;
    }

    public void setLastNameUA(String lastNameUA) {
        this.lastNameUA = lastNameUA;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDetails that = (UserDetails) object;
        return id == that.id &&
                login.equals(that.login) &&
                password.equals(that.password) &&
                firstNameEN.equals(that.firstNameEN) &&
                firstNameUA.equals(that.firstNameUA) &&
                lastNameEN.equals(that.lastNameEN) &&
                lastNameUA.equals(that.lastNameUA) &&
                email.equals(that.email) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstNameEN, firstNameUA, lastNameEN, lastNameUA, email, role);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstNameEN='" + firstNameEN + '\'' +
                ", firstNameUA='" + firstNameUA + '\'' +
                ", lastNameEN='" + lastNameEN + '\'' +
                ", lastNameUA='" + lastNameUA + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder {

        private int id;
        private String login;
        private String password;
        private String firstNameEN;
        private String firstNameUA;
        private String lastNameEN;
        private String lastNameUA;
        private String email;
        private Role role;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstNameEN(String firstNameEN) {
            this.firstNameEN = firstNameEN;
            return this;
        }

        public Builder setFirstNameUA(String firstNameUA) {
            this.firstNameUA = firstNameUA;
            return this;
        }

        public Builder setLastNameEN(String lastNameEN) {
            this.lastNameEN = lastNameEN;
            return this;
        }

        public Builder setLastNameUA(String lastNameUA) {
            this.lastNameUA = lastNameUA;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(this);
        }
    }
}
