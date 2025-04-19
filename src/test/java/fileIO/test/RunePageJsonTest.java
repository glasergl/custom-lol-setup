package fileIO.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import fileIO.RunePageJson;
import model.RunePage;
import model.RunePath;

class RunePageJsonTest {
	@Test
	void testToJson() {
		final RunePage runePage = new RunePage("title", RunePath.PRECISION, RunePath.SORCERY);
		runePage.selectKeyStone(0);
		runePage.selectSlotRune(0, 1);
		final JSONObject runePageJson = RunePageJson.getJson(runePage);
		final String runePageJsonString = runePageJson.toString();

		assertTrue(runePageJsonString.contains("Precision"));
		assertTrue(runePageJsonString.contains("Sorcery"));
		assertTrue(runePageJsonString.contains("Press the Attack"));
		assertTrue(runePageJsonString.contains("Triumph"));
		assertFalse(runePageJsonString.contains("Lethal Tempo"));
		assertFalse(runePageJsonString.contains("Presence of Mind"));
	}

	@Test
	void testFromJson() {
		final RunePage expectedRunePage = new RunePage("title", RunePath.DOMINATION, RunePath.PRECISION);
		expectedRunePage.selectKeyStone(2);
		expectedRunePage.selectSlotRune(2, 0);
		expectedRunePage.selectShard(0, 0);
		final JSONObject runePageJson = RunePageJson.getJson(expectedRunePage);
		final RunePage actualRunePage = RunePageJson.getFromJson(runePageJson);

		assertTrue(actualRunePage.getMainRunePath().equals(RunePath.DOMINATION));
		assertTrue(actualRunePage.getSecondRunePath().equals(RunePath.PRECISION));
	}
}
