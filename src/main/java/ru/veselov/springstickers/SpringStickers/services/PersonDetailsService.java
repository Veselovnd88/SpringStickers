package ru.veselov.springstickers.SpringStickers.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.veselov.springstickers.SpringStickers.security.PersonDetails;
/*Класс который участвует в аутентификации*/
@Component
public class PersonDetailsService implements UserDetailsService {
    private String adminName="Nikolay";
    /*Метод проверяет наличие имени пользователя (тут админа, т.к. пользователь один то нет
    * обращений к бд, а имя захардкожено в классе*/
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
