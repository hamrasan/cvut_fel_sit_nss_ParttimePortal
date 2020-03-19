package cz.cvut.fel.rsp.travelandwork.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username AND u.deleted_at is null")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    @Size(max = 30, min = 1, message = "First name is in incorrect format.")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Size(max = 255, min = 3, message = "Username is in incorrect format.")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 255, min = 6, message = "Password is in incorrect format.")
    @JsonIgnore
    private String password;

    @Email(message = "Email should be valid")
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(@Size(max = 255, min = 3, message = "Username is in incorrect format.") String username,
                @Size(max = 255, min = 6, message = "Password is in incorrect format.") String password) {
        this.username = username;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    protected void setRole(Role role) {
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

//    public UserRole getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(UserRole userRole) {
//        this.userRole = userRole;
//    }
}
