package com.graduate.odondong.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.Inquery;
import com.graduate.odondong.repository.InqueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InqueryService {

    private final InqueryRepository inqueryRepository;
    private final UserService userService;

    public void addInquery(String contents) {
        inqueryRepository.save(new Inquery(contents, userService.getUserEmail()));
    }
}
