package utilities;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {
	private static final String JSON_PATH = ConfigReader.getProperty("JSON_Path");

	public static Map<String, Object> getScenarioData(String scenarioName) {
		try {
			//Read JSON Content Loads the file and parses it into a tree-like structure using Jackson’s ObjectMapper.
			ObjectMapper objectMapper = new ObjectMapper();

			// Read the root as an array
			JsonNode rootNode = objectMapper.readTree(new File(JSON_PATH));

			// AAssumes the root is an array, and picks the first object in that array.From there, it accesses the "requests" node.
			JsonNode requests = rootNode.get(0).get("requests");

			// Loop through the "requests" array For each data entry, it checks if the scenario field matches the provided scenarioName.
			for (JsonNode request : requests) {
				JsonNode dataList = request.get("data");

				// Loop through the "data" array to find the matching scenario Collects all key-value pairs of that JSON object into a Map
				for (JsonNode data : dataList) {
					if (data.get("scenario").asText().equals(scenarioName)) {
						Map<String, Object> scenarioData = new HashMap<>();
						Iterator<String> fieldNames = data.fieldNames();
						while (fieldNames.hasNext()) {
							String fieldName = fieldNames.next();
							scenarioData.put(fieldName, data.get(fieldName).asText());
						}
						//Return the map which will be a json format
						return scenarioData;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
