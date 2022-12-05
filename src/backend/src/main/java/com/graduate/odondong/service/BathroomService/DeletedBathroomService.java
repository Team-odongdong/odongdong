package com.graduate.odondong.service.BathroomService;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.DeletedBathroom;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.DeletedBathroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeletedBathroomService {

	private final BathroomRepository bathroomRepository;
	private final DeletedBathroomRepository deletedBathroomRepository;

	public void addDeletedBathroom(Long bathroomId) {
		Bathroom bathroom = bathroomRepository.findById(bathroomId).orElseThrow();
		DeletedBathroom deletedBathroom = DeletedBathroom.builder()
				.title(bathroom.getTitle())
				.isUnisex(bathroom.getIsUnisex())
				.latitude(bathroom.getLatitude())
				.longitude(bathroom.getLongitude())
				.register(bathroom.getRegister())
				.isLocked(bathroom.getIsLocked())
				.imageUrl(bathroom.getImageUrl())
				.addressDetail(bathroom.getAddressDetail())
				.address(bathroom.getAddress())
				.created_at(bathroom.getCreated_at())
				.build();
		deletedBathroomRepository.save(deletedBathroom);
	}
}
