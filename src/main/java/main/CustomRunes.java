package main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.RunePageSuite;
import view.RunePageSuiteView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			try {
				frame.setIconImage(ImageIO.read(CustomRunes.class.getResource("/GatheringStorm.png"))
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			final RunePageSuite runePageSuite = new RunePageSuite();
			final RunePageSuiteView runePageSuiteView = new RunePageSuiteView(runePageSuite);
			final JPanel p = new JPanel();
			p.add(runePageSuiteView.getView());
			frame.add(p, BorderLayout.CENTER);
//			frame.setSize(1200, 800);
//			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
