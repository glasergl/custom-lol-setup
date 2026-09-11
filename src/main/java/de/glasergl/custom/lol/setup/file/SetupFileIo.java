package de.glasergl.custom.lol.setup.file;

import com.google.gson.Gson;
import de.glasergl.custom.lol.setup.model.entity.MatchUp;
import de.glasergl.custom.lol.setup.model.entity.Setups;
import lombok.Getter;

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

    public void store(final MatchUp matchUpToStore) throws IOException {
        final List<MatchUp> existingMatchingMatchUp = setups.matchUps().stream().filter(storedSetup -> {
            final boolean roleMatches = matchUpToStore.role() == null || storedSetup.role() == null || matchUpToStore.role().equals(storedSetup.role());
            return storedSetup.me().equals(matchUpToStore.me()) && storedSetup.enemy().equals(matchUpToStore.enemy()) && roleMatches;
        }).toList();
        setups.matchUps().removeAll(existingMatchingMatchUp);
        setups.matchUps().add(matchUpToStore);

        try (final Writer writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            gson.toJson(setups, writer);
        }
    }
}
