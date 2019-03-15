package com.kuretru.api.common.entity.transfer.weapp;

import com.kuretru.api.common.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 呉真 Kuretru < kuretru@gmail.com >
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WeappUserDTO extends BaseDTO {

    private String nickName;

    private String avatarUrl;

    private String gender;

    private String country;

    private String province;

    private String city;

}
