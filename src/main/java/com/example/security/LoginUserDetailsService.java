package com.example.security;

import com.example.dao.MstEmployeeDao;
import org.springframework.stereotype.Service;

import com.example.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by ko-aoki on 2016/09/03.
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    MstEmployeeDao dao;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity user = dao.selectUser(id);

        UserInfo userInfo = new UserInfo();

        userInfo.setId(user.getEmployeeId());
        userInfo.setEmployeeFirstName(user.getEmployeeFirstName());
        userInfo.setEmployeeLastName(user.getEmployeeLastName());
        userInfo.setRoleId(user.getRoleId());
        userInfo.setPassword(user.getPassword());

        if (userInfo == null) {
            throw new UsernameNotFoundException("The requested user is not found.");
        }
        return new LoginUserDetails(userInfo);
    }
}
