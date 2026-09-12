package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.ui.helper.EmptyMouseListener;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public final class RoleSelectionUi {
    private final @Getter JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER));

    public RoleSelectionUi(final Images images, final Consumer<Role> selectHandler) {
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
                    }
                }
            });
            roleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            roleLabel.setToolTipText(role.toString());
            ui.add(roleLabel);
        }
    }
}
