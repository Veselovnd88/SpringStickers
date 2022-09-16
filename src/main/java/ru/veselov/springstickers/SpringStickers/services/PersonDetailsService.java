package ru.veselov.springstickers.SpringStickers.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.veselov.springstickers.SpringStickers.security.PersonDetails;
@Component
public class PersonDetailsService implements UserDetailsService {
    private String adminName="Nikolay";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals(adminName)){
            throw new UsernameNotFoundException("Wrong admin name");
        }
        else{
            return new PersonDetails();
        }
    }
}
