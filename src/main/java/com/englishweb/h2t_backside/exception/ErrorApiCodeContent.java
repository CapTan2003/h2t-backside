package com.englishweb.h2t_backside.exception;

public class ErrorApiCodeContent {
    private ErrorApiCodeContent() {}

    // Loi lien quan den AIResponse
    public static final String AIRESPONSE_CREATED_FAIL = "201"; // Tao moi AIResponse that bai
    public static final String AIRESPONSE_UPDATED_FAIL = "202"; // Cap nhat AIResponse that bai

    // Lỗi liên quan đến người dùng và xác thực
    public static final String USER_CREATED_FAIL = "101"; // Tao moi user that bai
    public static final String USER_EMAIL_EXIST = "102";
    public static final String USER_EMAIL_EMPTY = "103";
    public static final String USER_NOT_FOUND = "104"; // Không tìm thấy user
    public static final String USER_NAME_EMPTY = "105"; // Không tìm thấy user
    public static final String USER_UPDATED_FAIL = "106"; // Cap nhat user that bai

    // Loi lien quan den authen
    public static final String AUTHENTICATION_FAILED = "201"; // Xác thực thất bại
    public static final String TOKEN_EXPIRED = "202";         // Token hết hạn
    public static final String TOKEN_INVALID = "203";         // Token không hợp lệ

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
    public static final String TEST_NOT_FOUND = "304";
    public static final String COMMENT_TEST_EXCEPTION = "305";
    public static final String TEST_CREATED_FAIL = "306";
    public static final String TEST_UPDATED_FAIL = "307";
    public static final String TEST_READING_CREATED_FAIL = "308";
    public static final String TEST_READING_UPDATED_FAIL = "309";
    public static final String TEST_LISTENING_CREATED_FAIL = "310";
    public static final String TEST_LISTENING_UPDATED_FAIL = "311";
    public static final String TEST_SPEAKING_CREATED_FAIL = "312";
    public static final String TEST_SPEAKING_UPDATED_FAIL = "313";
    public static final String TEST_WRITING_CREATED_FAIL = "314";
    public static final String TEST_WRITING_UPDATED_FAIL = "315";
    public static final String TEST_PART_CREATED_FAIL = "316";
    public static final String TEST_PART_UPDATED_FAIL = "317";
    public static final String COMPETITION_TEST_CREATED_FAIL = "318";
    public static final String COMPETITION_TEST_UPDATED_FAIL = "319";
    public static final String SUBMIT_TEST_CREATED_FAIL = "320";
    public static final String SUBMIT_TEST_UPDATED_FAIL = "321";
    public static final String SUBMIT_TEST_ANSWER_CREATED_FAIL = "322";
    public static final String SUBMIT_TEST_ANSWER_UPDATED_FAIL = "323";
    public static final String SUBMIT_TEST_SPEAKING_CREATED_FAIL = "324";
    public static final String SUBMIT_TEST_SPEAKING_UPDATED_FAIL = "325";
    public static final String SUBMIT_TEST_WRITING_CREATED_FAIL = "326";
    public static final String SUBMIT_TEST_WRITING_UPDATED_FAIL = "327";
    public static final String SUBMIT_COMPETITION_CREATED_FAIL = "328";
    public static final String SUBMIT_COMPETITION_UPDATED_FAIL = "329";
    public static final String SUBMIT_COMPETITION_ANSWER_CREATED_FAIL = "330";
    public static final String SUBMIT_COMPETITION_ANSWER_UPDATED_FAIL = "331";
    public static final String SUBMIT_COMPETITION_SPEAKING_CREATED_FAIL = "332";
    public static final String SUBMIT_COMPETITION_SPEAKING_UPDATED_FAIL = "333";
    public static final String SUBMIT_COMPETITION_WRITING_CREATED_FAIL = "334";
    public static final String SUBMIT_COMPETITION_WRITING_UPDATED_FAIL = "335";
    public static final String QUESTION_CREATED_FAIL = "336";
    public static final String QUESTION_UPDATED_FAIL = "337";
    public static final String ANSWER_CREATED_FAIL = "338";
    public static final String ANSWER_UPDATED_FAIL = "339";
    public static final String TOEIC_CREATED_FAIL = "340";
    public static final String TOEIC_UPDATED_FAIL = "341";
    public static final String TOEIC_ANSWER_CREATED_FAIL = "342";
    public static final String TOEIC_ANSWER_UPDATED_FAIL = "343";
    public static final String TOEIC_PART1_CREATED_FAIL = "344";
    public static final String TOEIC_PART1_UPDATED_FAIL = "345";
    public static final String TOEIC_PART2_CREATED_FAIL = "346";
    public static final String TOEIC_PART2_UPDATED_FAIL = "347";
    public static final String TOEIC_PART3_4_CREATED_FAIL = "348";
    public static final String TOEIC_PART3_4_UPDATED_FAIL = "349";
    public static final String TOEIC_PART6_CREATED_FAIL = "350";
    public static final String TOEIC_PART6_UPDATED_FAIL = "351";
    public static final String TOEIC_PART7_CREATED_FAIL = "352";
    public static final String TOEIC_PART7_UPDATED_FAIL = "353";
    public static final String TOEIC_QUESTION_CREATED_FAIL = "354";
    public static final String TOEIC_QUESTION_UPDATED_FAIL = "355";
    public static final String SUBMIT_TOEIC_CREATED_FAIL = "356";
    public static final String SUBMIT_TOEIC_UPDATED_FAIL = "357";
    public static final String SUBMIT_TOEIC_ANSWER_CREATED_FAIL = "358";
    public static final String SUBMIT_TOEIC_ANSWER_UPDATED_FAIL = "359";
    public static final String SUBMIT_TOEIC_PART1_CREATED_FAIL = "360";
    public static final String SUBMIT_TOEIC_PART1_UPDATED_FAIL = "361";
    public static final String SUBMIT_TOEIC_PART2_CREATED_FAIL = "362";
    public static final String SUBMIT_TOEIC_PART2_UPDATED_FAIL = "363";

    // Lỗi liên quan đến gọi api bên ngoài
    public static final String ERROR_LOG_CREATED_FAIL = "301";
    public static final String ERROR_LOG_UPDATED_FAIL = "302";

    // Các lỗi khác
    public static final String RESOURCE_NOT_FOUND = "404";
    public static final String UNEXPECTED_ERROR = "500";
    public static final String ARGUMENT_DTO_INVALID = "601";
    public static final String PAGE_INDEX_INVALID = "602";
    public static final String PAGE_SIZE_INVALID = "603";
    public static final String SORT_FIELD_INVALID = "604";
    public static final String ARGUMENT_TYPE_MISMATCH = "605";
    public static final String MISSING_REQUEST_PARAMETER = "606"; // Thieu tham so
    public static final String IO_EXCEPTION = "607";
    public static final String BUCKET_CREATED_FAIL = "608"; // Tạo bucket minio that bai
    public static final String FILE_UPLOAD_FAIL = "609"; // Tạo file minio that bai
    public static final String URL_GENERATION_FAIL = "610"; // Tạo url minio that bai
    public  static final String FILE_DOWNLOAD_FAIL = "611";  // Tải file tu minio that bai
    public static final String FILE_DELETE_FAIL = "612"; // Xóa file tu minio that bai
    public static final String UNAUTHORIZED = "401";  // Xác thực thất bại
    public static final String OPEN_ROUTER_EXCEPTION = "613";
    public static final String JSON_PROCESSING_EXCEPTION = "614";
    public static final String SPEECH_PROCESSING_EXCEPTION = "615";
    public static final String TEXT_TO_SPEECH_EXCEPTION = "616";
    public static final String CLIENT_DISCONNECTED = "617";
}
