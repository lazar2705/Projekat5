import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private JTextField imeField, zdravljeField, xField, yField;
    private JRadioButton rectBtn, circleBtn;
    private JButton startBtn;
    private JTextArea outputArea;

    public GameGUI() {
        super("Pokretanje igre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        imeField = new JTextField();
        zdravljeField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();

        rectBtn = new JRadioButton("Pravougaoni kolajder");
        circleBtn = new JRadioButton("Kružni kolajder");

        ButtonGroup group = new ButtonGroup();
        group.add(rectBtn);
        group.add(circleBtn);
        rectBtn.setSelected(true);

        inputPanel.add(new JLabel("Ime:"));
        inputPanel.add(imeField);
        inputPanel.add(new JLabel("Početno zdravlje:"));
        inputPanel.add(zdravljeField);
        inputPanel.add(new JLabel("Pozicija X:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Pozicija Y:"));
        inputPanel.add(yField);
        inputPanel.add(rectBtn);
        inputPanel.add(circleBtn);

        startBtn = new JButton("Pokreni igru");
        inputPanel.add(startBtn);

        add(inputPanel, BorderLayout.NORTH);

     
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pokreniIgru();
            }
        });

        setVisible(true);
 
