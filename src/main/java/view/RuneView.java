package view;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.SelectableRune;

public class RuneView {
	private final SelectableRune selectableRune;
	private final JPanel runeView = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private final JLabel runeIcon = new JLabel();
	private final Runnable onClick;
	private final Image coloredIcon;
	private final Image grayScaleIcon;

	public RuneView(final SelectableRune rune, final int runeIconSize, final Runnable onClick) {
		this.selectableRune = rune;
		this.onClick = onClick;
		final Image image = ImageReading.getImageFromName(rune.getRune().getName(), "png");
		this.coloredIcon = image.getScaledInstance(runeIconSize, runeIconSize, Image.SCALE_SMOOTH);
		this.grayScaleIcon = getGrayScale(coloredIcon);
		runeIcon.addMouseListener(getRuneSelectionMouseListener());
		runeView.add(runeIcon);
	}

	public void updateSelectionState() {
		runeIcon.setIcon(new ImageIcon(selectableRune.isSelected() ? coloredIcon : grayScaleIcon));
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
		painter.drawImage(image, 0, 0, null);
		painter.dispose();
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(bufferedImage, null);
	}
}
