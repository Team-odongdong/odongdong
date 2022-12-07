package RamJongSuck.odongdong.DataInserter.type;

public enum DatabaseType {
	VARCHAR, FLOAT, DATETIME, BOOLEAN;

	public String getDatabaseFormat(String data) {
		if(data == null || data.isEmpty()) {
			return "\"\"";
		}

		if(this.equals(DatabaseType.VARCHAR)) {
			return "\"" + data + "\"";
		} else if (this.equals(DatabaseType.FLOAT)) {
			return Double.valueOf(data).toString();
		} else if (this.equals(DatabaseType.BOOLEAN)) {
			if(data.equals("Y") || data.equals("1") || data.equals("YES") || data.equals("공중화장실")) {
				return "true";
			}
			return "false";
		}
		throw new RuntimeException("등록되지 않은 데이터베이스 타입입니다.");
	}
}
