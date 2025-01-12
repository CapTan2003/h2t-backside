package com.englishweb.h2t_backside.service;

public interface BaseService<DTO> {

    DTO findById(Long id);

    DTO create(DTO dto);

    DTO update(DTO dto, Long id);

    void delete(Long id);

    boolean isExist(Long id);
}
