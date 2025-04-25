package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.RouteDTO;
import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.teacherdashboard.LessonDataDTO;
import com.englishweb.h2t_backside.dto.teacherdashboard.TeacherDashboardDTO;
import com.englishweb.h2t_backside.dto.teacherdashboard.TestDataDTO;
import com.englishweb.h2t_backside.service.feature.TeacherDashboardService;
import com.englishweb.h2t_backside.service.lesson.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherDashboardServiceImpl implements TeacherDashboardService {

    private final RouteService routeService;

    public TeacherDashboardServiceImpl(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public TeacherDashboardDTO getTeacherDashboardByTeacherId(Long teacherId) {
        List<RouteDTO> routes = routeService.findByOwnerId(teacherId);
        long totalRoutes = routes.size();
        long totalTopics = 0;
        long totalGrammars = 0;
        long totalReadings = 0;
        long totalWritings = 0;
        long totalSpeakings = 0;
        long totalListenings = 0;
        long totalMixingTests = 0;
        long totalListeningTests = 0;
        long totalWritingTests = 0;
        long totalSpeakingTests = 0;
        long totalReadingTests = 0;
        long activeContent = 0;
        long inactiveContent = 0;

        for (RouteDTO route: routes) {
            for (RouteNodeDTO routeNode: route.getRouteNodes()){
                if (routeNode.getStatus())
                    ++activeContent;
                else
                    ++inactiveContent;
                switch (routeNode.getType()) {
                    case VOCABULARY -> ++totalTopics;
                    case GRAMMAR -> ++totalGrammars;
                    case READING -> ++totalReadings;
                    case WRITING -> ++totalWritings;
                    case SPEAKING -> ++totalSpeakings;
                    case LISTENING -> ++totalListenings;
                    case MIXING_TEST -> ++totalMixingTests;
                    case LISTENING_TEST -> ++totalListeningTests;
                    case READING_TEST -> ++totalReadingTests;
                    case WRITING_TEST -> ++totalWritingTests;
                    case SPEAKING_TEST -> ++totalSpeakingTests;
                }
            }
        }

        long totalLessons = totalTopics + totalGrammars + totalReadings + totalListenings + totalSpeakings + totalWritings;
        long totalTests = totalListeningTests + totalMixingTests + totalReadingTests + totalSpeakingTests + totalWritingTests;

        return TeacherDashboardDTO.builder()
                .lessonData(LessonDataDTO.builder()
                        .totalTopics(totalTopics)
                        .totalGrammars(totalGrammars)
                        .totalReadings(totalReadings)
                        .totalListenings(totalListenings)
                        .totalSpeakings(totalSpeakings)
                        .totalWritings(totalWritings)
                        .build())
                .testData(TestDataDTO.builder()
                        .totalListeningTests(totalListeningTests)
                        .totalMixingTests(totalMixingTests)
                        .totalReadingTests(totalReadingTests)
                        .totalSpeakingTests(totalSpeakingTests)
                        .totalWritingTests(totalWritingTests)
                        .build())
                .totalLessons(totalLessons)
                .totalTests(totalTests)
                .totalRoutes(totalRoutes)
                .activeContent(activeContent)
                .inactiveContent(inactiveContent)
                .build();
    }
}
