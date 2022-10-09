package com.graduate.odondong.service;

import com.graduate.odondong.domain.Inquery;
import com.graduate.odondong.repository.InqueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InqueryService {

    private final InqueryRepository inqueryRepository;

    public void createInquery(String contents) {
        Inquery inquery = Inquery.builder().content(contents).build();
        inqueryRepository.save(inquery);
    }
}
