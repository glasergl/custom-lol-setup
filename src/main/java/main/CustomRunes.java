package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.RunePage;
import model.RunePath;
import view.RunePageView;

public class CustomRunes {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			final RunePage runePage = new RunePage(RunePath.DOMINATION, RunePath.DOMINATION);
			frame.add(new RunePageView(runePage).getView(), BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
