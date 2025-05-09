package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.HistoryStatsDTO;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionRepository;
import com.englishweb.h2t_backside.repository.test.SubmitTestRepository;
import com.englishweb.h2t_backside.repository.test.SubmitToeicRepository;
import com.englishweb.h2t_backside.service.test.TestHistoryStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TestHistoryStatsServiceImpl implements TestHistoryStatsService {

    private final SubmitTestRepository submitTestRepository;
    private final SubmitToeicRepository submitToeicRepository;
    private final SubmitCompetitionRepository submitCompetitionRepository;

    @Override
    public HistoryStatsDTO getStatisticsForUser(Long userId, Boolean status) {
        List<Integer> allScores = new ArrayList<>();

        List<SubmitTest> submitTests = (status != null)
                ? submitTestRepository.findAllByUserIdAndStatus(userId, status)
                : submitTestRepository.findAllByUserId(userId);

        List<SubmitToeic> submitToeics = (status != null)
                ? submitToeicRepository.findAllByUserIdAndStatus(userId, status)
                : submitToeicRepository.findAllByUserId(userId);

        List<SubmitCompetition> submitCompetitions = (status != null)
                ? submitCompetitionRepository.findAllByUserIdAndStatus(userId, status)
                : submitCompetitionRepository.findAllByUserId(userId);

        for (SubmitTest s : submitTests) {
            if (s.getScore() != null) allScores.add(s.getScore());
        }

        for (SubmitToeic s : submitToeics) {
            if (s.getScore() != null) allScores.add(s.getScore());
        }

        for (SubmitCompetition s : submitCompetitions) {
            if (s.getScore() != null) allScores.add(s.getScore());
        }

        int total = allScores.size();
        int max = allScores.stream().max(Integer::compare).orElse(0);
        double avg = total > 0 ? allScores.stream().mapToInt(Integer::intValue).average().orElse(0.0) : 0.0;

        return HistoryStatsDTO.builder()
                .averageScore(avg)
                .highestScore(max)
                .totalTestsTaken(total)
                .build();
    }
}