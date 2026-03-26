package de.glasergl.custom.lol.setup.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.glasergl.custom.lol.setup.model.entity.Setup;
import lombok.Getter;

public final class SetupFileIo {
    private final Path outputFilePath = Path.of("lol-setup.json");
    private final Gson gson = new Gson();
    private final Type serializationType = new TypeToken<Set<Setup>>() {
    }.getType();
    private final @Getter Set<Setup> setups;

    public SetupFileIo() throws IOException {
	setups = fetchSetups();
    }

    private Set<Setup> fetchSetups() throws IOException {
	if (!Files.exists(outputFilePath)) {
	    Files.writeString(outputFilePath, "[]", StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
	}
	try (final Reader reader = Files.newBufferedReader(outputFilePath, StandardCharsets.UTF_8)) {
	    return gson.fromJson(reader, serializationType);
	}
    }

    public void store(final Setup setupToStore) throws IOException {
	final List<Setup> existingMatchingSetup = setups.stream().filter(storedSetup -> {
	    final boolean roleMatches = setupToStore.role() != null && storedSetup.role() != null ? setupToStore.role().equals(storedSetup.role()) : true;
	    return storedSetup.me().equals(setupToStore.me()) && storedSetup.enemy().equals(setupToStore.enemy()) && roleMatches;
	}).toList();
	setups.removeAll(existingMatchingSetup);
	setups.add(setupToStore);

	try (final Writer writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
	    gson.toJson(setups, writer);
	}
    }
}
