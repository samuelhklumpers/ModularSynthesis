package synthesis;

import javax.swing.*;
import java.awt.*;

public class ModularSynthesis {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Modular Synthesis");

        setupFrame(frame);
    }

    private static void setupFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        fillFrame(frame);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static void fillFrame(JFrame frame) {
        JPanel nodePanel = new JPanel();

        frame.add(nodePanel, BorderLayout.CENTER);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());

        JPanel nodeBar = new JPanel();
        nodeBar.setLayout(new GridLayout(0, 1));

        JButton identityButton = new JButton("identity");

        nodeBar.add(identityButton);

        sideBar.add(nodeBar, BorderLayout.CENTER);

        frame.add(sideBar, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save as...");

        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
}