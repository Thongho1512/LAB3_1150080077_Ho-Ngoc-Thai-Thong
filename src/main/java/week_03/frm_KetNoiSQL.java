package week_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_KetNoiSQL extends JFrame {

    private JTextField txt_server;
    private JTextField txt_port;
    private JTextField txt_database;
    private JTextField txt_user;
    private JPasswordField txt_password;
    private JButton btn_test;
    private JButton btn_history;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("KẾT NỐI MICROSOFT SQL SERVER", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Server
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Server:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txt_server = new JTextField("127.0.0.1", 20);
        txt_server.setPreferredSize(new Dimension(320, 28));
        formPanel.add(txt_server, gbc);

        // Row 1: Port
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Port:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txt_port = new JTextField("14333", 20);
        txt_port.setPreferredSize(new Dimension(120, 28));
        formPanel.add(txt_port, gbc);

        // Row 2: Database
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Database:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txt_database = new JTextField("LAB3_MaHoaKhoaCongKhai", 20);
        txt_database.setPreferredSize(new Dimension(320, 28));
        formPanel.add(txt_database, gbc);

        // Row 3: User
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("User:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txt_user = new JTextField("lab3_user", 20);
        txt_user.setPreferredSize(new Dimension(320, 28));
        formPanel.add(txt_user, gbc);

        // Row 4: Password
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txt_password = new JPasswordField("", 20);
        txt_password.setPreferredSize(new Dimension(320, 28));
        formPanel.add(txt_password, gbc);

        // Row 5: Buttons
        JPanel btnPanel = new JPanel();
        btn_test = new JButton("Test Connection");
        btn_test.setPreferredSize(new Dimension(150, 30));
        btn_test.addActionListener(evt -> btn_testActionPerformed(evt));
        btnPanel.add(btn_test);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setPreferredSize(new Dimension(130, 30));
        btn_history.addActionListener(evt -> btn_historyActionPerformed(evt));
        btnPanel.add(btn_history);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // Row 6: Status
        lbl_status = new JLabel("Chưa kết nối.", SwingConstants.CENTER);
        lbl_status.setPreferredSize(new Dimension(450, 40));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(lbl_status, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        pack();
    }

    private void btn_testActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int port = Integer.parseInt(txt_port.getText().trim());
            DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                    txt_user.getText().trim(), txt_password.getPassword());
            lbl_status.setText("<html>" + DbConfig.test() + "</html>");
        } catch (Exception ex) {
            DbConfig.clear();
            lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_KetNoiSQL().setVisible(true));
    }
}
