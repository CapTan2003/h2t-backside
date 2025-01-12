package com.englishweb.h2t_backside.service;

import com.englishweb.h2t_backside.dto.UserDTO;

public interface UserService extends BaseService<UserDTO>{
    boolean verifyOtp(String email, String inputOtp);
}
