package com.graduate.odondong.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.graduate.odondong.domain.Member;
import com.graduate.odondong.dto.MemberProfileResponseDto;
import com.graduate.odondong.repository.MemberRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member registerMember() {
		return memberRepository.save(Member.createMember());
	}

	public Member registerOrLoginMemberUUID(UUID uuid) {
		return memberRepository.findByUuid(uuid)
			.orElseGet(() -> memberRepository.save(Member.createMember()));
	}

	public Member findMemberByUUID(UUID uuid) {
		return memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.MEMBER_HEADER_INVAILD_UUID));
	}

	public BaseResponse<MemberProfileResponseDto> findMemberProfile(Member member) {
		return new BaseResponse<>(new MemberProfileResponseDto(member));
	}
}
