package RamJongSuck.odongdong.DataInserter.type;

public enum FileType {
	JSON, XML, XLSX;

	public static FileType getFileType(String extension) {
		if(extension.equals("json")) {
			return FileType.JSON;
		}else if(extension.equals("xml")) {
			return FileType.XML;
		}else if(extension.equals("xlsx")) {
			return FileType.XLSX;
		}
		throw new RuntimeException("확장자가 잘못되었습니다.");
	}
}
