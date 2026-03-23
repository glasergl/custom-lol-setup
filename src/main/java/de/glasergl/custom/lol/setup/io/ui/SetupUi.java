package de.glasergl.custom.lol.setup.io.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.model.builder.RunePageBuilder;
import de.glasergl.custom.lol.setup.model.entity.RunePath;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import lombok.Getter;

public class SetupUi {
    private final RunePageBuilderView runePageBuilderView;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final JTextArea notes = new JTextArea(10, 30);

    private SetupUi(final Images images, final RunePageBuilder runePageBuilder) {
	this.runePageBuilderView = new RunePageBuilderView(runePageBuilder, images);
	ui.add(runePageBuilderView.getView(), BorderLayout.CENTER);
	final JScrollPane scrollableNotes = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollableNotes.setBorder(new TitledBorder("Notes"));
	ui.add(scrollableNotes, BorderLayout.SOUTH);
    }

    public SetupUi(final Images images) {
	this(images, new RunePageBuilder(RunePath.PRECISION, RunePath.RESOLVE));
    }

    public SetupUi(final Setup setup, final Images images) {
	this(images, new RunePageBuilder(setup.runePage()));
    }
}
