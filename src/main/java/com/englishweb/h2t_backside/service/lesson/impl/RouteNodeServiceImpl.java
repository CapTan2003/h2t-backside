package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
import com.englishweb.h2t_backside.dto.lesson.*;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.RouteNode;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import com.englishweb.h2t_backside.repository.RouteNodeRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.*;
import com.englishweb.h2t_backside.service.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class RouteNodeServiceImpl extends BaseServiceImpl<RouteNodeDTO, RouteNode, RouteNodeRepository> implements RouteNodeService {

    private final RouteNodeMapper mapper;
    private final RouteService routeService;
    private final TopicService topicService;
    private final GrammarService grammarService;
    private final ReadingService readingService;
    private final WritingService writingService;
    private final SpeakingService speakingService;
    private final ListeningService listeningService;
    private final TestService testService;

    public RouteNodeServiceImpl(RouteNodeRepository repository, DiscordNotifier discordNotifier,
                                @Lazy RouteNodeMapper mapper, RouteService routeService,
                                @Lazy TopicService topicService,
                                @Lazy GrammarService grammarService,
                                @Lazy  ReadingService readingService,
                                @Lazy  WritingService writingService,
                                @Lazy  SpeakingService speakingService,
                                @Lazy  ListeningService listeningService,
                                @Lazy  TestService testService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.routeService = routeService;
        this.topicService = topicService;
        this.grammarService = grammarService;
        this.readingService = readingService;
        this.writingService = writingService;
        this.speakingService = speakingService;
        this.listeningService = listeningService;
        this.testService = testService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Route node with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(RouteNodeDTO dto, Exception ex) {
        log.error("Error creating route node: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating route node: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_NODE_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(RouteNodeDTO dto, Long id, Exception ex) {
        log.error("Error updating route node: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating route node: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_NODE_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Route node with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(RouteNodeDTO dto, RouteNode entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected RouteNode convertToEntity(RouteNodeDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected RouteNodeDTO convertToDTO(RouteNode entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<RouteNodeDTO> findByRouteId(Long routeId) {
        if (!routeService.isExist(routeId)) {
            throw new ResourceNotFoundException(routeId, String.format("Route with ID '%d' not found.", routeId));
        }

        Map<RouteNodeEnum, BiConsumer<RouteNodeDTO, Long>> typeProcessor = new EnumMap<>(RouteNodeEnum.class);
        typeProcessor.put(RouteNodeEnum.VOCABULARY, this::processTopic);
        typeProcessor.put(RouteNodeEnum.GRAMMAR, this::processGrammar);
        typeProcessor.put(RouteNodeEnum.READING, this::processReading);
        typeProcessor.put(RouteNodeEnum.WRITING, this::processWriting);
        typeProcessor.put(RouteNodeEnum.SPEAKING, this::processSpeaking);
        typeProcessor.put(RouteNodeEnum.LISTENING, this::processListening);
        typeProcessor.put(RouteNodeEnum.READING_TEST, this::processTest);
        typeProcessor.put(RouteNodeEnum.WRITING_TEST, this::processTest);
        typeProcessor.put(RouteNodeEnum.SPEAKING_TEST, this::processTest);
        typeProcessor.put(RouteNodeEnum.LISTENING_TEST, this::processTest);
        typeProcessor.put(RouteNodeEnum.MIXING_TEST, this::processTest);

        return repository.findByRoute_Id(routeId).stream()
                .map(mapper::convertToDTO)
                .peek(node -> Optional.ofNullable(typeProcessor.get(node.getType()))
                        .ifPresent(processor -> processor.accept(node, node.getNodeId())))
                .toList();
    }

    private void processTopic(RouteNodeDTO node, Long nodeId) {
        TopicDTO dto = topicService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processGrammar(RouteNodeDTO node, Long nodeId) {
        GrammarDTO dto = grammarService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processReading(RouteNodeDTO node, Long nodeId) {
        ReadingDTO dto = readingService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processWriting(RouteNodeDTO node, Long nodeId) {
        WritingDTO dto = writingService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processSpeaking(RouteNodeDTO node, Long nodeId) {
        SpeakingDTO dto = speakingService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processListening(RouteNodeDTO node, Long nodeId) {
        ListeningDTO dto = listeningService.findById(nodeId);
        setCommonFields(node, dto);
    }

    private void processTest(RouteNodeDTO node, Long nodeId) {
        TestDTO dto = testService.findById(nodeId);
        node.setTitle(dto.getTitle());
        node.setDescription(dto.getDescription());
        node.setImage(null);
    }

    private void setCommonFields(RouteNodeDTO node, LessonDTO dto) {
        node.setTitle(dto.getTitle());
        node.setDescription(dto.getDescription());
        node.setImage(dto.getImage());
    }

    private void setCommonFields(RouteNodeDTO node, TestDTO dto) {
        node.setTitle(dto.getTitle());
        node.setDescription(dto.getDescription());
        node.setImage(null);
    }
}
