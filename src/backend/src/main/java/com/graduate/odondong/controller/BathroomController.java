package com.graduate.odondong.controller;

import static com.graduate.odondong.util.BaseResponseStatus.SUCCESS;
import static com.graduate.odondong.util.ErrorLogWriter.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.graduate.odondong.domain.UpdatedBathroom;
import com.graduate.odondong.dto.*;
import com.graduate.odondong.util.BaseResponseStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.service.AwsS3Service;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BathroomController {
	private final BathroomService bathroomService;
	private final AwsS3Service awsS3Service;

	/**
	 * 현재 위치 주변 모든 화장실 정보 조회 api
	 * [GET] /api/bathroom/address
	 **/
	@ResponseBody
	@GetMapping("/api/bathroom/address")
	public BaseResponse<CoordinateInfoDto> findAllBathroomsFromCoordinate(HttpServletRequest request,
																		  @RequestParam("longitude") Double x, @RequestParam("latitude") Double y) {
		try {
			return bathroomService.getAddressByCoordinate(x, y);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * 현재 위치 주변 지정 거리 반경 화장실 정보 조회 api
	 * [GET] /api/bathroom/list
	 **/
	@ResponseBody
	@GetMapping("/api/bathroom/list")
	public BaseResponse<List<BathroomResponseDto>> findBathroomsAroundDistanceFromCoordinate(HttpServletRequest request,
																							 @RequestParam("longitude") Double x, @RequestParam("latitude") Double y,
																							 @RequestParam("distance") Double distance) {
		try {
			return bathroomService.get1kmByLongitudeLatitude(y, x, distance);
		} catch (BaseException e) {
			e.printStackTrace();
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * 화장실 등록 요청 api
	 * [POST] /api/bathroom/add
	 **/
	@ResponseBody
	@PostMapping("/api/bathroom/add")
	public BaseResponse<String> addBathroomRequest(HttpServletRequest request,
												   @RequestPart BathroomRequestDto bathroomRequestDto,
												   @RequestPart(value = "bathroomImg", required = false) MultipartFile multipartFile) {
		try {
			String bathroomImgUrl = bathroomRequestDto.getImageUrl();

			if (multipartFile != null) {
				String dirName = "info/";
				bathroomImgUrl = awsS3Service.upload(multipartFile, dirName);
			}
			return new BaseResponse<String>(
					bathroomService.RegisterBathroomRequest(bathroomRequestDto, bathroomImgUrl));
		} catch (BaseException e) {
			e.printStackTrace();
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * 화장실 정보 수정 요청 api
	 * [POST] /api/bathroom/edit
	 **/
	@ResponseBody
	@PostMapping("/api/bathroom/edit")
	public BaseResponse<BaseResponseStatus> modifyBathroomRequest(HttpServletRequest request, @RequestBody BathroomUpdateRequestDto bathroomUpdateRequestDto) {
		try {
			bathroomService.registerUpdatedBathroomInfo(bathroomUpdateRequestDto);
			return new BaseResponse<>(SUCCESS);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * 모든 화장실 정보 조회 api
	 * [GET] /admin/bathroom/all
	 **/
	@ResponseBody
	@GetMapping("/admin/bathroom/all")
	public List<BathroomResponseInterface> findAllBathrooms() {
		return bathroomService.bathroomList();

	}

	/**
	 * 등록된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/registered
	 **/
	@ResponseBody
	@GetMapping("/admin/bathroom/registered")
	public List<Bathroom> findAddedBathrooms() {
		return bathroomService.RegisterBathroomList();
	}

	/**
	 * 등록 요청된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/not-registered
	 **/
	@GetMapping("/admin/bathroom/not-registered")
	public String findNotAddedBathrooms(Model model) {
		List<Bathroom> bathrooms = bathroomService.NotRegisterBathroomList();
		model.addAttribute("bathrooms", bathrooms);
		return "register";
	}

	/**
	 * 수정 요청된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/not-edited
	 **/
	@GetMapping("/admin/bathroom/not-edited")
	public String findNotModifiedBathrooms(Model model) {
		List<UpdatedBathroom> updatedBathrooms = bathroomService.notEditBathroomList();
		model.addAttribute("updatedBathrooms", updatedBathrooms);
		return "edit";
	}

	/**
	 * 화장실 등록 요청 승인 api
	 * [POST] /admin/bathroom/register
	 **/
	@PostMapping("/admin/bathroom/register")
	public String acceptBathroomAdd(@RequestParam("id") Long id) {
		bathroomService.UpdateBathroom(id);
		return "redirect:/not-register-bathroom";
	}

	/**
	 * 화장실 수정 요청 승인 api
	 * [POST] /admin/bathroom/edit
	 **/
	@PostMapping("/admin/bathroom/edit")
	public String acceptBathroomModify(HttpServletRequest request, @RequestParam("id") Long id) {
			bathroomService.registerUpdatedBathroom(id);
			return "redirect:/admin/bathroom/not-edited";
	}

	/**
	 * 화장실 삭제 api
	 * [DELETE] /admin/bathroom/edit
	 **/
	@DeleteMapping("/admin/bathroom/delete")
	@ResponseBody
	public String removeNotAddedBathroom(@RequestParam("id") Long id) {
		bathroomService.DeleteBathroom(id);
		return "Delete";
	}

	/**
	 * 화장실 수정 반려 api
	 * [DELETE] /admin/bathroom/edit
	 **/
	@DeleteMapping("/admin/bathroom/delete-updated")
	@ResponseBody
	public String removeNotModifiedBathroom(@RequestParam("id") Long id) {
		bathroomService.deleteUpdatedBathroom(id);
		return "deleteUpdated";
	}

}
