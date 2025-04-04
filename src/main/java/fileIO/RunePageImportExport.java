package fileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.RunePage;

public class RunePageImportExport {
	private static final Path OUTPUT_FILE_PATH = Path.of("runes.json");

	public static List<RunePage> getRunePages() throws IOException {
		if (!Files.exists(OUTPUT_FILE_PATH)) {
			Files.writeString(OUTPUT_FILE_PATH, "{\"runes\":{}}", StandardOpenOption.CREATE_NEW,
					StandardOpenOption.WRITE);
		}
		final String fileContent = Files.readString(OUTPUT_FILE_PATH);
		final JSONObject runesJson = new JSONObject(fileContent);
		final JSONArray runePagesJson = runesJson.getJSONArray("runePages");
		final List<RunePage> runePages = new ArrayList<>();
		for (int i = 0; i < runePagesJson.length(); i++) {
			final JSONObject runePageJson = runePagesJson.getJSONObject(i);
			runePages.add(RunePageJson.getFromJson(runePageJson));
		}
		return runePages;
	}

	public static void storeRunePages(final List<RunePage> runePages) throws IOException {
		final JSONArray runePagesSerialization = new JSONArray();
		for (final RunePage runePage : runePages) {
			runePagesSerialization.put(RunePageJson.getJson(runePage));
		}
		final JSONObject runePageSerialization = new JSONObject();
		runePageSerialization.put("runePages", runePagesSerialization);
		Files.writeString(OUTPUT_FILE_PATH, runePageSerialization.toString(), StandardOpenOption.WRITE);
	}
}
