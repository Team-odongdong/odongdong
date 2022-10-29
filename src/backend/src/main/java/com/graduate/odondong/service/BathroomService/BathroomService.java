package com.graduate.odondong.service.BathroomService;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.dto.*;
import com.graduate.odondong.repository.BathroomRepository;

import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderNaver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.graduate.odondong.util.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BathroomService {
    private final BathroomRepository bathroomRepository;
    private final RatingRepository ratingRepository;
    private final ChangeByGeocoderKakao changeByGeocoderKakao;
    private final ChangeByGeocoderNaver changeByGeocoderNaver;
    private final DeletedBathroomService deletedBathroomService;

    public List<BathroomResponseInterface> bathroomList() {
        return bathroomRepository.findAllBathroomResponseDto();
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

    public void DeleteBathroom(Long id) {
        Bathroom bathroom = bathroomRepository.findById(id).orElseThrow();
        deletedBathroomService.AddDeletedBathroom(bathroom);
        bathroomRepository.deleteById(id);
    }

    public String RegisterBathroomRequest(BathroomRequestDto bathroomRequestDto) throws BaseException{
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
                    .isUnisex(bathroomRequestDto.getIsUnisex())
                    .build();
            bathroomRepository.save(bathroom);
            Rating rating = Rating.builder()
                    .score(bathroomRequestDto.getRate())
                    .bathroom(bathroomRepository.findById(bathroom.getId()).get())
                    .build();
            ratingRepository.save(rating);
            return "SUCCESS";
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public BaseResponse<List<BathroomResponseDto>> get1kmByLongitudeLatitude(Double x, Double y) throws BaseException {
        try {
            double lati_minus = x - 0.0091;
            double lati_plus = x + 0.0091;
            double long_minus = y - 0.0113;
            double long_plus = y + 0.0113;
            List<BathroomResponseInterface> bathroomResponseDto = bathroomRepository.findBathroomResponseDto(lati_minus,
                lati_plus, long_minus, long_plus);
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
                    .isOpened("Y")
                    .build()
            ).toList();
            return new BaseResponse<>(bathroomResponseDtos);
        }catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public CoordinateInfoDto getAddressByCoordinate(Double x, Double y) {
        CoordinateInfoDto address = changeByGeocoderKakao.getAddressByCoordinate(x, y);
        if(address.getAddress_name() == null) {
            address = changeByGeocoderNaver.getAddressByCoordinate(x, y);
        }
        return address;
    }
}
