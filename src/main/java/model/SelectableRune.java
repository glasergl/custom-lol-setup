package model;

public final class SelectableRune {
	private final Rune rune;
	private boolean isSelected;

	public SelectableRune(final Rune rune, final boolean initiallySelected) {
		this.rune = rune;
		this.isSelected = initiallySelected;
	}

	public Rune getRune() {
		return rune;
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
