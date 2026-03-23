package de.glasergl.custom.lol.setup.io.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.model.builder.RunePageBuilder;
import de.glasergl.custom.lol.setup.model.entity.RunePath;

/**
 * Creates the GUI frame for this application.
 */
public final class CreateFrame {
    private final JFrame jFrame;
    private final String title = "Custom LoL Setup";
    private final Images images;
    private final String legalBoilerPlateText = title
	    + " isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing Riot Games properties. Riot Games, and all associated properties are trademarks or registered trademarks of Riot Games, Inc.";

    public CreateFrame(final Images images) {
	this.jFrame = new JFrame(title);
	this.images = images;
	jFrame.setIconImage(images.getFrameIcon());
	jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	createAndAddComponents();
	jFrame.pack();
	jFrame.setLocationRelativeTo(null);
	jFrame.setVisible(true);
    }

    private void createAndAddComponents() {
	final Container frameContentPane = jFrame.getContentPane();
	frameContentPane.setLayout(new BorderLayout());
	final ChampionSelectionUi meSelection = new ChampionSelectionUi(champion -> {
	    // TODO
	}, images);
	meSelection.getUi().setBorder(new TitledBorder("Me"));
	final ChampionSelectionUi enemySelection = new ChampionSelectionUi(champion -> {
	    // TODO
	}, images);
	enemySelection.getUi().setBorder(new TitledBorder("Enemy"));
	frameContentPane.add(meSelection.getUi(), BorderLayout.WEST);
	frameContentPane.add(enemySelection.getUi(), BorderLayout.CENTER);
	final JTextArea legalBoilerPlateComponent = new JTextArea(legalBoilerPlateText);
	legalBoilerPlateComponent.setLineWrap(true);
	legalBoilerPlateComponent.setEditable(false);
	legalBoilerPlateComponent.setWrapStyleWord(true);
	frameContentPane.add(legalBoilerPlateComponent, BorderLayout.SOUTH);
	frameContentPane.add(new RoleSelectionUi(images).getUi(), BorderLayout.NORTH);
	frameContentPane.add(new RunePageBuilderView(new RunePageBuilder(RunePath.PRECISION, RunePath.SORCERY), images).getView(), BorderLayout.EAST);
    }

    public JFrame getFrame() {
	return jFrame;
    }
}
