package com.graduate.odondong.service.BathroomService;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.UpdatedBathroom;
import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.BathroomUpdateRequestDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.UpdatedBathroomRepository;
import com.graduate.odondong.util.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.graduate.odondong.util.BaseResponseStatus.DATABASE_ERROR;
import static com.graduate.odondong.util.BaseResponseStatus.NOT_FOUND_BATHROOM;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdatedBathroomService {

    private final BathroomRepository bathroomRepository;
    private final UpdatedBathroomRepository updatedBathroomRepository;

    public List<UpdatedBathroom> findNotModifiedBathrooms() {
        return updatedBathroomRepository.findUpdatedBathroomsByRegisterIsFalse();
    }

    public void addUpdatedBathroom(BathroomUpdateRequestDto bathroomUpdateRequestDto, User user) throws BaseException {
        Bathroom bathroom = bathroomRepository.findById(bathroomUpdateRequestDto.getBathroomId()).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        UpdatedBathroom updatedBathroom = bathroomUpdateRequestDto.toUpdatedBathroom(bathroom, user);
        updatedBathroomRepository.save(updatedBathroom);
    }

    public void saveModifiedBathroom(Long updatedBathroomId) throws BaseException{
        UpdatedBathroom updatedBathroom = updatedBathroomRepository.findById(updatedBathroomId).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        updatedBathroom.setRegister(true);
        updatedBathroomRepository.save(updatedBathroom);

        Bathroom bathroom = bathroomRepository.findById(updatedBathroom.getBathroom().getId()).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        bathroom.update(updatedBathroom);
        bathroomRepository.save(bathroom);
    }

    public void removeNotModifiedBathroom(Long updatedBathroomId) throws BaseException{
        UpdatedBathroom updatedBathroom = updatedBathroomRepository.findById(updatedBathroomId).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        updatedBathroomRepository.deleteById(updatedBathroomId);
    }
}
