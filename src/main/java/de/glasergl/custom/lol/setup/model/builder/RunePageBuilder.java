package de.glasergl.custom.lol.setup.model.builder;

import de.glasergl.custom.lol.setup.model.SelectableRunes;
import de.glasergl.custom.lol.setup.model.SelectableSecondPathRunes;
import de.glasergl.custom.lol.setup.model.entity.Rune;
import de.glasergl.custom.lol.setup.model.entity.RunePage;
import de.glasergl.custom.lol.setup.model.entity.RunePath;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public final class RunePageBuilder {
    private final @Getter SelectableRunes selectableShards = new SelectableRunes(RunePath.SHARDS);

    private @Getter RunePath mainRunePath;
    private @Getter RunePath secondRunePath;
    private @Getter SelectableRunes selectableKeyStones;
    private @Getter SelectableRunes selectableSlotRunes;
    private @Getter SelectableSecondPathRunes selectableSecondPathSlotRunes;

    public RunePageBuilder(final RunePath mainRunePath, final RunePath secondRunePath) {
        if (mainRunePath.equals(secondRunePath)) {
            throw new IllegalArgumentException();
        }
        this.mainRunePath = mainRunePath;
        this.secondRunePath = secondRunePath;
        this.selectableKeyStones = new SelectableRunes(List.of(mainRunePath.keyStones()));
        this.selectableSlotRunes = new SelectableRunes(mainRunePath.slotRuneRows());
        this.selectableSecondPathSlotRunes = new SelectableSecondPathRunes(secondRunePath.slotRuneRows());
    }

    public RunePageBuilder(final RunePage initialRunePage) {
        this(initialRunePage.main(), initialRunePage.second());
        selectableKeyStones.select(initialRunePage.mainPathRuneSelections());
        selectableSlotRunes.select(initialRunePage.mainPathRuneSelections());
        selectableSecondPathSlotRunes.select(initialRunePage.secondPathRuneSelections());
        selectableShards.select(initialRunePage.selectedShards());
    }

    public void selectKeyStone(final int columnIndex) {
        selectableKeyStones.select(0, columnIndex);
    }

    public void selectSlotRune(final int rowIndex, final int columnIndex) {
        selectableSlotRunes.select(rowIndex, columnIndex);
    }

    public void selectSecondPath(final int rowIndex, final int columnIndex) {
        selectableSecondPathSlotRunes.select(rowIndex, columnIndex);
    }

    public void selectShard(final int rowIndex, final int columnIndex) {
        selectableShards.select(rowIndex, columnIndex);
    }

    public RunePage build() {
        final Set<Rune> mainPathRunes = selectableKeyStones.getSelected();
        mainPathRunes.addAll(selectableSlotRunes.getSelected());
        return new RunePage(mainRunePath, secondRunePath, mainPathRunes, selectableSecondPathSlotRunes.getSelected(), selectableShards.getSelected());
    }

    public void selectMainPath(final RunePath nextMainRunePath) {
        if (nextMainRunePath.equals(mainRunePath)) {
            return;
        }
        selectableKeyStones = new SelectableRunes(List.of(nextMainRunePath.keyStones()));
        selectableSlotRunes = new SelectableRunes(nextMainRunePath.slotRuneRows());
        mainRunePath = nextMainRunePath;
        if (nextMainRunePath.equals(secondRunePath)) {
            final RunePath alternativeSecondRunePath = getFirstRunePathExcept(nextMainRunePath);
            secondRunePath = alternativeSecondRunePath;
            selectableSecondPathSlotRunes = new SelectableSecondPathRunes(alternativeSecondRunePath.slotRuneRows());
        }
    }

    public void selectSecondPath(final RunePath nextSecondRunePath) {
        if (nextSecondRunePath.equals(secondRunePath) || nextSecondRunePath.equals(mainRunePath)) {
            return;
        }
        secondRunePath = nextSecondRunePath;
        selectableSecondPathSlotRunes = new SelectableSecondPathRunes(nextSecondRunePath.slotRuneRows());
    }

    private RunePath getFirstRunePathExcept(final RunePath excludedRunePath) {
        for (final RunePath runePath : RunePath.ALL) {
            if (!runePath.equals(excludedRunePath)) {
                return runePath;
            }
        }
        throw new IllegalStateException("Not enough rune paths to select a different one than" + excludedRunePath.name());
    }
}
