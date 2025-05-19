package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.feature.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.feature.TestCommentResponseDTO;

public interface CommentTestService {
    TestCommentResponseDTO comment(TestCommentRequestDTO request);
}
