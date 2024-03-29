package com.kuretru.microservices.common.utils;

import org.springframework.util.StringUtils;

/**
 * 命名方式工具类
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class CaseUtils {

    public static final String SNAKE_STRING = "_";
    public static final char SNAKE_CHAR = '_';

    private CaseUtils() {

    }

    /**
     * snake_case 转换为 camelCase
     *
     * @param snakeCase snake_case
     * @return camelCase
     */
    public static String snakeToCamel(String snakeCase) {
        if (!StringUtils.hasText(snakeCase)) {
            return snakeCase;
        }
        char[] snake = snakeCase.toCharArray();
        char[] camel = new char[snake.length];
        int length = 0;
        for (int i = 0; i < snake.length; i++) {
            if (snake[i] == SNAKE_CHAR) {
                if (i + 1 < snake.length) {
                    snake[i + 1] = Character.toUpperCase(snake[i + 1]);
                }
            } else {
                camel[length++] = snake[i];
            }
        }
        return new String(camel, 0, length);
    }

}
