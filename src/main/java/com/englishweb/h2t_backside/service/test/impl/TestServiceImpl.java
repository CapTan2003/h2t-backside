package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.repository.test.TestRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl extends BaseServiceImpl<TestDTO, Test, TestRepository> implements TestService {

    public TestServiceImpl(TestRepository repository, DiscordNotifierImpl discordNotifier) {
        super(repository, discordNotifier);
    }

    @Override
    protected void findByIdError(Long id) {

    }

    @Override
    protected void createError(TestDTO dto, Exception ex) {

    }

    @Override
    protected void updateError(TestDTO dto, Long id, Exception ex) {

    }

    @Override
    protected void patchEntityFromDTO(TestDTO dto, Test entity) {

    }

    @Override
    protected Test convertToEntity(TestDTO dto) {
        return null;
    }

    @Override
    protected TestDTO convertToDTO(Test entity) {
        return null;
    }
}
