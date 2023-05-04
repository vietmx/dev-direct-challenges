package com.maixuanviet.internal.security;

import com.maixuanviet.internal.logger.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VietMX
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private MyLog log;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAdminEntity appUser = userAdminRepos.findByUsername(username);
//        if (appUser == null) {
//            log.error("User not found! " + username);
//            throw new UsernameNotFoundException("User " + username + " was not found in the database");
//        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        String userRole = "ROLE_USER";
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        grantList.add(authority);

        // Example authentication user
        String usernameExample = "admin";
        String passwordExample = "$68h#8hfhf2(&H";

        UserDetails userDetails = new User(usernameExample, passwordExample, grantList);

        return userDetails;
    }

}
