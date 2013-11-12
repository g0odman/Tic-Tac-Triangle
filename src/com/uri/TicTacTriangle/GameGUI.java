package com.uri.TicTacTriangle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameGUI extends JFrame
{
	private JPanel panel;

	public static Scanner reader = new Scanner(System.in);

	private char current = 'O', token;
	private int pos = 0;
	private char[][] board = new char[4][4];

	private static final long serialVersionUID = 6069778748946406294L;

	public GameGUI()
	{
		this.panel = new JPanel();
		setMinimumSize(new Dimension(500, 500));
		panel.setBackground(Color.red);
		setLocationRelativeTo(null);
		setTitle("kill me");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(4, 4);
		panel.setLayout(layout);
		setResizable(false);

		ActionListener buttonListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (arg0.getSource() instanceof GridButton)
				{
					GridButton button = (GridButton) arg0.getSource();
					int selectedX = button.getButtonX(), selectedY = button.getButtonY();
					determineTurn();
					board[selectedX][selectedY] = token;
					button.setText("" + token);
					button.setEnabled(false);
					if (check(board, current))
					{
						JOptionPane.showMessageDialog(GameGUI.this, current + " won!!!", "This game is worse than cancer", JOptionPane.OK_OPTION);
						System.exit(0);
					}
				}
			}
		};

		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
			{
				GridButton temp = new GridButton(x, y);
				temp.setText("");
				temp.addActionListener(buttonListener);
				panel.add(temp);
			}
		add(panel);
	}

	public static boolean check(char a[][], char token)
	{
		boolean checker = false;
		for (int i = 0; i < 4; i++)
		{
			checker = lines(a, i, 0, 0, 1, token);
			if (checker)
				return checker;

			checker = lines(a, 0, i, 1, 0, token);
			if (checker)
				return checker;
		}
		checker = lines(a, 0, 0, 1, 1, token);
		if (checker)
			return checker;
		checker = lines(a, 0, 3, 1, -1, token);

		return checker;

	}

	public static boolean lines(char a[][], int row, int column, int dr, int dc, char token)
	{
		while (a[row][column] == token || a[row][column] == 'P')
		{	row += dr;
			column += dc;
			if (row == 4 || column == 4)
			{
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args)
	{
		new GameGUI().setVisible(true);

	}

	public void createGame()
	{

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				board[i][j] = '_';
	}

	public void determineTurn()
	{
		if (current == 'X')
			current = 'O';
		else
			current = 'X';
		if (current == 'X')
		{
			if (pos % 2 == 0)
			{
				if (getInput())
					token = 'P';
				else
				{
					token = 'X';
					pos += 1;
				}
			}
			else
			{
				token = 'P';
				pos--;
			}
		}
		else
		{
			if (pos < 2)
			{
				if (getInput())
					token = 'P';
				else
				{
					token = 'O';
					pos += 2;
				}
			}
			else
			{
				token = 'P';
				pos -= 2;
			}
		}

	}

	private boolean getInput()
	{
		return JOptionPane.showConfirmDialog(this, "Would you like to place a triangle?", "DO IT", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	private static class GridButton extends JButton
	{
		private static final long serialVersionUID = -5345685610684876465L;
		private int x, y;

		public GridButton(int x, int y)
		{
			super();
			this.x = x;
			this.y = y;
		}

		public int getButtonX()
		{
			return this.x;
		}

		public int getButtonY()
		{
			return this.y;
		}
	}
}
