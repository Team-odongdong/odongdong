package com.graduate.odondong.service.BathroomService;


import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.AddressInfoDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Component
public class BulkInsertPublicBathroom {

	private final BathroomRepository bathroomRepository;
	private final ChangeByGeocoderKakao changeByGeocoder;

	public void BulkInsert(String fileName) {

		List<Bathroom> bathroomList = new ArrayList<>();

		try {
			XSSFWorkbook workbook = getXssfSheets(fileName);
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int rowindex = 1; rowindex < sheet.getPhysicalNumberOfRows(); rowindex++) {
				addElementBathroomList(bathroomList, rowindex, sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		bathroomRepository.saveAll(bathroomList);
	}

	private void addElementBathroomList(List<Bathroom> bathroomList, int rowindex, XSSFSheet sheet) {
		int columnIndex = 0;
		XSSFRow row = sheet.getRow(rowindex);
		if (row != null) {
			int cells = row.getPhysicalNumberOfCells();
			CellDto cellDto = getCellDto(row, cells);
			AddressInfoDto addressInfoDto;
			try {
				addressInfoDto = changeByGeocoder.getCoordinateByAddress(cellDto.getAddress());
			} catch (Exception e) {
				addressInfoDto = AddressInfoDto.builder().address(cellDto.getAddress()).address_detail("").longitude(0.0).latitude(0.0).build();
			}
			Bathroom bathroom = getBathroom(cellDto.getTitle(), addressInfoDto, cellDto.getUnisex());
			bathroomList.add(bathroom);
		}
	}
	
	private CellDto getCellDto(XSSFRow row, int cells) {
		int columnIndex;
		CellDto cellDto = new CellDto();
		for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
			if (columnIndex != 2 && columnIndex != 3 && columnIndex != 4 && columnIndex != 5) {
				continue;
			}
			
			String value = "";
			value = getColumnValue(columnIndex, row, value);
			if(value == null) continue;
			
			
			if (columnIndex == 2) {
				cellDto.setTitle(value);
			} else if (columnIndex == 3 && value.equals("없음")) {
				cellDto.setAddress(value);
			} else if (columnIndex == 4 && cellDto.getAddress() == null) {
				cellDto.setAddress(value);
			} else if (columnIndex == 5) {
				cellDto.setUnisex(!value.equals("N"));
			}
		}
		return cellDto;
	}
	
	private static String getColumnValue(int columnIndex, XSSFRow row, String value) {
		XSSFCell cell = row.getCell(columnIndex);
		if (cell == null) {
			value = null;
		} else {
			switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getNumericCellValue() + "";
					break;
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue() + "";
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = cell.getBooleanCellValue() + "";
					break;
				case XSSFCell.CELL_TYPE_ERROR:
					value = cell.getErrorCellValue() + "";
					break;
			}
		}
		return value;
	}
	
	private static XSSFWorkbook getXssfSheets(String fileName) throws IOException {
		Path relativePath = Paths.get("");
		String path = relativePath.toAbsolutePath().toString() + "\\src\\main\\resources\\BathroomFile\\" + fileName;
		FileInputStream file = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
	}

	private static Bathroom getBathroom(String title, AddressInfoDto addressInfoDto, Boolean unisex) {
		Bathroom bathroom = Bathroom.builder()
				.title(title)
				.latitude(addressInfoDto.getLatitude())
				.longitude(addressInfoDto.getLongitude())
				.address(addressInfoDto.getAddress())
				.addressDetail(addressInfoDto.getAddress_detail())
				.isLocked("N")
				.register(unisex)
				.build();
		return bathroom;
	}

	@Setter
	@Getter
	private class CellDto {
		String title;
		String address;
		Boolean unisex;
		
		public CellDto() {
		}
		
		public CellDto(String title, String address, Boolean unisex) {
			this.title = title;
			this.address = address;
			this.unisex = unisex;
		}
	}
	
}
