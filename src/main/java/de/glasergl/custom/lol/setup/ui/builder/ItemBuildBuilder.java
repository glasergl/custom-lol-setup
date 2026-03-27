package de.glasergl.custom.lol.setup.ui.builder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.ItemBuild;
import lombok.Getter;

public final class ItemBuildBuilder {
    private final List<JTextField> notesTextFields = new ArrayList<>();
    private final List<List<Item>> items = new ArrayList<>();
    private final @Getter JPanel ui = new JPanel();
    
    private JPanel selectedItemRow;

    public ItemBuildBuilder() {
	this.ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
	this.selectedItemRow = addRow("New Row", new ArrayList<>());
    }

    public ItemBuildBuilder(final ItemBuild itemBuild) {
	
    }

    public void addRow() {
	addRow("", new ArrayList<>());
    }

    private JPanel addRow(final String note, final List<Item> items) {
	notesTextFields.add(new JTextField(note));
	return null;
    }

    public ItemBuild build() {
	final List<String> notes = this.notesTextFields.stream().map(JTextField::getText).toList();
	return new ItemBuild(notes, items);
    }
}
