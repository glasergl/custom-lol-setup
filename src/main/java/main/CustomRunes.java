package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.RunePath;
import view.MainRunePathView;
import view.SecondRunePathView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(new MainRunePathView(RunePath.DOMINATION).getView(), BorderLayout.CENTER);
			frame.add(new SecondRunePathView(RunePath.DOMINATION).getView(), BorderLayout.EAST);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
