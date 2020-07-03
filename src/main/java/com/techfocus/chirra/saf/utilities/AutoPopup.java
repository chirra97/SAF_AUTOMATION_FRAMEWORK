package com.techfocus.chirra.saf.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class AutoPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	public boolean isCancel = false;
	private final String message;

	public AutoPopup(String message) {
		this.message = message;
		initComponents();
	}

	private void initComponents() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
																		// task bar
		setLocation(scrSize.width / 2 - 200, scrSize.height / 2 - 100);

		getContentPane().setBackground(Color.LIGHT_GRAY);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED));
		setSize(225, 120);

		setLayout(null);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setLayout(new GridBagLayout());

		// Size set
		// setBounds(400,300, 479, 329);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;

		JLabel headingLabel = new JLabel(message, JLabel.CENTER);
		headingLabel.setForeground(Color.BLUE);

		/*
		 * image = new ImageIcon(
		 * Toolkit.getDefaultToolkit().getImage(AutoPopup.class.getResource(
		 * "/images/yourImage.jpg"))); headingLabel.setIcon(image);
		 */
		headingLabel.setOpaque(false);

		add(headingLabel, constraints);

		constraints.gridx++;
		constraints.weightx = 0f;
		constraints.weighty = 0f;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		JButton cloesButton = new JButton(new AbstractAction("x") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();
			}
		});
		cloesButton.setMargin(new Insets(1, 4, 1, 4));
		cloesButton.setFocusable(false);

		add(cloesButton, constraints);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000); // time after which pop up will be disappeared.
					dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	public static void main(String[] args) {

		AutoPopup obj = new AutoPopup("Love u MOM");
	}
}
