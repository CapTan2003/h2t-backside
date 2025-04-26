package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface TestListeningService extends BaseService<TestListeningDTO> {
    List<TestListeningDTO> findByIds(List<Long> ids);
    List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status);

}
