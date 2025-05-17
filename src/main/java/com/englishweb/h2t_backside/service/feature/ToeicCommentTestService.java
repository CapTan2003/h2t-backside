package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.ToeicCommentRequestDTO;

public interface ToeicCommentTestService {

    TestCommentResponseDTO comment(ToeicCommentRequestDTO request);
}
