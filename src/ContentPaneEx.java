

import javax.swing.*;
import java.awt.*;

public class ContentPaneEx extends JFrame {
    ContentPaneEx() {
        setTitle("명지 주문 시스템");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setBackground(Color.GRAY);
        contentPane.setLayout(new FlowLayout());

        contentPane.add(new JButton("OK"));
        contentPane.add(new JButton("cancle"));
        contentPane.add(new JButton("ignore"));

        setVisible(true);
    }

    public static void main(String[] args) {
        new ContentPaneEx();
    }
}