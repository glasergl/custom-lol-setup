package de.glasergl.custom.lol.setup.file;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;

import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.model.entity.Setups;
import lombok.Getter;

public final class SetupFileIo {
    private final Path outputFilePath = getPathOfOutputFile();
    private final Gson gson = new Gson();
    private final @Getter Setups setups;

    public SetupFileIo() throws IOException {
	setups = fetchSetups();
    }

    private Path getPathOfOutputFile() {
	final String pathFromEnvironmentVariable = System.getenv("LOL_SETUP_FILE_PATH");
	return Path.of(pathFromEnvironmentVariable != null && !pathFromEnvironmentVariable.isBlank() ? pathFromEnvironmentVariable : "lol-setup.json");
    }

    private Setups fetchSetups() throws IOException {
	if (!Files.exists(outputFilePath)) {
	    final Setups setups = new Setups(new HashMap<>(), new HashSet<>());
	    try (final Writer writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
		gson.toJson(setups, writer);
	    }
	}
	try (final Reader reader = Files.newBufferedReader(outputFilePath, StandardCharsets.UTF_8)) {
	    return gson.fromJson(reader, Setups.class);
	}
    }

    public void store(final Setup setupToStore) throws IOException {
	final List<Setup> existingMatchingSetup = setups.setups().stream().filter(storedSetup -> {
	    final boolean roleMatches = setupToStore.role() != null && storedSetup.role() != null ? setupToStore.role().equals(storedSetup.role()) : true;
	    return storedSetup.me().equals(setupToStore.me()) && storedSetup.enemy().equals(setupToStore.enemy()) && roleMatches;
	}).toList();
	setups.setups().removeAll(existingMatchingSetup);
	setups.setups().add(setupToStore);

	try (final Writer writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
	    gson.toJson(setups, writer);
	}
    }
}
