package com.englishweb.h2t_backside.model.log;

import com.englishweb.h2t_backside.model.enummodel.ActionEnum;

import java.time.LocalDateTime;

public class UpdateLog {
    private Long id;
    private Long userId;                // Id người dùng thực hiện update
    private Long targetId;              // Id đối tượng được update
    private String targetTable;         // Tên bảng của đối tượng được update
    private LocalDateTime timestamp;    // Thời gian thực hiện update
    private ActionEnum action;          // Hành động được thực hiện: CREATE, UPDATE, DELETE
}