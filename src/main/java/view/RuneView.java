package view;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Rune;

public class RuneView {
	private final Rune rune;
	private final JPanel runeView = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private final JLabel runeIcon = new JLabel();
	private final Image coloredIcon;
	private final Image grayScaleIcon;
	private final List<RuneView> excludingRuneViews = new ArrayList<>();

	public RuneView(final Rune rune, final int runeIconSize) {
		this.rune = rune;
		final Image image = ImageReading.getImageFromName(rune.getName(), "png");
		this.coloredIcon = image.getScaledInstance(runeIconSize, runeIconSize, Image.SCALE_SMOOTH);
		this.grayScaleIcon = getGrayScale(coloredIcon);
		setSelected(rune.isSelected());
		runeView.add(runeIcon);
	}s
	
	private MouseListener getRuneSelectionMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rune.setSelected(true);
				for (final RuneView excludingRuneView : excludingRuneViews) {
					excludingRuneView.setSelected(false);
				}
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

	public void addExcludingRuneView(final RuneView runeView) {
		excludingRuneViews.add(runeView);
	}

	public JPanel getView() {
		return runeView;
	}

	public void setSelected(final boolean shouldBeSelected) {
		runeIcon.setIcon(new ImageIcon(shouldBeSelected ? coloredIcon : grayScaleIcon));
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
