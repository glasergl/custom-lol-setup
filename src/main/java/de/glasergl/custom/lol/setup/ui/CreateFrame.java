package de.glasergl.custom.lol.setup.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.file.SetupFileIo;
import de.glasergl.custom.lol.setup.ui.selection.ChampionSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.RoleSelectionUi;

/**
 * Creates the GUI frame for this application.
 */
public final class CreateFrame {
    private final JFrame frame;
    private final String title = "Custom LoL Setup";
    private final Images images;
    private final SetupFileIo setupFileIo;
    private final String legalBoilerPlateText = title
	    + " isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing Riot Games properties. Riot Games, and all associated properties are trademarks or registered trademarks of Riot Games, Inc.";

    public CreateFrame(final Images images, final SetupFileIo setupFileIo) {
	this.frame = new JFrame(title);
	this.images = images;
	this.setupFileIo = setupFileIo;
	frame.setIconImage(images.getFrameIcon());
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	createAndAddComponents();
	frame.setResizable(false);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }

    private void createAndAddComponents() {
	final Container frameContentPane = frame.getContentPane();
	frameContentPane.setLayout(new BorderLayout());

	final SetupSelectionHandler setupSelectionHandler = new SetupSelectionHandler(setupFileIo, images);
	final ChampionSelectionUi meSelection = new ChampionSelectionUi(champion -> {
	    setupSelectionHandler.setMe(champion);
	}, images);
	meSelection.getUi().setBorder(new TitledBorder("Me"));
	final ChampionSelectionUi enemySelection = new ChampionSelectionUi(champion -> {
	    setupSelectionHandler.setEnemy(champion);
	}, images);
	enemySelection.getUi().setBorder(new TitledBorder("Enemy"));
	final JPanel setupSelection = new JPanel(new BorderLayout());
	setupSelection.add(meSelection.getUi(), BorderLayout.WEST);
	setupSelection.add(enemySelection.getUi(), BorderLayout.EAST);

	final RoleSelectionUi roleSelectionUi = new RoleSelectionUi(images, role -> {
	    setupSelectionHandler.setRole(role);
	}, role -> {
	    setupSelectionHandler.unsetRole();
	});
	setupSelection.add(roleSelectionUi.getUi(), BorderLayout.SOUTH);

	frameContentPane.add(setupSelection, BorderLayout.WEST);

	final JTextArea legalBoilerPlateComponent = new JTextArea(legalBoilerPlateText);
	legalBoilerPlateComponent.setLineWrap(true);
	legalBoilerPlateComponent.setEditable(false);
	legalBoilerPlateComponent.setWrapStyleWord(true);
	legalBoilerPlateComponent.setOpaque(false);
	legalBoilerPlateComponent.setFocusable(false);
	legalBoilerPlateComponent.setBorder(new EmptyBorder(3, 3, 3, 3));
	frameContentPane.add(legalBoilerPlateComponent, BorderLayout.SOUTH);

	frameContentPane.add(setupSelectionHandler.getUi(), BorderLayout.CENTER);
    }

    public JFrame getFrame() {
	return frame;
    }
}
