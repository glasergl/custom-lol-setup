package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.RunePath;
import view.RunePathView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(new RunePathView(RunePath.DOMINATION).getView(), BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
