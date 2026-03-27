package de.glasergl.custom.lol.setup.ui.selection;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.ui.misc.EmptyMouseListener;
import lombok.Getter;

public final class RoleSelectionUi {
    private final @Getter JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER));

    public RoleSelectionUi(final Images images, final Consumer<Role> selectHandler, final Consumer<Role> unselectHandler) {
	for (final Role role : List.of(Role.TOP, Role.JUNGLE, Role.MID, Role.BOT, Role.SUPPORT)) {
	    final JLabel roleLabel = new JLabel(new ImageIcon(images.getRoleImage(role)));
	    roleLabel.setOpaque(true);
	    roleLabel.addMouseListener(new EmptyMouseListener() {
		@Override
		public void mouseClicked(final MouseEvent click) {
		    if (roleLabel.getBackground() != Color.CYAN) {
			for (final Component component : ui.getComponents()) {
			    component.setBackground(null);
			}
			roleLabel.setBackground(Color.CYAN);
			selectHandler.accept(role);
		    } else {
			roleLabel.setBackground(null);
			unselectHandler.accept(role);
		    }
		}
	    });
	    roleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    ui.add(roleLabel);
	}
    }
}
