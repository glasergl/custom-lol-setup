package main;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.RunePageBuilder;
import model.RunePath;
import view.RunePageBuilderView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			try {
				frame.setIconImage(ImageIO.read(CustomRunes.class.getResource("/Conqueror.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			final RunePageBuilder runePageSelection = new RunePageBuilder(List.of(RunePath.PRECISION,
					RunePath.DOMINATION, RunePath.SORCERY, RunePath.RESOLVE, RunePath.INSPIRATION), RunePath.SORCERY,
					RunePath.DOMINATION);
			frame.add(new RunePageBuilderView(runePageSelection).getView(), BorderLayout.CENTER);
			frame.setSize(1200, 800);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
