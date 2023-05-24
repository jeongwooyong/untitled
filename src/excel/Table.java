package excel;

import example.TabbedTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class orderEX extends Thread {
    private static int num;

    public void run() {
        new TabbedTable();
    }

    public void setCount(int num) {
        this.num =num;
    }
    public static int getCount(){
        return num;
    }
}

public class Table extends JFrame implements ActionListener {
    private orderEX th1;
    private orderEX th2;
    private orderEX th3;
    private orderEX th4;
    private orderEX th5;
    private int num;


    public Table() {
        setTitle("Table Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 40));

        JButton tb1 = new JButton("Table 1");
        JButton tb2 = new JButton("Table 2");
        JButton tb3 = new JButton("Table 3");
        JButton tb4 = new JButton("Table 4");
        JButton tb5 = new JButton("Table 5");

        tb1.addActionListener(this);
        tb2.addActionListener(this);
        tb3.addActionListener(this);
        tb4.addActionListener(this);
        tb5.addActionListener(this);

        contentPane.add(tb1);
        contentPane.add(tb2);
        contentPane.add(tb3);
        contentPane.add(tb4);
        contentPane.add(tb5);

        setSize(350, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Table();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (button.getText().equals("Table 1")) {
            if (th1 == null) {
                th1 = new orderEX();
                th1.setCount(1);
                th1.start();
            }
        } else if (button.getText().equals("Table 2")) {
            if (th2 == null) {
                th2 = new orderEX();
                th2.setCount(2);
                th2.start();
            }
        } else if (button.getText().equals("Table 3")) {
            if (th3 == null) {
                th3 = new orderEX();
                th3.setCount(3);
                th3.start();
            }
        } else if (button.getText().equals("Table 4")) {
            if (th4 == null) {
                th4 = new orderEX();
                th4.setCount(4);
                th4.start();
            }
        } else if (button.getText().equals("Table 5")) {
            if (th5 == null) {
                th5 = new orderEX();
                th5.setCount(5);
                th5.start();
            }
        }
    }
}

