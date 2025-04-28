package com.englishweb.h2t_backside.service.homepage.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
import com.englishweb.h2t_backside.service.homepage.FeatureLessonService;
import com.englishweb.h2t_backside.service.lesson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeatureLessonServiceImpl implements FeatureLessonService {
    private static final int DEFAULT_LIMIT = 4;
    private static final int DEFAULT_ACCURACY = 20;

    // Services
    private final GrammarService grammarService;
    private final ListeningService listeningService;
    private final ReadingService readingService;
    private final SpeakingService speakingService;
    private final TopicService topicService;
    private final WritingService writingService;

    @Override
    public List<LessonDTO> getMostViewedLessons(Integer limit) {
        log.info("Fetching {} most viewed lessons", limit);
        return getFilteredLessons(limit, "views");
    }
    @Override
    public List<LessonDTO> getMostRecentLessons(Integer limit) {
        log.info("Fetching {} most recent lessons", limit);
        return getFilteredLessons(limit, "-createdAt");
    }

    /**
     * Fetches lessons from all services based on provided sorting and filtering criteria
     *
     * @param limit Maximum number of lessons to return
     * @param sortBy Field to sort by
     * @return List of filtered and sorted lessons
     */
    private List<LessonDTO> getFilteredLessons(Integer limit, String sortBy) {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        LessonFilterDTO lessonFilterDTO = new LessonFilterDTO();
        lessonFilterDTO.setStatus(true);

        // Map of services and their corresponding fetch function
        Map<String, Function<LessonFilterDTO, Page<? extends LessonDTO>>> serviceMap = Map.of(
                "Grammar", dto -> grammarService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Topic", dto -> topicService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Listening", dto -> listeningService.searchWithFilters(DEFAULT_ACCURACY, 20, sortBy, dto),
                "Reading", dto -> readingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Speaking", dto -> speakingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto),
                "Writing", dto -> writingService.searchWithFilters(0, DEFAULT_ACCURACY, sortBy, dto)
        );

        // Fetch and combine all lessons
        List<LessonDTO> allLessons = serviceMap.entrySet().stream()
                .map(entry -> {
                    try {
                        log.debug("Fetching {} lessons", entry.getKey());
                        return entry.getValue().apply(lessonFilterDTO).getContent();
                    } catch (Exception e) {
                        log.error("Error fetching {} lessons: {}", entry.getKey(), e.getMessage());
                        return Collections.<LessonDTO>emptyList();
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // For most viewed lessons, sort by views
        if ("views".equals(sortBy)) {
            allLessons.sort(Comparator.comparing(LessonDTO::getViews).reversed());
        }

        return allLessons.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}