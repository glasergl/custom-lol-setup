package de.glasergl.custom.lol.setup.io.ui;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.model.entity.Role;

public class RoleSelectionUi {
    private final JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER));

    public RoleSelectionUi(final Images images) {
	for (final Role role : Role.values()) {
	    ui.add(new JLabel(new ImageIcon(images.getRoleImage(role))));
	}
    }

    public JPanel getUi() {
	return ui;
    }
}
