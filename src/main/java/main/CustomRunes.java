package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.RunePageBuilder;
import model.RunePath;
import view.RunePageBuilderView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			UIManager.put("Panel.background", new Color(34, 34, 34));
			final JFrame frame = new JFrame("Custom Runes");
			try {
				frame.setIconImage(ImageIO.read(CustomRunes.class.getResource("/GatheringStorm.png"))
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			final RunePageBuilder runePageSelection = new RunePageBuilder(List.of(RunePath.PRECISION,
					RunePath.DOMINATION, RunePath.SORCERY, RunePath.RESOLVE, RunePath.INSPIRATION), RunePath.SORCERY,
					RunePath.DOMINATION);
			final JPanel p = new JPanel();
			p.add(new RunePageBuilderView(runePageSelection).getView());
			frame.add(p, BorderLayout.CENTER);
			frame.setSize(1200, 800);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
