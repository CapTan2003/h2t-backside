package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.homepage.QuoteDTO;

import java.util.List;

public interface QuoteService {

    List<QuoteDTO> getRandomQuotes(Integer limit);
}
