package com.graduate.odondong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graduate.odondong.config.Login;
import com.graduate.odondong.domain.Member;
import com.graduate.odondong.dto.MemberProfileResponseDto;
import com.graduate.odondong.dto.MemberResponseDto;
import com.graduate.odondong.service.MemberService;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@ResponseBody
	@GetMapping(value = "/profile")
	public BaseResponse<MemberProfileResponseDto> getMemberProfile(@ApiIgnore @Login Member member){
		return memberService.findMemberProfile(member);
	}

	@ResponseBody
	@GetMapping(value = "/register")
	public BaseResponse<MemberResponseDto> registerMember(){
		return new BaseResponse<>(new MemberResponseDto(memberService.registerMember()));
	}

}
