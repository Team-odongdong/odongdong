package com.graduate.odondong.service.BathroomService;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.repository.BathroomRepository;

import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.dto.CoordinateInfoDto;
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
    private final ChangeByGeocoderKakao changeByGeocoderKakao;
    private final ChangeByGeocoderNaver changeByGeocoderNaver;
    private final DeletedBathroomService deletedBathroomService;

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

    public void DeleteBathroom(Long id) {
        Bathroom bathroom = bathroomRepository.findById(id).orElseThrow();
        deletedBathroomService.AddDeletedBathroom(bathroom);
        bathroomRepository.deleteById(id);
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


    public List<Bathroom> get1kmByLongitudeLatitude(Double x, Double y) {
        double lati_minus = x - 0.0091;
        double lati_plus = x + 0.0091;
        double long_minus = y - 0.0113;
        double long_plus = y + 0.0113;
        return bathroomRepository.findByRegisterIsTrueAndLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(lati_minus, lati_plus, long_minus, long_plus);
    }

    public CoordinateInfoDto getAddressByCoordinate(Double x, Double y) {
        CoordinateInfoDto address = changeByGeocoderKakao.getAddressByCoordinate(x, y);
        if(address.getAddress_name() == null) {
            address = changeByGeocoderNaver.getAddressByCoordinate(x, y);
        }
        return address;
    }
}
