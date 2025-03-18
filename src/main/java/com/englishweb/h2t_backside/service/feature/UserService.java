package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import org.springframework.data.domain.Page;

public interface UserService extends BaseService<UserDTO>{
    Page<UserDTO> searchWithFilters(int page, int size, String sortFields, UserFilterDTO filter);
}
