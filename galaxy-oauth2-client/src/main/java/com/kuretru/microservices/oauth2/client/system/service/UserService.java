package com.kuretru.microservices.oauth2.client.system.service;

import com.kuretru.microservices.authentication.entity.UserLoginDTO;
import com.kuretru.microservices.oauth2.client.system.entity.UserDTO;
import com.kuretru.microservices.oauth2.common.entity.GalaxyUserDTO;
import com.kuretru.microservices.web.exception.ServiceException;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface UserService {

    /**
     * 根据业务逻辑主键查询一条记录
     *
     * @param uuid 业务逻辑主键UUID
     * @return 一条记录，找不到时返回Null
     */
    UserDTO get(UUID uuid);

    /**
     * 用户登录
     *
     * @param record 登录请求实体
     * @return 登录响应实体
     * @throws ServiceException 登录失败时会产生异常
     */
    UserLoginDTO login(GalaxyUserDTO record) throws ServiceException;

    /**
     * 用户登出
     *
     * @param accessTokenId AccessTokenID
     * @throws ServiceException 登出失败时会产生异常
     */
    void logout(String accessTokenId) throws ServiceException;

}
