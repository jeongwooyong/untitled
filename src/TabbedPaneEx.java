import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.lang.*;


public class TabbedPaneEx extends JFrame {
    // 교과서 497p에 있는 JTabbedPane 사용. 인기메뉴/ 면류/ 밥류 등등..
    private JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
    // 인기메뉴에 해당하는 Panel
    private JPanel popular = new JPanel();
    private JPanel[][] p;
    private JButton[][] plus, minus, basket;
    private JLabel[][] l;
    public TabbedPaneEx() {
        setTitle("명지 주문 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //버튼 개수와 그리드 레이아웃 크기 설정
        int numCols = 3;
        int numRows = 3;
        final int[] count = {0};
        //GridLayout 으로 설정. 3,3 5는 픽셀단위
        popular.setLayout(new GridLayout(numRows, numCols, 5, 5));


        //버튼 배열 생성
        p = new JPanel[numRows][numCols];
        l = new JLabel[numRows][numCols];
        plus = new JButton[numRows][numCols];
        minus = new JButton[numRows][numCols];
        basket = new JButton[numRows][numCols];

        //가격
        //String[] price = {"10000원","9000원","8000원","10000원","9000원","8000원", "10000원","9000원","8000원"};
        HashMap<String,String> price = new HashMap<String,String>();
        //<key,value>
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
                l[i][j] = new JLabel(key);
                plus[i][j] = new JButton("+");
                //액션 리스너에서 i,j 값을 입력받기 위해서 따로 변수 설정을 하였음
                //액션 리스너에서 변수를 사용하려면 final변수여야만 가능함.
                //이유는 final 변수를 public static과 선언하면 프로그램 전체에서 사용 가능하기 때문이다
                //교과서 233p
                //
                //

                plus[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 해당 버튼이 속한 JPanel에서 가격 정보 가져오기
                        count[0]++;
                        String text = "주문한 메뉴: " + price.get(key) +" "+ count[0] + " 개";
                        //소켓을 연결하기는 했지만 다음 추가는 잘 안됨.
                        //장바구니에서 추가해서 소켓을 보내야하지만
                        //아직은 되지 않음
                        try {
                            Socket socket = new Socket("localhost", 9999); // 서버에 연결하는 소켓 생성
                            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 서버로의 출력 스트림
                            out.write(text + "\n"); // 서버로 보냄
                            out.flush();
                        } catch (UnknownHostException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                });
                /*
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
                  }
                 */


                    minus[i][j] = new JButton("-");
                basket[i][j] = new JButton("장바구니");
                popular.add(p[i][j]);
                p[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                p[i][j].setLayout(new FlowLayout(FlowLayout.CENTER));
                p[i][j].add(plus[i][j]);
                p[i][j].add(l[i][j]);
                p[i][j].add(minus[i][j]);
                p[i][j].add(basket[i][j]);
            }
        }
        //면류
        JPanel myon = new JPanel();
        myon.add(new JButton("메뉴 1"));
        myon.add(new JButton("메뉴 2"));
        myon.add(new JButton("메뉴 3"));
        myon.add(new JButton("메뉴 4"));
        myon.add(new JButton("메뉴 5"));
        myon.add(new JButton("메뉴 6"));
        //밥류
        JPanel rice = new JPanel();
        rice.add(new JButton("메뉴 1"));
        rice.add(new JButton("메뉴 2"));
        rice.add(new JButton("메뉴 3"));
        //주문내역
        JPanel order = new JPanel();
        order.add(new JButton("주문내역"));
        //계산서
        JPanel bill = new JPanel();
        bill.add(new JButton("주문내역"));
        //장바구니
        JPanel buy = new JPanel();
        JTextArea text = new JTextArea();
        buy.add(text);

        // private JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
        // BorderLayout의 왼쪽에 추가.
        pane.addTab("인기 메뉴", popular);
        pane.addTab("면 류", myon);
        pane.addTab("밥 류",rice);
        pane.addTab("주문내역", order);
        pane.addTab("계산서", bill);
        pane.add("장바구니", buy);
        add(pane, BorderLayout.WEST);




        setSize(1000, 600);
        setVisible(true);
    }



    public static void main(String[] args) {
        new TabbedPaneEx();
    }
}