package com.graduate.odondong.service;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.repository.BathroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BathroomService {

    private final BathroomRepository bathroomRepository;

    public List<Bathroom> bathroomList() {
        return bathroomRepository.findAll();
    }

    public List<Bathroom> RegisterBathroomList() {
        return bathroomRepository.findBathroomsByRegisterIsTrue();
    }

    public List<Bathroom> NotRegisterBathroomList() {
        return bathroomRepository.findBathroomsByRegisterIsFalse();
    }

    public void UpdateBathroom(Long id) {
        Bathroom bathroom = bathroomRepository.findById(id).get();
        bathroom.setRegister(true);
        bathroomRepository.save(bathroom);
    }

    public String RegisterBathroomRequest(BathroomRequestDto bathroomRequestDto) {
        try {
            Bathroom bathroom = Bathroom.builder()
                    .title(bathroomRequestDto.getTitle())
                    .latitude(bathroomRequestDto.getLatitude())
                    .longitude(bathroomRequestDto.getLongitude())
                    .isLocked(bathroomRequestDto.getIsLocked())
                    .address(bathroomRequestDto.getAddress())
                    .addressDetail(bathroomRequestDto.getAddressDetail())
                    .register(false)
                    .imageUrl(bathroomRequestDto.getImageUrl())
                    .build();
            bathroomRepository.save(bathroom);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAIL";
        }
    }
}