package model;

/**
 * Wrapper of a rune to make it selectable. An instance of a rune should only be
 * created once per rune, but instances of this class can be created as often as
 * desired, i.e., for multiple rune pages containing the same runes.
 */
public final class SelectableRune {
	private final Rune rune;
	private boolean isSelected;

	public SelectableRune(final Rune rune, final boolean initiallySelected) {
		this.rune = rune;
		this.isSelected = initiallySelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(final boolean shouldBeSelected) {
		this.isSelected = shouldBeSelected;
	}

	public String getName() {
		return rune.getName();
	}
}
