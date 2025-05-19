package com.englishweb.h2t_backside.service.ai;

import com.englishweb.h2t_backside.dto.feature.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.feature.ToeicCommentRequestDTO;

public interface ToeicCommentTestService {

    TestCommentResponseDTO comment(ToeicCommentRequestDTO request);
}
