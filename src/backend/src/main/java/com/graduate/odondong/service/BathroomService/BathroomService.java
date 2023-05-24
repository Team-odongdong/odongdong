package com.graduate.odondong.service.BathroomService;

import static com.graduate.odondong.util.BaseResponseStatus.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.BathroomResponseDto;
import com.graduate.odondong.dto.BathroomResponseInterface;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.dto.Point;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.service.UserLocationCalculator;
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

	public BaseResponse<CoordinateInfoDto> findAllBathroomsFromCoordinate(Double x, Double y) throws BaseException {
		try {
			CoordinateInfoDto address = changeByGeocoderKakao.getAddressByCoordinate(x, y);
			if (address.getAddress_name() == null) {
				address = changeByGeocoderNaver.getAddressByCoordinate(x, y);
			}
			return new BaseResponse<>(address);
		} catch (Exception e) {
			throw new BaseException(GEOCODING_ERROR);
		}
	}

	public BaseResponse<List<BathroomResponseDto.BathroomInfo>> findBathroomsAroundDistanceFromCoordinate(Double x,
		Double y, Double distance) throws BaseException {
		try {
			UserLocationCalculator locationService = new UserLocationCalculator(new Point(x, y), distance);

			List<BathroomResponseInterface> bathroomResponseDto = bathroomRepository.findBathroomResponseDto(
				locationService.getLatitudeMinus(),
				locationService.getLatitudePlus(), locationService.getLongitudeMinus(),
				locationService.getLongitudePlus());

			List<BathroomResponseDto.BathroomInfo> bathroomInfoList = new BathroomResponseDto(
				bathroomResponseDto).getBathroomInfoList();

			return new BaseResponse<>(locationService.calculateDistanceAndRemove(bathroomInfoList));
		} catch (Exception e) {
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
		Bathroom bathroom = bathroomRepository.findById(bathroomId)
			.orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
		bathroom.setRegister(true);
		bathroomRepository.save(bathroom);
	}

	public void removeNotAddedBathroom(Long bathroomId) {
		bathroomRepository.deleteById(bathroomId);
	}
}
