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
    public static final String LESSON_CREATED_FAIL = "201"; // Tao moi bai hoc that bai
    public static final String LESSON_UPDATED_FAIL = "202"; // Cap nhat bai hoc that bai
    public static final String LESSON_NOT_FOUND = "204"; // Không tìm thấy bài học
    public static final String VOCABULARY_CREATED_FAIL = "205"; // Tao moi tu vung that bai
    public static final String VOCABULARY_UPDATED_FAIL = "206"; // Cap nhat tu vung that bai
    public static final String ROUTE_CREATED_FAIL = "207"; // Tao moi tu vung that bai
    public static final String ROUTE_UPDATED_FAIL = "208"; // Cap nhat tu vung that bai
    public static final String ROUTE_NODE_CREATED_FAIL = "209"; // Tao moi tu vung that bai
    public static final String ROUTE_NODE_UPDATED_FAIL = "210"; // Cap nhat tu vung that bai
    public static final String PREPARATION_CREATED_FAIL = "211"; // Thêm mới phần chuẩn bị thất bại
    public static final String PREPARATION_UPDATED_FAIL = "211"; // Cập nhật phần chuẩn bị thất bại
    public static final String LESSON_ANSWER_CREATED_FAIL = "212"; // Thêm mới câu trả lời cho bài học thất bại
    public static final String LESSON_ANSWER_UPDATED_FAIL = "213"; // Cập nhật câu trả lời cho bài học thất bại
    public static final String LESSON_QUESTION_CREATED_FAIL = "214"; // Thêm mới câu hoi cho bài học thất bại
    public static final String LESSON_QUESTION_UPDATED_FAIL = "215"; // Cập nhật câu hoi cho bài học thất bại

    // Lỗi liên quan đến bài kiểm tra
    public static final String TEST_NOT_FOUND = "304"; // Không tìm thấy bài học

    // Lỗi liên quan đến gọi api bên ngoài

    // Các lỗi khác
    public static final String RESOURCE_NOT_FOUND = "404";
    public static final String ARGUMENT_DTO_INVALID = "601";
    public static final String PAGE_INDEX_INVALID = "602";
    public static final String PAGE_SIZE_INVALID = "603";
    public static final String SORT_FIELD_INVALID = "604";
    public static final String ARGUMENT_TYPE_MISMATCH = "605";
}
