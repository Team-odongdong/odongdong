package com.graduate.odondong.service.BathroomService;

import static com.graduate.odondong.util.BaseResponseStatus.*;

import java.util.List;
import java.util.stream.Collectors;

import com.graduate.odondong.dto.*;
import com.graduate.odondong.util.operationTime.OperationTimeValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderNaver;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BathroomService {
    private final BathroomRepository bathroomRepository;
    private final RatingRepository ratingRepository;
    private final ChangeByGeocoderKakao changeByGeocoderKakao;
    private final ChangeByGeocoderNaver changeByGeocoderNaver;
    private final OperationTimeValidation operationTimeValidation;

    public BaseResponse<CoordinateInfoDto> findAllBathroomsFromCoordinate(Double x, Double y) throws BaseException{
        try {
            CoordinateInfoDto address = changeByGeocoderKakao.getAddressByCoordinate(x, y);
            if(address.getAddress_name() == null) {
                address = changeByGeocoderNaver.getAddressByCoordinate(x, y);
            }
            return new BaseResponse<>(address);
        }catch (Exception e){
            throw new BaseException(GEOCODING_ERROR);
        }
    }

    public BaseResponse<List<BathroomResponseDto>> findBathroomsAroundDistanceFromCoordinate(Double x, Double y, Double distance) throws BaseException {
        try {
            LocationDto locationDto = LocationDto.builder()
                    .x(x)
                    .y(y)
                    .distance(distance)
                    .build();
            List<BathroomResponseInterface> bathroomResponseDto = bathroomRepository.findBathroomResponseDto(locationDto.getLati_minus(),
                    locationDto.getLati_plus(), locationDto.getLong_minus(), locationDto.getLong_plus());
            // bathroomResponseDto.stream().map()
            List<BathroomResponseDto> bathroomResponseDtos = bathroomResponseDto.stream().map(
                    (data) -> BathroomResponseDto.builder()
                            .bathroomId(data.getBathroomId())
                            .latitude(data.getLatitude())
                            .longitude(data.getLongitude())
                            .operationTime(data.getOperationTime())
                            .rate(data.getRate())
                            .address(data.getAddress())
                            .addressDetail(data.getAddressDetail())
                            .title(data.getTitle())
                            .imageUrl(data.getImageUrl())
                            .isLocked(data.getIsLocked())
                            .register(data.getRegister())
                            .isUnisex(data.getIsUnisex())
                            .isOpened(operationTimeValidation.checkBathroomOpen(data.getOperationTime()))
                            .build()
            ).collect(Collectors.toList());
            return new BaseResponse<>(bathroomResponseDtos);
        }catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<BathroomResponseInterface> findAllBathrooms() {
        return bathroomRepository.findAllBathroomResponseDto();
    }

    public List<Bathroom> findAddedBathrooms() {
        return bathroomRepository.findBathroomsByRegisterIsTrue();
    }

    public List<Bathroom> findNotAddedBathrooms() {
        return bathroomRepository.findBathroomsByRegisterIsFalse();
    }

    public BaseResponse<String> addBathroom(BathroomRequestDto bathroomRequestDto, String bathroomImgUrl) {
        Bathroom bathroom = bathroomRequestDto.toBathroom(bathroomImgUrl);
        bathroomRepository.save(bathroom);

        Rating rating = bathroomRequestDto.toRating(bathroom);
        ratingRepository.save(rating);
        return new BaseResponse<>(SUCCESS);
    }

    public void saveAddedBathroom(Long bathroomId) throws BaseException {
        Bathroom bathroom = bathroomRepository.findById(bathroomId).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        bathroom.setRegister(true);
        bathroomRepository.save(bathroom);
    }

    public void removeNotAddedBathroom(Long bathroomId) {
        bathroomRepository.deleteById(bathroomId);
    }
}
