package com.example.sboot.model;

import com.example.sboot.utils.JsonDeserializers;
import com.example.sboot.utils.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends BaseEntity implements Serializable {

    public User(Integer id, String email, String firstName, String lastName, String password, Collection<Role> roles) {
        this(email, firstName, lastName, password, roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles));
        this.id = id;
    }

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml(message = "XSS vulnerable E-mail!")
    private String email;

    @Column(name = "first_name")
    @Size(max = 128)
    @NoHtml(message = "XSS vulnerable first name!")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 128)
    @NoHtml(message = "XSS vulnerable last name!")
    private String lastName;

    @Column(name = "password")
    @Size(max = 256)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = JsonDeserializers.PasswordDeserializer.class)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void setEmail(String email) {
        this.email = StringUtils.hasLength(email) ? email.toLowerCase() : null;
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }
}
