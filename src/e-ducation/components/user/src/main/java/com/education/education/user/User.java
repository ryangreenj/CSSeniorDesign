package com.education.education.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
public class User implements UserDetails {

    private final String  id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;



    /**  GETTERS  **/
    // TODO - use @JsonIgnore as much as possible to supress potentially dangerous information from leaking
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore // @GetMapping serializes all getters, so this suppresses this from being returned
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public static UserBuilder aUserBuilder(){

        List<GrantedAuthority> tempAuthorities = new ArrayList<>();
        tempAuthorities.add(new SimpleGrantedAuthority("USER"));
        tempAuthorities.add(new SimpleGrantedAuthority("ADMIN"));


        return User.builder()
                .authorities(tempAuthorities);
    }
}
