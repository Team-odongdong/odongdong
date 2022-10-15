package com.graduate.odondong.service.BathroomService;


import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.AddressInfoDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.util.ChangeByGeocoder;
import lombok.RequiredArgsConstructor;
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
	private final ChangeByGeocoder changeByGeocoder;

	public void BulkInsert() {

		List<Bathroom> bathroomList = new ArrayList<>();

		try {
			String fileName = "12_04_01_E_공중화장실정보.xlsx";
//            String fileName = "test.xlsx";
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

			String title = null;
			String address = null;
			Boolean unisex = null;

			for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
				if (columnIndex != 2 && columnIndex != 3 && columnIndex != 4) {
					continue;
				}
				XSSFCell cell = row.getCell(columnIndex);
				String value = "";
				if (cell == null) {
					continue;
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
				if (columnIndex == 2) {
					title = value;
				} else if (columnIndex == 3 && value.equals("없음")) {
					address = value;
				} else if (columnIndex == 4 && address == null) {
					address = value;
				} else if (columnIndex == 5) {
					unisex = !value.equals("N");
				}
			}
			AddressInfoDto addressInfoDto;
			try {
				addressInfoDto = changeByGeocoder.getCoordinateByAddress(address);
			} catch (Exception e) {
				addressInfoDto = AddressInfoDto.builder().address(address).address_detail("").longitude(0.0).latitude(0.0).build();
			}

			Bathroom bathroom = getBathroom(title, addressInfoDto, unisex);
			bathroomList.add(bathroom);

		}
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

}
