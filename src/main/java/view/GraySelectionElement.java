package view;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class GraySelectionElement {
	private final JPanel runeView = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private final JLabel runeIcon = new JLabel();
	private final Supplier<Boolean> shouldBeSelectedCheck;
	private final Runnable onClick;
	private final Image coloredIcon;
	private final Image grayScaleIcon;

	public GraySelectionElement(final Image image, final Supplier<Boolean> shouldBeSelectedCheck,
			final Runnable onClick) {
		this.shouldBeSelectedCheck = shouldBeSelectedCheck;
		this.onClick = onClick;
		this.coloredIcon = image;
		this.grayScaleIcon = getGrayScale(coloredIcon);
		runeIcon.addMouseListener(getRuneSelectionMouseListener());
		runeView.add(runeIcon);
	}

	public void updateSelectionState() {
		runeIcon.setIcon(new ImageIcon(shouldBeSelectedCheck.get() ? coloredIcon : grayScaleIcon));
	}

	private MouseListener getRuneSelectionMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick.run();
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
		return runeView;
	}

	private Image getGrayScale(final Image image) {
		final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_BYTE_GRAY);
		final Graphics2D painter = bufferedImage.createGraphics();
		painter.drawImage(image, 0, 0, runeView.getBackground(), null);
		painter.dispose();
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(bufferedImage, null);
	}
}
