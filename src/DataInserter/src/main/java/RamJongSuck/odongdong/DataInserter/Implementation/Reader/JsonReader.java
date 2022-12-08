package RamJongSuck.odongdong.DataInserter.Implementation.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RamJongSuck.odongdong.DataInserter.Interface.DataReader;
import lombok.Getter;

@Getter
public class JsonReader implements DataReader {
	List<Map<String,String>> mapList = new ArrayList<>();

	public JsonReader(String filePath, Map<String, String> fileToDatabaseMap) {
		readJsonFile(filePath, fileToDatabaseMap);
	}

	private void readJsonFile(String filePath, Map<String, String> fileToDatabaseMap) {
		try {
			JSONArray jsonArray = new JSONArray(Files.readString(Paths.get(filePath)));
			Set<String> keySet = fileToDatabaseMap.keySet();

			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String,String> map = new HashMap<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				keySet.forEach((key) -> {
					try {
						map.put(fileToDatabaseMap.get(key), (String) jsonObject.get(key));
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
				});
				mapList.add(map);
			}
		} catch (IOException | JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
