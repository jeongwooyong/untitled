package kr.mjc.lgm.morder.exam;

import kr.mjc.lgm.morder.zzz.ChatClient;

import javax.sound.midi.Receiver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.lang.*;



public class TabbedTable extends JFrame implements ActionListener {
    // 교과서 497p에 있는 JTabbedPane 사용. 인기메뉴/ 면류/ 밥류 등등..
    private BufferedReader in = null;
    private BufferedWriter out = null;
    private Socket socket = null;
    private Receiver receiver = null; // JTextArea를 상속받고 Runnable 인터페이스를 구현한 클래스로서 받은 정보를 담는 객체
    private JTextField sender = null; // JTextField 객체로서 보내는 정보를 담는 객체

    private JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
    private JTabbedPane text = new JTabbedPane(JTabbedPane.RIGHT);
    private JTextArea basketText = new JTextArea();
    private JTextArea orderText = new JTextArea();
    private JTextArea billText = new JTextArea();

    // 인기메뉴에 해당하는 Panel
    private JPanel popular = new JPanel();
    private JPanel[][] p;
    private JButton[][] plus, minus, basket;
    private JButton getbasket,canclebasket,total, cancleorder;
    private JLabel[][] lWon,lmenu, count1;
    public int number = orderEX.getCount();
    public TabbedTable() {
        // 타이틀 제목에 테이블 번호를 입력함
        setTitle("Table" + "명지 주문 시스템 테이블 번호 " + number);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        receiver = new Receiver(); // 서버에서 받은 메시지를 출력할 컴퍼넌트
        receiver.setEditable(false); // 편집 불가

        sender = new JTextField();
        sender.addActionListener(this);

        text.add(new JScrollPane(receiver),BorderLayout.CENTER); // 스크롤바를 위해  ScrollPane 이용
        text.add(sender,BorderLayout.SOUTH);

        //버튼 개수와 그리드 레이아웃 크기 설정
        int numCols = 3;
        int numRows = 3;
        final int[] count = {0};
        //GridLayout 으로 설정. 3,3 5는 픽셀단위
        popular.setLayout(new GridLayout(numRows, numCols, 5, 5));

        //버튼 배열 생성
        p = new JPanel[numRows][numCols];
        count1 = new JLabel[numRows][numCols];
        lWon = new JLabel[numRows][numCols];
        lmenu = new JLabel[numRows][numCols];
        plus = new JButton[numRows][numCols];
        minus = new JButton[numRows][numCols];
        basket = new JButton[numRows][numCols];
        getbasket = new JButton("주문하기");
        canclebasket = new JButton("취소");
        total = new JButton("총 금액 표시");
        cancleorder = new JButton("주문취소");


        HashMap<String,String> price = new HashMap<String,String>();
        price.put("10000","제육덮밥");
        price.put("9000", "고추장찌개");
        price.put("8000","새우볶음밥");
        price.put("11000","돌솥비빔밥");
        price.put("7000","순두부찌개");
        price.put("6000","오므라이스");
        price.put("4000","라면");
        price.put("4500","떡만두국");
        price.put("6500","짜장면");
        //교과서 414p 에 있는 Iterator 를 사용함.
        Set<String> keys = price.keySet();
        Iterator<String> it = keys.iterator();


        for(int i=0; i<numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                p[i][j] = new JPanel();
                String key = it.next();
                // 값을 넣기 위해서 Iterator를 사용한 것.
                count1[i][j] = new JLabel("1");
                lWon[i][j] = new JLabel(key );
                lmenu[i][j] = new JLabel(price.get(key));
                plus[i][j] = new JButton("+");
                minus[i][j] = new JButton("-");
                basket[i][j] = new JButton("장바구니");
                popular.add(p[i][j]);
                p[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                p[i][j].setLayout(new FlowLayout(FlowLayout.CENTER));
                p[i][j].add(plus[i][j]);
                p[i][j].add(count1[i][j]);
                p[i][j].add(minus[i][j]);
                p[i][j].add(basket[i][j]);
                p[i][j].add(lWon[i][j]);
                p[i][j].add(lmenu[i][j]);
            }
        }
        // + 누르면 숫자 감소
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                final int row = i; // Capture the value of i in a final variable
                final int col = j; // Capture the value of j in a final variable

                plus[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       String count = count1[row][col].getText();
                       int menucount = Integer.parseInt(count);
                       menucount++;
                       count1[row][col].setText(String.valueOf(menucount));

                    }
                });
            }
        }
        // -  누르면 숫자 감소
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                final int row = i; 
                final int col = j; 

                minus[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String count = count1[row][col].getText();
                        int menucount = Integer.parseInt(count);

                        if(menucount == 0 )
                            menucount =0;
                        else
                            menucount--;
                        count1[row][col].setText(String.valueOf(menucount));

                    }
                });
            }
        }
        // 개수가 0 이하이면 버튼의 리스너가 동작 안하게 하고 싶은데 안됨.
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                final int row = i; // Capture the value of i in a final variable
                final int col = j; // Capture the value of j in a final variable
                String count2 = count1[row][col].getText();
                if (count2 == "0")
                    break;
                else {
                    basket[i][j].addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String menu = lmenu[row][col].getText(); // Use the captured values
                            String won = lWon[row][col].getText();
                            int count = Integer.parseInt(count1[row][col].getText());

                            String output = String.format("%s  %s원  %d 개\n", menu, won, count);
                            basketText.append(output);
                        }
                    });
                }
            }
        }
        //장바구니
        JPanel buy = new JPanel();
        basketText.setText("테이블 번호 " + number + "\n\n");
        buy.add(new JScrollPane(basketText));
        buy.add(getbasket);
        buy.add(canclebasket);
        buy.add(total);

        getbasket.addActionListener(this);

        canclebasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                basketText.setText("테이블 번호 " + number + "\n");
            }
        });

        try {
            setupConnection();
        } catch (IOException e) {
            handleError(e.getMessage());
        }

        Thread th = new Thread(receiver); // 상대로부터 메시지 수신을 위한 스레드 생성
        th.start();

        //주문내역
        JPanel order = new JPanel();
        order.add(new JScrollPane(orderText));
        order.add(cancleorder);

        //주문취소 버튼 누를 시 동작
        cancleorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //주문내역 텍스트필드 비우기
                orderText.setText("");
                //주문내역 소켓으로 취소 보내야함

                //계산서 텍스트필드 비우기
                billText.setText("");
            }
        });

        //계산서
        JPanel bill = new JPanel();
        bill.add(new JScrollPane(billText));

        // private JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
        // BorderLayout의 왼쪽에 추가.
        pane.addTab("인기 메뉴", popular);
        pane.add("장바구니", buy);
        pane.add("주문내역", order);
        pane.add("계산서", bill);
        add(pane, BorderLayout.WEST);
        add(text, BorderLayout.EAST);


        setSize(1200, 600);
        setVisible(true);
    }
    private class Receiver extends JTextArea implements Runnable {
        @Override
        public void run() {
            String msg = null;
            while (true) {
                try {
                    msg = in.readLine(); // 상대로부터 한 행의 문자열 받기
                } catch (IOException e) {
                    handleError(e.getMessage());
                }
                this.append("\n  클라이언트 : " + msg); // 받은 문자열을 JTextArea에 출력
                int pos = this.getText().length();
                this.setCaretPosition(pos); // caret 포지션을 가장 마지막으로 이동
            }
        }
    }
    private static void handleError(String string) {
        System.out.println(string);
        System.exit(1);
    }
    public void actionPerformed(ActionEvent e) { // JTextField에 <Enter> 키 처리
        if (e.getSource() == sender) {
            String msg = sender.getText(); // 텍스트 필드에 사용자가 입력한 문자열
            try {
                out.write(msg+"\n"); // 문자열 전송
                out.flush();

                receiver.append("\n클라이언트 : " + msg); // JTextArea에 출력
                int pos = receiver.getText().length();
                receiver.setCaretPosition(pos); // caret 포지션을 가장 마지막으로 이동
                sender.setText(null); // 입력창의 문자열 지움
            } catch (IOException e1) {
                handleError(e1.getMessage());
            }
        }
        else if (e.getSource() == getbasket) {
            String call = basketText.getText(); // 텍스트 필드에 사용자가 입력한 문자열
            try {
                orderText.setText(call);
                billText.setText(call);
                out.write(call+"\n"); // 문자열 전송
                out.flush();

                sender.setText(null); // 입력창의 문자열 지움
            } catch (IOException e1) {
                handleError(e1.getMessage());
            }
        }
    }

    private void setupConnection() throws IOException {
        socket = new Socket("localhost", 9999); // 클라이언트 소켓 생성
        // System.out.println("연결됨");
        receiver.append("서버에 연결 완료");
        int pos = receiver.getText().length();
        receiver.setCaretPosition(pos); // caret 포지션을 가장 마지막으로 이동

        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 클라이언트로부터의 입력 스트림
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 클라이언트로의 출력 스트림
    }
    public static void main(String[] args) {
        new TabbedTable();
    }
}
