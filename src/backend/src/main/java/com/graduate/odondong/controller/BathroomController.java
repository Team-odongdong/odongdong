package com.graduate.odondong.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.BathroomResponseDto;
import com.graduate.odondong.dto.BathroomResponseInterface;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.AwsS3Service;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.graduate.odondong.util.ErrorLogWriter.writeExceptionWithRequest;

@Controller
@RequiredArgsConstructor
public class BathroomController {
	private final BathroomService bathroomService;
	private final AwsS3Service awsS3Service;

	@ResponseBody
	@GetMapping("/admin/bathroom/all")
	public List<BathroomResponseInterface> AllBathroomList() {
		return bathroomService.bathroomList();

	}

	@ResponseBody
	@GetMapping("/admin/bathroom/registered")
	public List<Bathroom> RegisterBathroomList() {
		return bathroomService.RegisterBathroomList();
	}

	@ResponseBody
	@PostMapping("/api/bathroom/add")
	public BaseResponse<String> RegisterBathroomRequest(HttpServletRequest request,
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

	@GetMapping("/admin/bathroom/not-registered")
	public String NotRegisterBathroomList(Model model) {
		List<Bathroom> bathrooms = bathroomService.NotRegisterBathroomList();
		model.addAttribute("bathrooms", bathrooms);
		return "register";
	}

	@PostMapping("/admin/bathroom/register")
	public String RegisterBathroom(@RequestParam("id") Long id) {
		bathroomService.UpdateBathroom(id);
		return "redirect:/not-register-bathroom";
	}

	@DeleteMapping("/admin/bathroom/delete")
	@ResponseBody
	public String DeleteBathroom(@RequestParam("id") Long id) {
		bathroomService.DeleteBathroom(id);
		return "Delete";
	}

	@ResponseBody
	@GetMapping("/api/bathroom/address")
	public BaseResponse<CoordinateInfoDto> AllAddressInfo(HttpServletRequest request,
		@RequestParam("longitude") Double x, @RequestParam("latitude") Double y) {
		try {
			return bathroomService.getAddressByCoordinate(x, y);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

	@ResponseBody
	@GetMapping("/api/bathroom/list")
	public BaseResponse<List<BathroomResponseDto>> get1kmBathroom(HttpServletRequest request,
		@RequestParam("longitude") Double x, @RequestParam("latitude") Double y,
		@RequestParam("distance") Double distance) {
		try {
			return bathroomService.get1kmByLongitudeLatitude(y, x, distance);
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
	}

}
