package de.glasergl.custom.lol.setup.file;

import com.google.gson.Gson;
import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.ChampionSetup;
import de.glasergl.custom.lol.setup.model.entity.MatchUp;
import de.glasergl.custom.lol.setup.model.entity.Role;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class SetupFileIo {
    private final String setupFileEnvironmentVariableName = "LOL_SETUP_FILE_PATH";
    private final Path defaultSetupsDirectoryPath = Path.of("./champs/");
    private final Path setupsDirectoryPath = getSetupsDirectoryPath();
    private final Gson gson = new Gson();
    private final Set<ChampionSetup> setups;

    public SetupFileIo() throws IOException {
        log.debug("Resolved setup directory path to '{}'", setupsDirectoryPath);
        this.setups = fetchSetups();
    }

    public Optional<ChampionSetup> getSetup(final Champion me, final Role role) {
        for (final ChampionSetup setup : setups) {
            if (setup.me().equals(me) && setup.role().equals(role)) {
                return Optional.of(setup);
            }
        }
        return Optional.empty();
    }

    public Optional<MatchUp> getMatchUp(final Optional<ChampionSetup> setup, final Champion enemy) {
        if (setup.isEmpty()) {
            return Optional.empty();
        }
        for (final MatchUp matchUp : setup.get().matchUps()) {
            if (matchUp.enemy().equals(enemy)) {
                return Optional.of(matchUp);
            }
        }
        return Optional.empty();
    }

    private Path getSetupsDirectoryPath() {
        final String pathFromEnvironmentVariable = System.getenv(setupFileEnvironmentVariableName);
        return pathFromEnvironmentVariable != null && !pathFromEnvironmentVariable.isBlank() ? Paths.get(pathFromEnvironmentVariable) : defaultSetupsDirectoryPath;
    }

    private Set<ChampionSetup> fetchSetups() throws IOException {
        try (final Stream<Path> subPaths = Files.walk(setupsDirectoryPath)) {
            final Set<Path> setupFiles = subPaths.filter(path -> Files.isRegularFile(path) && path.endsWith(".json"))
                    .collect(Collectors.toSet());
            final Set<ChampionSetup> championSetups = new HashSet<>();
            for (final Path setupFile : setupFiles) {
                championSetups.add(gson.fromJson(Files.readString(setupFile), ChampionSetup.class));
            }
            return championSetups;
        }
    }

    public void store(final Champion me, final Role role, final String notes, final MatchUp matchUpToStore) throws IOException {
        final Optional<ChampionSetup> existingSetup = getSetup(me, role);
        existingSetup.ifPresent(setups::remove);
        final ChampionSetup updatedSetup = existingSetup.isPresent() ? replaceMatchUp(existingSetup.get(), matchUpToStore, notes) : new ChampionSetup(me, role, notes, Set.of(matchUpToStore));
        final String fileName = String.format("%s_%s.json", role.toString(), me.toString());
        final Path setupFilePath = Path.of(setupsDirectoryPath.toString(), fileName);
        try (final Writer writer = Files.newBufferedWriter(setupFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            gson.toJson(updatedSetup, writer);
        }
        setups.add(updatedSetup);
    }

    private ChampionSetup replaceMatchUp(final ChampionSetup setup, final MatchUp matchUpToStore, final String notes) {
        final Set<MatchUp> updatedMatchUps = new HashSet<>(setup.matchUps());
        final Iterator<MatchUp> matchUpIterator = updatedMatchUps.iterator();
        MatchUp nextMatchUp;
        while (matchUpIterator.hasNext()) {
            nextMatchUp = matchUpIterator.next();
            if (nextMatchUp.enemy().equals(matchUpToStore.enemy())) {
                matchUpIterator.remove();
            }
        }
        updatedMatchUps.add(matchUpToStore);
        return new ChampionSetup(setup.me(), setup.role(), notes, updatedMatchUps);
    }
}
