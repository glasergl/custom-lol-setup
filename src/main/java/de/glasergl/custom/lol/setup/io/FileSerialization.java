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

public class FileSerialization {
    private static final Path OUTPUT_FILE_PATH = Path.of("lol-setup.json");
    private static final Gson GSON = new Gson();
    private static final Type SERIALIZATION_TYPE = new TypeToken<Set<Setup>>() {
    }.getType();

    public static Set<Setup> getSetups() throws IOException {
	if (!Files.exists(OUTPUT_FILE_PATH)) {
	    Files.writeString(OUTPUT_FILE_PATH, "[]", StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
	}
	try (final Reader reader = Files.newBufferedReader(OUTPUT_FILE_PATH, StandardCharsets.UTF_8)) {
	    return GSON.fromJson(reader, SERIALIZATION_TYPE);
	}
    }

    public static void storeRunePages(final List<Setup> lolSetup) throws IOException {
	try (Writer writer = Files.newBufferedWriter(OUTPUT_FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
	    GSON.toJson(lolSetup, writer);
	}
    }
}
