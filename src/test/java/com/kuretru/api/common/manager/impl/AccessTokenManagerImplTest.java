package com.kuretru.api.common.manager.impl;

import com.kuretru.api.common.entity.business.AccessTokenBO;
import com.kuretru.api.common.entity.enums.UserRoleEnum;
import com.kuretru.api.common.exception.ApiException;
import com.kuretru.api.common.exception.AuthenticationFailedException;
import com.kuretru.api.common.exception.TokenInvalidException;
import com.kuretru.api.common.manager.AccessTokenManager;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 呉真 Kuretru < kuretru@gmail.com >
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class AccessTokenManagerImplTest {

    private static AccessTokenBO model = AccessTokenBO.build(1L, UserRoleEnum.USER);
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private AccessTokenManager manager;

    @Test
    public void aCreateToken() {
        manager.createToken(model);
    }

    @Test
    public void bVerifyToken() throws ApiException {
        manager.verifyToken(model.getToken());
    }

    @Test
    public void cRemoveToken() throws ApiException {
        manager.removeToken(model.getToken());
        thrown.expect(TokenInvalidException.class);
        manager.verifyToken(model.getToken());
    }

}