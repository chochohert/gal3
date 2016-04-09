package baseballgame;

import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;
import java.nio.channels.GatheringByteChannel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

class InItBaseBallGame extends JFrame {
	private JScrollPane scroll;
	private JPanel main, game_panel;
	private JTextField namefield, first_num, second_num, third_num, forth_num, fiveth_num;
	private JTextArea show_Gameresult;
	private JButton ok_bnt, exit, play_bnt, rank_show, next_stage_bnt, pre_bnt, ok_dialog;
	Scanner scan = new Scanner(System.in);
	private StartBaseBallGame game;
	private JOptionPane joption_panel;
	private JDialog dialog;

	public InItBaseBallGame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main = new JPanel();
		game_panel = new JPanel();
		namefield = new JTextField("이름을 입력하세요.");
		ok_bnt = new JButton("확인");
		exit = new JButton("나가기");
		pre_bnt = new JButton("이전으로");
		ok_dialog = new JButton("확인");
		dialog = new JDialog();
		joption_panel = new JOptionPane();

		scroll = new JScrollPane(show_Gameresult);

		namefield.addKeyListener(new keyeventListener());
		pre_bnt.addKeyListener(new keyeventListener());
		ok_bnt.addKeyListener(new keyeventListener());
		ok_bnt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainInIt();
			}
		});

		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});

		ok_dialog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dialog.dispose();

			}
		});

		main.add(namefield);
		main.add(ok_bnt);
		main.add(exit);
		play_bnt = new JButton("입력");
		play_bnt.setBounds(35, 220, 57, 23);
		play_bnt.setSize(80, 25);
		rank_show = new JButton("순위보기");
		rank_show.setBounds(146, 220, 65, 23);
		next_stage_bnt = new JButton("다음 레벨");
		next_stage_bnt.setSize(80, 25);
		next_stage_bnt.setBounds(146, 220, 65, 23);
		rank_show.setSize(90, 25);
		game_panel.setLayout(null);
		third_num = new JTextField(1);
		third_num.setBounds(96, 190, 20, 21);
		game_panel.add(third_num);
		second_num = new JTextField(1);
		second_num.setBounds(71, 190, 20, 21);
		game_panel.add(second_num);
		
		rank_show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				printRank();
			}
		});

		game_panel.add(rank_show);
		game_panel.add(play_bnt);
		first_num = new JTextField(1);
		first_num.setBounds(45, 190, 20, 21);
		forth_num = new JTextField(1);
		forth_num.setBounds(120, 190, 20, 21);
		fiveth_num = new JTextField(1);
		fiveth_num.setBounds(150, 190, 20, 21);
		game_panel.add(first_num);
		show_Gameresult = new JTextArea(5, 10);
		show_Gameresult.setEditable(false);
		show_Gameresult.setEnabled(false);
		show_Gameresult.setBounds(27, 50, 224, 121);
		pre_bnt.setBounds(180, 10, 80, 25);
		pre_bnt.setSize(95, 25);
		game_panel.add(pre_bnt);
		game_panel.add(show_Gameresult);
		//dialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				first_num.requestFocusInWindow();
			}
		});

		play_bnt.addMouseListener(new mouseeventListener());
		next_stage_bnt.addMouseListener(new mouseeventListener());
		first_num.addKeyListener(new keyeventListener());
		second_num.addKeyListener(new keyeventListener());
		third_num.addKeyListener(new keyeventListener());
		forth_num.addKeyListener(new keyeventListener());
		fiveth_num.addKeyListener(new keyeventListener());
		pre_bnt.addMouseListener(new mouseeventListener());

		getContentPane().add(main);
		setVisible(true);
		setSize(300, 320);
		game_panel.setBackground(Color.ORANGE);
		main.setBackground(Color.ORANGE);
	}

	void printResult() {
		String result;
		System.out.println("확인: " + game.getYou().size());
		if (game.getStrike() == (game.getLevel() + 2)) {

			result = "축하합니다. " + game.getCnt() + " 번 만에 맞혔어요!\n" + "걸린 시간은 " + ((game.endtime - game.starttime) / 1000)
					+ "초 입니다.";

			game.setLevel(game.getLevel() + 1);
			game_panel.add(next_stage_bnt);
			show_Gameresult.setText(result);

		} else if (game.getStrike() == 0 && game.getBall() == 0) {
			result = "아웃" + "\n" + "플레이어 : ";
			show_Gameresult.setText(result);
			for (int i = 0; i < game.getYou().size(); i++) {
				result += game.getYou().get(i) + "  ";
				show_Gameresult.setText(show_Gameresult.getText() + result);
			}
		} else {
			result = "스트라이크 : " + game.getStrike() + " 번 볼 : " + game.getBall() + " 번 \n";
			show_Gameresult.setText(result + "플레이어 : ");
			for (int i = 0; i < game.getYou().size(); i++) {
				result = game.getYou().get(i) + "  ";
				show_Gameresult.setText(show_Gameresult.getText() + result);
			}
		}
	}

	void enterStage() {
		String[] str = null;
		if (game.getLevel() == 1) {
			str = new String[3];
			str[0] = first_num.getText();
			str[1] = second_num.getText();
			str[2] = third_num.getText();

		} else if (game.getLevel() == 2) {
			game_panel.add(forth_num);
			str = new String[4];
			str[0] = first_num.getText();
			str[1] = second_num.getText();
			str[2] = third_num.getText();
			str[3] = forth_num.getText();

		} else if (game.getLevel() == 3) {
			game_panel.add(fiveth_num);
			str = new String[5];
			str[0] = first_num.getText();
			str[1] = second_num.getText();
			str[2] = third_num.getText();
			str[3] = forth_num.getText();
			str[4] = fiveth_num.getText();

		}
		if (checkStr(str)) {
			game.InputNumber(str);
			PLAYBALL();
		}
		first_num.setText("");
		second_num.setText("");
		third_num.setText("");
		forth_num.setText("");
		fiveth_num.setText("");
	}

	boolean checkStr(String[] str) {

		boolean flag = true; // 플래그 변수

		joption_panel.setOptions(new Object[] { ok_dialog });
		dialog = joption_panel.createDialog(this, "입력오류");

		for (int i = 0; i < str.length; i++) { // 레벨에 따라 숫자의 갯수가 다르다.
			// 각 시도마다 플래그 변수를 초기화한다.
			if (Integer.parseInt(str[i]) > 9) { // 범위에 벗어난 수를 입력받지 못하게 한다.
				joption_panel.setMessage("범위보다 큽니다. 다시 입력하세요. \n범위 : 1~9");
				dialog.setVisible(true);
				flag = false;
			} else if (Integer.parseInt(str[i]) < 1) {
				joption_panel.setMessage("범위보다 큽니다. 다시 입력하세요. \n범위 : 1~9");
				dialog.setVisible(true);
				flag = false;
			}

			for (int j = i + 1; j < str.length; j++) {
				if (str[i].equals(str[j])) {
					joption_panel.setMessage("같은 수는 입력 할 수 없습니다.");
					dialog.setVisible(true);
					flag = false;
				}
			}
		}
		if (flag)
			return true;
		else
			return false;

	}

	void PLAYBALL() {

		game.setComNumber();
		game.starttime = System.currentTimeMillis();
		game.setStrike(0);
		game.setBall(0);
		game.matchBall();
		game.setCnt(game.getCnt() + 1);
		if (game.getStrike() == (game.getLevel() + 2)) {
			game.endtime = System.currentTimeMillis();
			game.time = (int) (game.endtime - game.starttime);
			printResult();

			game.setLevel(game.getLevel() + 1);
			game.setCnt(0);
		} else {
			printResult();
		}

		game.getYou().removeAll(game.getYou());
	}

	void printRank() {
		
		show_Gameresult.setText("");		
		show_Gameresult.setText("------------------------순위----------------------------------\n");
		show_Gameresult.setText(show_Gameresult.getText()+"이름" + "\t" + "레벨" + "\t" + "걸린시간" + "\t");
		
		for (int i = 0; i <game.getPlayer_list().size(); i++) {
			show_Gameresult.setText(show_Gameresult.getText()+game.getPlayer_list().get(i).name+ "\t");	
			show_Gameresult.setText(show_Gameresult.getText()+game.getPlayer_list().get(i).level+ "\t");
			show_Gameresult.setText(show_Gameresult.getText()+game.getPlayer_list().get(i).time+ "\t");
		}
	}

	void mainInIt() {
		this.game = new StartBaseBallGame(namefield.getText());
		getContentPane().add(game_panel);
		main.setVisible(false);
		game_panel.setVisible(true);
	}

	class mouseeventListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource().equals(pre_bnt)) {
				getContentPane().add(main);
				game_panel.setVisible(false);
				main.setVisible(true);
			} else if (e.getSource().equals(play_bnt)) {
				enterStage();
			}
		};
	}

	class keyeventListener extends JFrame implements KeyListener {

		JOptionPane joption;
		JButton optionpane_bnt;
		JDialog dialog;

		public keyeventListener() {

			this.joption = new JOptionPane();
			this.optionpane_bnt = new JButton("확인");
			dialog=new JDialog();
			//dialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			optionpane_bnt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getSource().equals(first_num) || e.getSource().equals(second_num) || e.getSource().equals(third_num)
					|| e.getSource().equals(forth_num) || e.getSource().equals(fiveth_num)) {
				if ((e.getKeyCode() < 48 || e.getKeyCode() > 57) && (e.getKeyCode() != 8)) {

					joption.setMessage("숫자만 입력하세요");
					joption.setOptions(new Object[] { optionpane_bnt });
					dialog = joption.createDialog(this, "입력오류");
					dialog.setVisible(true);
					first_num.setText("");
					second_num.setText("");
					third_num.setText("");
					forth_num.setText("");
					fiveth_num.setText("");
					first_num.requestFocus();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (e.getSource().equals(ok_bnt)) {
					mainInIt();
				} else if (e.getSource().equals(exit)) {
					System.exit(0);
				} else if (e.getSource().equals(first_num)) {
					second_num.requestFocus();
				} else if (e.getSource().equals(third_num)) {
					forth_num.requestFocus();
				} else if (e.getSource().equals(fiveth_num)) {
					play_bnt.requestFocus();
				} else {
					ok_bnt.requestFocus();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
		
		}
	}
}
