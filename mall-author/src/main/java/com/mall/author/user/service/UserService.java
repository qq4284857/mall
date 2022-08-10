package com.mall.author.user.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mall.api.user.entity.UserDetail;
import com.mall.author.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        List<UserDetail> list = userDao.queryByUser(username);
        if (list.isEmpty() || list.size() > 1) {
            return null;
        }
        UserDetail userDetail=list.get(0);
        if (StringUtils.isEmpty(userDetail.getUsername()) || StringUtils.isEmpty(userDetail.getPassword())) {
            return null;
        }

        return new User(userDetail.getUsername(), userDetail.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userDetail.getUsername()));
    }
}
