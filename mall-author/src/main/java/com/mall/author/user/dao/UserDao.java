package com.mall.author.user.dao;

import com.mall.api.user.entity.UserDetail;

import java.util.List;

public interface UserDao {
    List<UserDetail> queryByUser(String username);
}
