package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fileIO.RunePageImportExport;
import model.RunePage;
import model.RunePageSuite;
import view.RunePageSuiteView;

public class CustomRunes {
	public static void main(String[] args) {
		final Map<String, List<RunePage>> runePagesByGroup;
		try {
			runePagesByGroup = RunePageImportExport.getRunePages();
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to interact with files", "File Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			try {
				frame.setIconImage(ImageIO.read(CustomRunes.class.getResource("/GatheringStorm.png"))
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unabel to read image from jar", "Jar Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			final RunePageSuite runePageSuite = new RunePageSuite(runePagesByGroup);
			final RunePageSuiteView runePageSuiteView = new RunePageSuiteView(runePageSuite);
			final Container frameContentPane = frame.getContentPane();
			frameContentPane.setLayout(new BorderLayout());
			frameContentPane.add(runePageSuiteView.getView(), BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
