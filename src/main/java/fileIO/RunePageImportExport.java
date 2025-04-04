package fileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import model.RunePage;

public class RunePageImportExport {
	private static final Path OUTPUT_FILE_PATH = Path.of("runes.json");
	private static final String RUNE_GROUP_JSON_KEY = "runeGroups";

	public static Map<String, List<RunePage>> getRunePages() throws IOException {
		if (!Files.exists(OUTPUT_FILE_PATH)) {
			Files.writeString(OUTPUT_FILE_PATH, String.format("{\"%s\":{}}", RUNE_GROUP_JSON_KEY),
					StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
		}
		final String fileContent = Files.readString(OUTPUT_FILE_PATH);
		final JSONObject runesJson = new JSONObject(fileContent);
		final JSONObject runeGroupsJson = runesJson.getJSONObject("runeGroups");
		final Map<String, List<RunePage>> runePagesByGroup = new HashMap<>();
		for (final String key : runeGroupsJson.keySet()) {
			final JSONArray runePagesOfGroupJson = runeGroupsJson.getJSONArray(key);
			final List<RunePage> runePagesOfGroup = new ArrayList<>();
			for (int i = 0; i < runePagesOfGroupJson.length(); i++) {
				final JSONObject runePageJson = runePagesOfGroupJson.getJSONObject(i);
				runePagesOfGroup.add(RunePageJson.getFromJson(runePageJson));
			}
		}
		return runePagesByGroup;
	}

	public static void storeRunePages(final Map<String, List<RunePage>> runePagesByGroup) throws IOException {
		final JSONObject runeGroupsJson = new JSONObject();
		for (final String runeGroupName : runePagesByGroup.keySet()) {
			final JSONArray runeGroupJson = new JSONArray();
			for (final RunePage runePage : runePagesByGroup.get(runeGroupName)) {
				runeGroupJson.put(RunePageJson.getJson(runePage));
			}
			runeGroupsJson.put(runeGroupName, runeGroupJson);
		}
		final JSONObject runesJson = new JSONObject();
		runesJson.put(RUNE_GROUP_JSON_KEY, runeGroupsJson);
		Files.writeString(OUTPUT_FILE_PATH, runesJson.toString(), StandardOpenOption.WRITE);
	}
}
