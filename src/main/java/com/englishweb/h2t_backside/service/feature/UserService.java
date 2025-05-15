package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import com.englishweb.h2t_backside.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BaseService<UserDTO>{
    Page<UserDTO> searchWithFilters(int page, int size, String sortFields, UserFilterDTO filter);
    List<UserDTO> findByIdInAndStatus(List<Long> ids, Boolean status);

}
