package com.englishweb.h2t_backside.exception;

public class ErrorApiCodeContent {
    private ErrorApiCodeContent() {}

    // Lỗi liên quan đến người dùng và xác thực
    public static final String USER_CREATED_FAIL = "101"; // Tao moi user that bai
    public static final String USER_EMAIL_EXIST = "102";
    public static final String USER_EMAIL_EMPTY = "103";
    public static final String USER_NOT_FOUND = "104"; // Không tìm thấy user
    public static final String USER_NAME_EMPTY = "105"; // Không tìm thấy user
    public static final String USER_UPDATED_FAIL = "106"; // Cap nhat user that bai

    // Lỗi liên quan đến bài học
    public static final String LESSON_CREATED_FAIL = "101"; // Tao moi bai hoc that bai
    public static final String LESSON_UPDATED_FAIL = "101";
    public static final String LESSON_NOT_FOUND = "204"; // Không tìm thấy bài học

    // Lỗi liên quan đến bài kiểm tra
    public static final String TEST_NOT_FOUND = "304"; // Không tìm thấy bài học

    // Lỗi liên quan đến gọi api bên ngoài

    // Các lỗi khác
    public static final String ARGUMENT_DTO_INVALID = "501";
}
