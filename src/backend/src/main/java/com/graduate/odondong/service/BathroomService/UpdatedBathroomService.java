package com.graduate.odondong.service.BathroomService;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.UpdatedBathroom;
import com.graduate.odondong.dto.BathroomUpdateRequestDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.UpdatedBathroomRepository;
import com.graduate.odondong.util.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.graduate.odondong.util.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdatedBathroomService {

    private final BathroomRepository bathroomRepository;
    private final UpdatedBathroomRepository updatedBathroomRepository;

    public List<UpdatedBathroom> findNotModifiedBathrooms() {
        return updatedBathroomRepository.findUpdatedBathroomsByRegisterIsFalse();
    }

    public void addUpdatedBathroom(BathroomUpdateRequestDto bathroomUpdateRequestDto) throws BaseException {
        try {
            Bathroom bathroom = bathroomRepository.findById(bathroomUpdateRequestDto.getBathroomId()).get();
            UpdatedBathroom updatedBathroom = bathroomUpdateRequestDto.toUpdatedBathroom(bathroom);
            updatedBathroomRepository.save(updatedBathroom);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void saveModifiedBathroom(Long updatedBathroomId) {
        UpdatedBathroom updatedBathroom = updatedBathroomRepository.findById(updatedBathroomId).orElseThrow(() -> new IllegalArgumentException("요청된 화장실 정보가 없습니다"));
        updatedBathroom.setRegister(true);
        updatedBathroomRepository.save(updatedBathroom);

        Bathroom bathroom = bathroomRepository.findById(updatedBathroom.getBathroom().getId()).orElseThrow(() -> new IllegalArgumentException("요청된 화장실 정보가 없습니다"));
        bathroom.update(updatedBathroom);
        bathroomRepository.save(bathroom);
        updatedBathroomRepository.deleteById(updatedBathroomId);
    }

    public void removeNotModifiedBathroom(Long updatedBathroomId) {
        UpdatedBathroom updatedBathroom = updatedBathroomRepository.findById(updatedBathroomId).orElseThrow();
        updatedBathroomRepository.deleteById(updatedBathroomId);
    }
}
