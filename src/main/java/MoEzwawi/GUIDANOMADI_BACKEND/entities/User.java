package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "users")
@Getter
@Setter
@JsonIgnoreProperties({"password", "authorities", "accountNonExpired", "enabled", "accountNonLocked", "credentialsNonExpired"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
