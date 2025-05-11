package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.TestCommentResponseDTO;

public interface CommentTestService {
    TestCommentResponseDTO comment(TestCommentRequestDTO request);
}
