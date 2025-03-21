package fileIO;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.RunePage;
import model.RunePath;
import model.SelectableRune;
import model.SelectableRunes;

public class RunePageJson {
	public static JSONObject getJson(final RunePage runePage) {
		final JSONObject json = new JSONObject();
		json.put("title", runePage.getTitle());

		final JSONObject mainPathJson = new JSONObject();
		mainPathJson.put("name", runePage.getMainRunePath().getName());
		final JSONArray mainPathSelection = new JSONArray();
		for (final SelectableRune keyStone : runePage.getKeyStones().get().get(0)) {
			if (keyStone.isSelected()) {
				mainPathSelection.put(keyStone.getName());
			}
		}
		for (final List<SelectableRune> row : runePage.getSlotRunes().get()) {
			for (final SelectableRune rune : row) {
				if (rune.isSelected()) {
					mainPathSelection.put(rune.getName());
				}
			}
		}
		mainPathJson.put("selection", mainPathSelection);

		final JSONObject secondPathJson = new JSONObject();
		secondPathJson.put("name", runePage.getSecondRunePath().getName());
		final JSONArray secondPathSelection = new JSONArray();
		for (final List<SelectableRune> row : runePage.getSecondPathSlotRunes().get()) {
			for (final SelectableRune rune : row) {
				if (rune.isSelected()) {
					secondPathSelection.put(rune.getName());
				}
			}
		}
		secondPathJson.put("selection", secondPathSelection);

		final JSONArray shardsJson = new JSONArray();
		for (final List<SelectableRune> row : runePage.getShards().get()) {
			for (final SelectableRune shard : row) {
				if (shard.isSelected()) {
					shardsJson.put(shard.getName());
				}
			}
		}

		json.put("mainPath", mainPathJson);
		json.put("secondPath", secondPathJson);
		json.put("shards", shardsJson);
		return json;
	}

	public static RunePage getFromJson(final String runePageAsJsonString) {
		final JSONObject runePageJson = new JSONObject(runePageAsJsonString);
		final JSONObject mainPathJson = runePageJson.getJSONObject("mainPath");
		final JSONObject secondPathJson = runePageJson.getJSONObject("secondPath");
		final JSONArray shardsJson = runePageJson.getJSONArray("shards");

		final RunePage runePage = new RunePage(runePageJson.getString("uuid"), runePageJson.getString("title"),
				runePageJson.getString("group"), getRunePath(mainPathJson.getString("name")),
				getRunePath(secondPathJson.getString("name")), RunePath.ALL);
		for (final JSONArray selection : List.of(mainPathJson.getJSONArray("selection"),
				secondPathJson.getJSONArray("selection"), shardsJson)) {
			selectRunes(selection, runePage);
		}
		return runePage;
	}

	private static void selectRunes(final JSONArray selection, final RunePage runePage) {
		for (int i = 0; i < selection.length(); i++) {
			final String selectedRune = selection.getString(i);
			for (final SelectableRunes selectableRunes : List.of(runePage.getKeyStones(), runePage.getSlotRunes(),
					runePage.getSecondPathSlotRunes(), runePage.getShards())) {
				for (final List<SelectableRune> row : selectableRunes.get()) {
					for (final SelectableRune rune : row) {
						if (rune.getName().equals(selectedRune)) {
							rune.setSelected(true);
						}
					}
				}
			}
		}
	}

	private static RunePath getRunePath(final String name) {
		if (name.equals("Precision")) {
			return RunePath.PRECISION;
		} else if (name.equals("Domination")) {
			return RunePath.DOMINATION;
		} else if (name.equals("Sorcery")) {
			return RunePath.SORCERY;
		} else if (name.equals("Resolve")) {
			return RunePath.RESOLVE;
		} else if (name.equals("Inspiration")) {
			return RunePath.INSPIRATION;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
