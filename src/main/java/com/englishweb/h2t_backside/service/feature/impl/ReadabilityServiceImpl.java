package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.readability.ReadabilityMetrics;
import com.englishweb.h2t_backside.service.feature.ReadabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ReadabilityServiceImpl implements ReadabilityService {

    // Pattern để phân chia câu
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)");

    // Pattern để tìm từ
    private static final Pattern WORD_PATTERN = Pattern.compile("\\b[a-zA-Z]+\\b");

    // Pattern để nhận biết các nguyên âm
    private static final Pattern VOWEL_PATTERN = Pattern.compile("[aeiouy]+");

    // Danh sách các hậu tố phổ biến (để tính toán nguyên âm chính xác hơn)
    private static final List<String> COMMON_SUFFIXES = Arrays.asList("es", "ed", "e");

    @Override
    public ReadabilityMetrics calculateReadabilityMetrics(String text) {
        log.info("Calculating readability metrics for text of length: {} characters", text.length());

        ReadabilityMetrics metrics = new ReadabilityMetrics();

        // Tính toán các thống kê cơ bản
        List<String> sentences = extractSentences(text);
        List<String> words = extractWords(text);
        int totalSyllables = countTotalSyllables(words);
        int complexWords = countComplexWords(words);

        metrics.setTotalSentences(sentences.size());
        metrics.setTotalWords(words.size());
        metrics.setTotalSyllables(totalSyllables);
        metrics.setComplexWords(complexWords);

        // Tính toán tỷ lệ trung bình
        double averageWordsPerSentence = sentences.isEmpty() ? 0 : (double) words.size() / sentences.size();
        double averageSyllablesPerWord = words.isEmpty() ? 0 : (double) totalSyllables / words.size();

        metrics.setAverageWordsPerSentence(averageWordsPerSentence);
        metrics.setAverageSyllablesPerWord(averageSyllablesPerWord);

        // Tính toán các chỉ số độ đọc
        calculateReadabilityIndices(metrics);

        // Tính toán điểm đọc từ 1-10 và mô tả
        setReadabilityScoreAndDescription(metrics);

        log.info("Readability calculation completed. Flesch Reading Ease: {}, Grade Level: {}",
                metrics.getFleschReadingEase(), metrics.getFleschKincaidGradeLevel());

        return metrics;
    }

    private List<String> extractSentences(String text) {
        List<String> sentences = new ArrayList<>();
        Matcher matcher = SENTENCE_PATTERN.matcher(text);

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }

        return sentences;
    }

    private List<String> extractWords(String text) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(text.toLowerCase());

        while (matcher.find()) {
            String word = matcher.group().trim();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }

    private int countSyllables(String word) {
        word = word.toLowerCase().trim();

        // Xử lý các trường hợp đặc biệt
        if (word.length() <= 3) {
            return 1;
        }

        // Loại bỏ các hậu tố phổ biến
        for (String suffix : COMMON_SUFFIXES) {
            if (word.endsWith(suffix)) {
                word = word.substring(0, word.length() - suffix.length());
                break;
            }
        }

        // Đếm nguyên âm
        Matcher matcher = VOWEL_PATTERN.matcher(word);
        int count = 0;

        while (matcher.find()) {
            count++;
        }

        // Điều chỉnh cho các trường hợp đặc biệt
        if (count == 0) {
            count = 1;  // Mọi từ đều có ít nhất 1 âm tiết
        }

        return count;
    }

    private int countTotalSyllables(List<String> words) {
        int totalSyllables = 0;

        for (String word : words) {
            totalSyllables += countSyllables(word);
        }

        return totalSyllables;
    }

    private int countComplexWords(List<String> words) {
        int complexWords = 0;

        for (String word : words) {
            if (countSyllables(word) >= 3) {
                complexWords++;
            }
        }

        return complexWords;
    }

    private void calculateReadabilityIndices(ReadabilityMetrics metrics) {
        // Các biến để tính toán
        int totalSentences = metrics.getTotalSentences();
        int totalWords = metrics.getTotalWords();
        int totalSyllables = metrics.getTotalSyllables();
        int complexWords = metrics.getComplexWords();
        double averageWordsPerSentence = metrics.getAverageWordsPerSentence();
        double averageSyllablesPerWord = metrics.getAverageSyllablesPerWord();

        // Đảm bảo không chia cho 0
        if (totalSentences == 0 || totalWords == 0) {
            setDefaultValues(metrics);
            return;
        }

        // 1. Flesch Reading Ease
        double fleschReadingEase = 206.835 - (1.015 * averageWordsPerSentence) - (84.6 * averageSyllablesPerWord);
        metrics.setFleschReadingEase(roundToOneDecimal(fleschReadingEase));

        // 2. Flesch-Kincaid Grade Level
        double fleschKincaidGrade = (0.39 * averageWordsPerSentence) + (11.8 * averageSyllablesPerWord) - 15.59;
        metrics.setFleschKincaidGradeLevel(roundToOneDecimal(fleschKincaidGrade));

        // 3. Gunning Fog Index
        double gunningFog = 0.4 * (averageWordsPerSentence + (100.0 * complexWords / totalWords));
        metrics.setGunningFogIndex(roundToOneDecimal(gunningFog));

        // 4. Coleman-Liau Index
        double l = (double) countLetters(metrics.getTotalWords()) / totalWords * 100; // Số ký tự trung bình trên 100 từ
        double s = (double) totalSentences / totalWords * 100; // Số câu trung bình trên 100 từ
        double colemanLiau = 0.0588 * l - 0.296 * s - 15.8;
        metrics.setColemanLiauIndex(roundToOneDecimal(colemanLiau));

        // 5. SMOG Index
        double smog = 1.043 * Math.sqrt(complexWords * (30.0 / totalSentences)) + 3.1291;
        metrics.setSmogIndex(roundToOneDecimal(smog));

        // 6. Automated Readability Index
        int characters = countCharacters(totalWords);
        double ari = 4.71 * ((double) characters / totalWords) + 0.5 * averageWordsPerSentence - 21.43;
        metrics.setAutomatedReadabilityIndex(roundToOneDecimal(ari));
    }

    private void setDefaultValues(ReadabilityMetrics metrics) {
        // Đặt giá trị mặc định khi không đủ dữ liệu để tính toán
        metrics.setFleschReadingEase(0);
        metrics.setFleschKincaidGradeLevel(0);
        metrics.setGunningFogIndex(0);
        metrics.setColemanLiauIndex(0);
        metrics.setSmogIndex(0);
        metrics.setAutomatedReadabilityIndex(0);
        metrics.setReadabilityScore(0);
        metrics.setReadabilityDescription("Unable to calculate - not enough text");
    }

    private int countLetters(int totalWords) {
        // Ước tính số ký tự dựa trên số từ (trung bình 5 ký tự/từ cho tiếng Anh)
        return totalWords * 5;
    }

    private int countCharacters(int totalWords) {
        // Ước tính số ký tự dựa trên số từ (trung bình 5 ký tự/từ cho tiếng Anh)
        return totalWords * 5;
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private void setReadabilityScoreAndDescription(ReadabilityMetrics metrics) {
        // Tính điểm đọc từ 1-10 dựa trên thang điểm Flesch Reading Ease
        double fleschScore = metrics.getFleschReadingEase();

        // Chuyển đổi thang đo 0-100 Flesch Reading Ease thành thang đo 1-10
        int readabilityScore;
        String description;

        if (fleschScore >= 90) {
            readabilityScore = 10;
            description = "Very easy to read. Easily understood by an average 11-year-old student.";
        } else if (fleschScore >= 80) {
            readabilityScore = 9;
            description = "Easy to read. Conversational English for consumers.";
        } else if (fleschScore >= 70) {
            readabilityScore = 8;
            description = "Fairly easy to read.";
        } else if (fleschScore >= 60) {
            readabilityScore = 7;
            description = "Plain English. Easily understood by 13- to 15-year-old students.";
        } else if (fleschScore >= 50) {
            readabilityScore = 6;
            description = "Fairly difficult to read.";
        } else if (fleschScore >= 40) {
            readabilityScore = 5;
            description = "Difficult to read.";
        } else if (fleschScore >= 30) {
            readabilityScore = 4;
            description = "Very difficult to read. Best understood by university graduates.";
        } else if (fleschScore >= 20) {
            readabilityScore = 3;
            description = "Extremely difficult to read. Best understood by university graduates.";
        } else if (fleschScore >= 10) {
            readabilityScore = 2;
            description = "Very confusing. Advanced academic text.";
        } else {
            readabilityScore = 1;
            description = "Extremely confusing. Advanced academic or professional text.";
        }

        metrics.setReadabilityScore(readabilityScore);
        metrics.setReadabilityDescription(description);
    }
}