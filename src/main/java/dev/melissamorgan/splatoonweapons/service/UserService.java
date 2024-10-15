package dev.melissamorgan.splatoonweapons.service;

import dev.melissamorgan.splatoonweapons.dao.UserDAO;
import dev.melissamorgan.splatoonweapons.entities.UserPrincipal;
import dev.melissamorgan.splatoonweapons.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Users user = userDAO.findByName(name);
        if(user == null) {
            throw new UsernameNotFoundException(name);
        }
        return new UserPrincipal(user);
    }
}
