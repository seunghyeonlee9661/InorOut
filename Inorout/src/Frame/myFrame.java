/*
 * 작성일자 : 2023-10-24
 * 작성자 : 이승현
 * 설명 : 자바 GUI를 활용하여 도형을 그리고 포인트가 도형의 내외부 어디에 존재하는지 확인하는 프로그램
 * 
 * 클래스 이름 : myFrame
 * 클래스 역할 : 프로그램이 실행되는 메인 프레임 구성
 * 클래스 내부 구성
 * 1. 클래스 함수 : 클래스 내부에서 작동할 클래스 함수 정의
 * 2. 프레임 생성자 : 프레임 설정, 기능에 필요한 마우스 이벤트, 각 판넬에 대한 설정과 프레임에 추가
 * 3. 메인 패널 세팅 : 메인 프레임 크기 및 색깔
 * 4. 사이드 패널 세팅 : 사이드 프레임 크기 및 색깔, 연산 및 초기화 버튼에 대한 이벤트
 * 5. 커스텀 패널 : 패널에 도형 및 포인트를 그려지도록 커스터마이징
 */

package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class myFrame extends JFrame {
	// 1. 클래스 함수
	MyPanel mainPanel;
	JPanel sidePanel;
	JRadioButton drawButton, pointButton;
	JButton runButton, resetButton;
	ButtonGroup groupRd;
	JLabel infoLabel;
	static int mod;
	ArrayList<Point> points = new ArrayList<>();
	Point myPoint;

	// 2. 프레임 생성자
	public myFrame() {
		// 프레임 레이아웃 설정
		mod = 1;
		this.setTitle("INorOut?");
		// 닫기 버튼 기능 지정 - 프로그램 종료
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(455, 510);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		// 좌표계 판넬
		mainPanel = mainPanelsetting();
		// 마우스 클릭 이벤트 설정
		mainPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 현재 모드 = 1 : 그리기 모드
				// 포인트 입력에 따라 도형 생성
				if (mod == 1) {
					// 포인트 배열이 비어있으면 클릭된 좌표를 포인트로 추가
					if (points.isEmpty()) {
						points.add(new Point(e.getX(), e.getY()));
					} else {
						// 포인트가 선택되었는지 확인
						boolean selected = false;
						for (Point point : points) {
							// 좌표값을 받아서 포인트가 선택되었는지 검사
							if (point.isSelected(e.getX(), e.getY())) {
								selected = true;
								// 선택 되었다면 포인트 제거
								points.remove(point);
								break;
							}
						}
						// 포인트가 선택되지 않았으면
						if (!selected) {
							// 포인트 추가
							points.add(new Point(e.getX(), e.getY()));
						}
					}
					// 현재 모드 = 0 : 점찍기 모드
					// 내부 외부를 확인할 포인트 위치 설정
				} else {
					// 확인 포인트 지정
					myPoint = new Point(e.getX(), e.getY());
				}
				// 화면을 새로 출력
				repaint();
			}
		});

		// 마우스 드래그 이벤트 설정
		mainPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// 포인트가 선택되었으면
				for (Point point : points) {
					if (point.isSelected(e.getX(), e.getY())) {
						// 포인트 좌표를 새로 설정
						point.reset(e.getX(), e.getY());
					}
				}
				// 화면 새로 출력
				repaint();
			}
		});

		// 제어판 판넬 설정
		sidePanel = sidePanelsetting();

		// 판넬을 프레임에 저장
		this.add(mainPanel);
		this.add(sidePanel);
	}

	// 3. 메인 패널 세팅
	public MyPanel mainPanelsetting() {
		MyPanel panel = new MyPanel();
		panel.setBorder(new LineBorder(Color.black, 1, false));
		panel.setBounds(10, 40, 420, 420);
		panel.setBackground(Color.WHITE);
		return panel;
	}

	// 4. 사이드 패널 세팅
	public JPanel sidePanelsetting() {
		JPanel panel = new JPanel();

		// 라디오 버튼 지정
		drawButton = new JRadioButton("Draw");
		pointButton = new JRadioButton("Point");
		drawButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mod = 1;
			}
		});
		pointButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mod = 2;
			}
		});
		drawButton.setSelected(true);
		groupRd = new ButtonGroup();
		groupRd.add(drawButton);
		groupRd.add(pointButton);
		panel.add(drawButton);
		panel.add(pointButton);

		// 확인 포인트의 내외부 검사 기능 실행
		runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 도형이 부적절하게 되어있으면 함수 종료
				if (points.size() < 3) {
					infoLabel.setText("부적절한 도형!");
					return;
				}
				// 포인트를 설정하지 않았을 경우 함수 종료
				if (myPoint == null) {
					infoLabel.setText("포인트 필요!");
					return;
				}
				// 교차점의 개수 세기
				int cross = 0;
				// 포인트를 반복하면서 확인
				for (int i = 0; i < points.size(); i++) {
					int j;
					if (i == points.size() - 1)
						j = 0;
					else
						j = i + 1;
					// 포인트의 경우 점이 선분을 지나가는지 연산을 통해 확인
					if (points.get(i).getY() > myPoint.getY() != points.get(j).getY() > myPoint.getY()) {
						int jx = points.get(j).getX();
						int jy = points.get(j).getY();
						int ix = points.get(i).getX();
						int iy = points.get(i).getY();
						// 포인트가 선분을 지나가면 교차점 개수 추가
						if (myPoint.getX() < ((jx - ix) * (myPoint.getY() - iy) / (jy - iy) + ix)) {
							cross++;
						}
					}
				}
				// 교차점의 개수가 홀수일 경우 내부
				if (cross % 2 > 0)
					infoLabel.setText("Inside!");
				// 교차점의 개수가 짝수일 경우 외부
				else
					infoLabel.setText("Outside!");
			}
		});

		// 도형과 포인트를 제거하고 리셋
		resetButton = new JButton("RESET");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawButton.setSelected(true);
				mod = 1;
				points.clear();
				myPoint = null;
				repaint();
			}
		});
		panel.add(runButton);
		panel.add(resetButton);

		// 확인 결과를 출력하는 라벨 설정
		infoLabel = new JLabel("");
		infoLabel.setPreferredSize(new Dimension(155, 30));
		infoLabel.setBorder(new LineBorder(Color.black, 1, false));
		infoLabel.setOpaque(true);
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setFont(infoLabel.getFont().deriveFont(15.0f));
		panel.add(infoLabel);

		// 사이드 판넬 레이아웃
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		panel.setLayout(fl);
		panel.setBounds(5, 0, 420, 50);
		return panel;
	}

	// 5. 커스텀 패널
	// 도형이 그려질 수 있도록 JPanel을 커스터마이징
	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// 도형 포인트 그리기
			g.setColor(Color.BLUE);
			for (int i = 0; i < points.size(); i++) {
				g.fillOval(points.get(i).getX() - 5, points.get(i).getY() - 5, 10, 10);
			}

			// 도형 선분 그리기
			g.setColor(Color.black);
			for (int i = 1; i < points.size(); i++) {
				g.drawLine(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(),
						points.get(i).getY());
			}

			// 도형에 점이 2개 이상일 경우 마지막 점과 처음 점을 연결
			if (points.size() > 2) {
				g.drawLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(),
						points.get(0).getX(), points.get(0).getY());
			}

			// 확인 포인트 그리기
			g.setColor(Color.yellow);
			if (myPoint != null) {
				g.fillOval(myPoint.getX() - 5, myPoint.getY() - 5, 10, 10);
			}
		}
	}
}
