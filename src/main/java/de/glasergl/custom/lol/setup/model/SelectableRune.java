package de.glasergl.custom.lol.setup.model;

import de.glasergl.custom.lol.setup.model.entity.Rune;
import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper of a rune to make it selectable. An instance of a rune should only be
 * created once per rune, but instances of this class can be created as often as
 * desired, i.e., for multiple rune pages containing the same runes.
 */
@Getter
public final class SelectableRune {
    private final Rune rune;
    @Setter
    private boolean isSelected;

    public SelectableRune(final Rune rune, final boolean initiallySelected) {
        this.rune = rune;
        this.isSelected = initiallySelected;
    }

    @Override
    public String toString() {
        return rune.toString();
    }
}
