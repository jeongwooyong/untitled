import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TabbedPaneEx extends JFrame {
    public TabbedPaneEx() {
        setTitle("명지 주문 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
        JTabbedPane money = new JTabbedPane(JTabbedPane.RIGHT);
        JPanel popular = new JPanel(new GridLayout(3,3,5,5));
        JButton p1 = new JButton("메뉴 1");
        JButton p2 = new JButton("메뉴 2");
        JButton p3 = new JButton("메뉴 3");
        JButton p4 = new JButton("메뉴 4");
        JButton p5 = new JButton("메뉴 5");
        JButton p6 = new JButton("메뉴 6");
        JButton p7 = new JButton("메뉴 7");
        JButton p8 = new JButton("메뉴 8");
        JButton p9 = new JButton("메뉴 9");


        p1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String text = "제육"; // 버튼이 클릭되면 제육
                try {
                    Socket socket = new Socket("localhost", 9999); // 서버에 연결하는 소켓 생성
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 서버로의 출력 스트림
                    out.write(text + "\n"); // 서버로 보냄
                    out.flush();
                    if (text.equals("끝")) { // "끝"을 입력받으면 소켓을 닫음
                        socket.close();
                    }

                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }

        });
        p2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String text = "짜장면"; // 버튼이 클릭되면 제육
                try {
                    Socket socket = new Socket("localhost", 9999); // 서버에 연결하는 소켓 생성
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 서버로의 출력 스트림
                    out.write(text + "\n"); // 서버로 보냄
                    out.flush();
                    if (text.equals("끝")) { // "끝"을 입력받으면 소켓을 닫음
                        socket.close();
                    }

                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }

        });
        popular.add(p1);
        popular.add(p2);
        popular.add(p3);
        popular.add(p4);
        popular.add(p5);
        popular.add(p6);
        popular.add(p7);
        popular.add(p8);
        popular.add(p9);


        JPanel myon = new JPanel();
        myon.add(new JButton("메뉴 1"));
        myon.add(new JButton("메뉴 2"));
        myon.add(new JButton("메뉴 3"));
        myon.add(new JButton("메뉴 4"));
        myon.add(new JButton("메뉴 5"));
        myon.add(new JButton("메뉴 6"));

        JPanel rice = new JPanel();
        rice.add(new JButton("메뉴 1"));
        rice.add(new JButton("메뉴 2"));
        rice.add(new JButton("메뉴 3"));

        JPanel order = new JPanel();
        order.add(new JButton("주문내역"));

        JPanel bill = new JPanel();
        bill.add(new JButton("주문내역"));

        JPanel buy = new JPanel();
        buy.add(new JButton("주문내역"));


        pane.addTab("인기 메뉴", popular);
        pane.addTab("면 류", myon);
        pane.addTab("밥 류",rice);
        money.addTab("주문내역", order);
        money.addTab("계산서", bill);
        money.add("장바구니", buy);
        add(pane, BorderLayout.WEST);
        add(money, BorderLayout.SOUTH);



        setSize(1000, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TabbedPaneEx();
    }
}