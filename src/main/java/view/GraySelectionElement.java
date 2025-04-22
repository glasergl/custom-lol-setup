package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Element which switches between two states, selected and unselected.
 */
public final class GraySelectionElement {
	private final JPanel view = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private final JLabel icon = new JLabel();
	private final Supplier<Boolean> shouldBeSelectedCheck;
	private final Runnable onClickAction;
	private final Image selectedImage;
	private final Image unselectedImage;

	public GraySelectionElement(final Image selectedImage, final Image unselectedImage,
			final Supplier<Boolean> shouldBeSelectedCheck, final Runnable onClickAction) {
		this.shouldBeSelectedCheck = shouldBeSelectedCheck;
		this.onClickAction = onClickAction;
		this.selectedImage = selectedImage;
		this.unselectedImage = unselectedImage;
		icon.addMouseListener(getClickActionMouseListener());
		view.add(icon);
	}

	public void updateSelectionState() {
		icon.setIcon(new ImageIcon(shouldBeSelectedCheck.get() ? selectedImage : unselectedImage));
	}

	public JLabel getIconLabel() {
		return icon;
	}

	private MouseListener getClickActionMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClickAction.run();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};
	}

	public JPanel getView() {
		return view;
	}
}
