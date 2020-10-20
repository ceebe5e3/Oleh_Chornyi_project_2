package chornyi.conferences.db.entity;

import java.util.Objects;

/**
 * User entity
 */

public class User {

    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

   private User(Builder builder){
       this.id = builder.id;
       this.login = builder.login;
       this.password = builder.password;
       this.firstName = builder.firstName;
       this.lastName = builder.lastName;
       this.email = builder.email;
       this.role = builder.role;
   }


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        User user = (User) object;
        return id == user.id &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, lastName, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder{
        private long id;
        private String login;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;

        public Builder setId(long id) {
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

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
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

        public User build() {
            return new User(this);
        }

    }
}
