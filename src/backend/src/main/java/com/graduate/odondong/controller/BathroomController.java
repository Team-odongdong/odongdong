package com.graduate.odondong.controller;

import static com.graduate.odondong.util.BaseResponseStatus.*;
import static com.graduate.odondong.util.ErrorLogWriter.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.UpdatedBathroom;
import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.BathroomResponseDto;
import com.graduate.odondong.dto.BathroomResponseInterface;
import com.graduate.odondong.dto.BathroomUpdateRequestDto;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.AwsS3Service;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.BathroomService.DeletedBathroomService;
import com.graduate.odondong.service.BathroomService.UpdatedBathroomService;
import com.graduate.odondong.service.UserService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BathroomController {
	private final BathroomService bathroomService;
	private final DeletedBathroomService deletedBathroomService;
	private final UpdatedBathroomService updatedBathroomService;
	private final UserService userService;
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
			return bathroomService.findAllBathroomsFromCoordinate(x, y);
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
	public BaseResponse<List<BathroomResponseDto.BathroomInfo>> findBathroomsAroundDistanceFromCoordinate(HttpServletRequest request,
																							 @RequestParam("longitude") Double x, @RequestParam("latitude") Double y,
																							 @RequestParam("distance") Double distance) {
		try {
			return bathroomService.findBathroomsAroundDistanceFromCoordinate(y, x, distance);
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
		String bathroomImgUrl = bathroomRequestDto.getImageUrl();
		String dirName = "info/";

		if (multipartFile != null) {
			try {
				bathroomImgUrl = awsS3Service.upload(multipartFile, dirName);
			} catch (BaseException e) {
				writeExceptionWithRequest(e, request);
				return new BaseResponse<>(e.getStatus());
			}
		}
		return bathroomService.addBathroom(bathroomRequestDto, bathroomImgUrl);
	}

	/**
	 * 화장실 정보 수정 요청 api
	 * [POST] /api/bathroom/edit
	 **/
	@ResponseBody
	@PostMapping("/api/bathroom/edit")
	public BaseResponse<BaseResponseStatus> modifyBathroomRequest(HttpServletRequest request, @RequestBody BathroomUpdateRequestDto bathroomUpdateRequestDto) {
		try {
			User user = userService.getUserInfo();
			updatedBathroomService.addUpdatedBathroom(bathroomUpdateRequestDto,user);
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
		return bathroomService.findAllBathrooms();
	}

	/**
	 * 등록된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/registered
	 **/
	@ResponseBody
	@GetMapping("/admin/bathroom/registered")
	public List<Bathroom> findAddedBathrooms() {
		return bathroomService.findAddedBathrooms();
	}

	/**
	 * 등록 요청된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/not-registered
	 **/
	@GetMapping("/admin/bathroom/not-registered")
	public String findNotAddedBathrooms(Model model) {
		List<Bathroom> bathrooms = bathroomService.findNotAddedBathrooms();
		model.addAttribute("bathrooms", bathrooms);
		return "register";
	}

	/**
	 * 수정 요청된 화장실 정보 조회 api
	 * [GET] /admin/bathroom/not-edited
	 **/
	@GetMapping("/admin/bathroom/not-edited")
	public String findNotModifiedBathrooms(Model model) {
		List<UpdatedBathroom> updatedBathrooms = updatedBathroomService.findNotModifiedBathrooms();
		model.addAttribute("updatedBathrooms", updatedBathrooms);
		return "edit";
	}

	/**
	 * 화장실 등록 요청 승인 api
	 * [POST] /admin/bathroom/register
	 **/
	@PostMapping("/admin/bathroom/register")
	public String acceptBathroomAdd(HttpServletRequest request, @RequestParam("id") Long bathroomId) {
		try {
			bathroomService.saveAddedBathroom(bathroomId);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
		}
		return "redirect:/admin/bathroom/not-registered";
	}

	/**
	 * 화장실 수정 요청 승인 api
	 * [POST] /admin/bathroom/edit
	 **/
	@PostMapping("/admin/bathroom/edit")
	public String acceptBathroomModify(HttpServletRequest request, @RequestParam("id") Long updatedBathroomId) {
		try {
			updatedBathroomService.saveModifiedBathroom(updatedBathroomId);
		} catch (BaseException e){
			writeExceptionWithRequest(e, request);
		}
		return "redirect:/admin/bathroom/not-edited";
	}

	/**
	 * 화장실 삭제 api
	 * [DELETE] /admin/bathroom/edit
	 **/
	@DeleteMapping("/admin/bathroom/delete")
	@ResponseBody
	public String removeNotAddedBathroom(HttpServletRequest request, @RequestParam("id") Long bathroomId) {
		try {
			deletedBathroomService.addDeletedBathroom(bathroomId);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
		}
		bathroomService.removeNotAddedBathroom(bathroomId);
		return "Delete";
	}

	/**
	 * 화장실 수정 반려 api
	 * [DELETE] /admin/bathroom/edit
	 **/
	@DeleteMapping("/admin/bathroom/delete-updated")
	@ResponseBody
	public String removeNotModifiedBathroom(HttpServletRequest request, @RequestParam("id") Long updatedBathroomId) {
		try {
			updatedBathroomService.removeNotModifiedBathroom(updatedBathroomId);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
		}
		return "deleteUpdated";
	}

}
