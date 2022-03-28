package com.kuretru.microservices.web.constant.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 错误发生在用户端的响应码枚举，范围在10000~19999之间
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Getter
public enum UserErrorCodes implements ResponseCodes {

    /** A0200-用户登录异常 */
    USER_LOGIN_ERROR(10200, "用户登录异常", HttpStatus.UNAUTHORIZED),
    /** A0201-用户账户不存在 */
    WRONG_USERNAME(10201, "用户账户不存在", HttpStatus.BAD_REQUEST),
    /** A0202-用户账户被冻结 */
    USER_FROZEN(10202, "用户账户被冻结", HttpStatus.UNAUTHORIZED),
    /** A0203-用户账户已作废 */
    USER_VOIDED(10203, "用户账户已作废", HttpStatus.UNAUTHORIZED),
    /** A0210-用户密码错误 */
    WRONG_PASSWORD(10210, "用户密码错误", HttpStatus.UNAUTHORIZED),
    /** A0211-用户输入密码错误次数超限 */
    MAX_TRY_LIMIT(10211, "用户输入密码错误次数超限", HttpStatus.UNAUTHORIZED),
    /** A0230-用户登录已过期 */
    USER_LOGIN_EXPIRED(10230, "用户登录已过期", HttpStatus.UNAUTHORIZED),

    /** A0300-访问权限异常 */
    ACCESS_PERMISSION_ERROR(10300, "访问权限异常", HttpStatus.FORBIDDEN),
    /** A0340-用户签名异常 */
    WRONG_USER_SIGNATURE(10340, "用户签名异常", HttpStatus.BAD_REQUEST),

    /** A0400-用户请求参数错误 */
    REQUEST_PARAMETER_ERROR(10400, "用户请求参数错误", HttpStatus.BAD_REQUEST),
    /** A0410-请求必填参数为空 */
    MISSING_REQUIRED_PARAMETERS(10410, "请求必填参数为空", HttpStatus.BAD_REQUEST),
    /** A0412-订购数量为空 */
    ORDER_QUANTITY_EMPTY(10412, "订购数量为空", HttpStatus.BAD_REQUEST),

    /** A0506-用户重复请求 */
    USER_REPEATED_REQUEST(10506, "用户重复请求", HttpStatus.CONFLICT);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    UserErrorCodes(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}