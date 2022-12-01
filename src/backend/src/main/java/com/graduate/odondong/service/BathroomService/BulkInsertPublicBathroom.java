package com.graduate.odondong.service.BathroomService;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.AddressInfoDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.type.BulkInsertEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Component
public class BulkInsertPublicBathroom {

	private final BathroomRepository bathroomRepository;
	private final ChangeByGeocoderKakao changeByGeocoder;

	/**
	 * @Param Map<String, String> : 바꾸려는 ColumnName -> DB ColumnName
	 * */
	public void BulkInsert(String fileName, Map<String, BulkInsertEnum> changeMap) {

		List<Bathroom> bathroomList = new ArrayList<>();

		try {
			XSSFWorkbook workbook = getXssfSheets(fileName);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Map<Integer, String> columnToDBName = getColumnToDBName(changeMap, sheet);

			for (int rowindex = 1; rowindex < sheet.getPhysicalNumberOfRows(); rowindex++) {
				addElementBathroomList(bathroomList, rowindex, sheet, columnToDBName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		bathroomRepository.saveAll(bathroomList);
	}

	private Map<Integer, String> getColumnToDBName(Map<String, BulkInsertEnum> changeMap, XSSFSheet sheet) {
		Map<Integer, String> columnToDBName = new HashMap<>();
		XSSFRow row = sheet.getRow(0);
		int cells = row.getPhysicalNumberOfCells();
		for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
			String value = "";
			value = getColumnValue(columnIndex, row, value);
			BulkInsertEnum bulkInsertEnum = changeMap.get(value);
			String dbColumnName = null;
			if (bulkInsertEnum != null)
				dbColumnName = bulkInsertEnum.name();
			if (dbColumnName != null) {
				columnToDBName.put(columnIndex, dbColumnName);
			}
		}
		return columnToDBName;
	}

	private void addElementBathroomList(List<Bathroom> bathroomList, int rowindex, XSSFSheet sheet,
		Map<Integer, String> columnToDBName) {
		XSSFRow row = sheet.getRow(rowindex);
		if (row != null) {
			int cells = row.getPhysicalNumberOfCells();
			CellDto cellDto = getCellDto(row, cells, columnToDBName);
			AddressInfoDto addressInfoDto;

			try {
				addressInfoDto = changeByGeocoder.getCoordinateByAddress(cellDto.getAddress());
			} catch (Exception e) {
				addressInfoDto = AddressInfoDto.builder()
					.address(cellDto.getAddress())
					.address_detail("")
					.longitude(0.0)
					.latitude(0.0)
					.build();
			}
			Bathroom bathroom = getBathroom(cellDto, addressInfoDto);
			bathroomList.add(bathroom);
		}
	}

	private CellDto getCellDto(XSSFRow row, int cells, Map<Integer, String> columnToDBName) {
		CellDto cellDto = new CellDto();
		for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
			String dbName = columnToDBName.get(columnIndex);
			if (dbName == null) {
				continue;
			}
			Stream<Field> stream = Arrays.stream(Bathroom.class.getDeclaredFields());
			if (stream.anyMatch(element -> element.getName().equals(dbName))) {
				String value = "";
				value = getColumnValue(columnIndex, row, value);
				if (value == null)
					continue;

				if (dbName.equals(BulkInsertEnum.isUnisex.name())) {
					cellDto.setUnisex(!value.equals("N"));
				} else if (dbName.equals(BulkInsertEnum.title.name())) {
					cellDto.setTitle(value);
				} else if (dbName.equals(BulkInsertEnum.address.name())) {
					cellDto.setAddress(value);
				} else if (dbName.equals(BulkInsertEnum.addressDetail.name()) && (cellDto.getAddress() == null
					|| cellDto.getAddress().equals(""))) {
					cellDto.setAddress(value);
				} else if (dbName.equals(BulkInsertEnum.operationTime.name())) {
					cellDto.setOperationTime(value);
				}
			} else {
				throw new RuntimeException("DB Name을 잘못입력하였습니다.");
			}
		}
		return cellDto;
	}

	private String getColumnValue(int columnIndex, XSSFRow row, String value) {
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

	private XSSFWorkbook getXssfSheets(String fileName) throws IOException {
		Path relativePath = Paths.get("");
		String path = relativePath.toAbsolutePath().toString() + "\\src\\main\\resources\\BathroomFile\\" + fileName;
		FileInputStream file = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
	}

	private static Bathroom getBathroom(CellDto cellDto, AddressInfoDto addressInfoDto) {
		Bathroom bathroom = Bathroom.builder()
			.title(cellDto.title)
			.latitude(addressInfoDto.getLatitude())
			.longitude(addressInfoDto.getLongitude())
			.address(addressInfoDto.getAddress())
			.addressDetail(addressInfoDto.getAddress_detail())
			.operationTime(cellDto.operationTime)
			.isUnisex(cellDto.unisex)
			.isLocked("N")
			.register(true)
			.build();
		return bathroom;
	}

	@Setter
	@Getter
	private class CellDto {
		String title;
		String address;
		Boolean unisex;
		String operationTime;

		public CellDto() {
		}

		public CellDto(String title, String address, Boolean unisex, String operationTime) {
			this.title = title;
			this.address = address;
			this.unisex = unisex;
			this.operationTime = operationTime;
		}
	}

}
