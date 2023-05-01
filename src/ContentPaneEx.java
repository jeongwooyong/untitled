import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ContentPaneEx extends JFrame {

    private JTextField textField;

    public ContentPaneEx() {
        setTitle("명지 주문 시스템");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setBackground(Color.GRAY);
        contentPane.setLayout(new FlowLayout());

        textField = new JTextField(20);
        contentPane.add(textField);

        JButton btn = new JButton("전송");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText(); // 버튼이 클릭되면 textField에 입력된 텍스트 가져오기
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
                textField.setText(""); // textField 초기화
            }

        });
        contentPane.add(btn);

        setVisible(true);
    }

    private void sendTextToServer(String text) {
        try {
            while(true) {
                Socket socket = new Socket("localhost", 9999); // 서버에 연결
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(text + "\n"); // 서버로 메시지 보내기
                out.flush();
                socket.close(); // 연결 종료
            }
        } catch (IOException ex) {
            System.err.println("입출력 오류가 발생했습니다.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ContentPaneEx();
    }


}