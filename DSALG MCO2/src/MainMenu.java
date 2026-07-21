import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    private JLabel title, title2;
    private JButton loadmaze, startsim, exit;
    private JPanel panel, textPanel;
    private JPanel masterPanel;

    public MainMenu() {
        super("Main Menu");
        this.setSize(600, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title = new JLabel("<html><center>UP, UP,<br>DOWN, DOWN,<br>LEFT, RIGHT,<br>LEFT, RIGHT</center></html>");
        title.setFont(new Font("Nintendo NES Font", Font.PLAIN, 48));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);

        masterPanel = new JPanel();
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

        textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        title.setHorizontalAlignment(SwingConstants.CENTER);
        textPanel.add(title, gbc);
        textPanel.setPreferredSize(new Dimension(600, 400));

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 10, 0));
        panel.setPreferredSize(new Dimension(600, 100));

        loadmaze = new JButton("<html><center>LOAD<br>MAZE</center></html>");
        loadmaze.setFont(new Font("Nintendo NES Font", Font.PLAIN, 15));
        loadmaze.setHorizontalAlignment(SwingConstants.CENTER);
        loadmaze.addActionListener(this);

        startsim = new JButton("<html><center>START<br>SIMULATION</center></html>");
        startsim.setFont(new Font("Nintendo NES Font", Font.PLAIN, 15));
        startsim.setHorizontalAlignment(SwingConstants.CENTER);
        startsim.addActionListener(this);

        exit = new JButton("<html><center>EXIT<br>PROGRAM</center></html>");
        exit.setFont(new Font("Nintendo NES Font", Font.PLAIN, 15));
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.addActionListener(this);

        panel.add(loadmaze);
        panel.add(startsim);
        panel.add(exit);

        masterPanel.add(textPanel);
        masterPanel.add(panel);
        masterPanel.setVisible(true);
        this.add(masterPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadmaze) {

        } else if (e.getSource() == startsim) {

        } else if (e.getSource() == exit) {
            System.exit(0);
        }
    }
}
