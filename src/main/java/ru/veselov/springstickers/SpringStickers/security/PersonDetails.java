package ru.veselov.springstickers.SpringStickers.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
/*Класс участвует в аутентификации СпрингСекьюрити*/
public class PersonDetails implements UserDetails {
    /*Возвращаются возможные роли, здесь так как одна роль - хардкод*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    /*Пароль захардкожен*/
    @Override
    public String getPassword() {
        return "$2y$10$EQRJGPJ5oNo15MmEyJfcdeCGYiN3qz04O6yMM9nChchqNXycEJ1Ka";
    }

    @Override
    public String getUsername() {
        return "Nikolay";
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
