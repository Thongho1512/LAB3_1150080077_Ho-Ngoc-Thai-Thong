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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_Menu extends JFrame {

    private JButton btn_rsa;
    private JButton btn_ecc;
    private JButton btn_rsaaes;
    private JButton btn_signature;
    private JButton btn_sql;
    private JButton btn_history;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 3 - Mã hóa khóa công khai");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel headerPanel = new JPanel(new BorderLayout(5, 8));
        JLabel lblTitle = new JLabel("LAB 3 - MÃ HÓA KHÓA CÔNG KHAI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(lblTitle, BorderLayout.NORTH);

        JLabel lblSubtitle = new JLabel("Chọn một bài để mở form tương ứng (bài 3.1 chạy RSADemo.java bằng Run File):", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 2 rows x 3 columns of buttons
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        Dimension btnDim = new Dimension(200, 35);

        // Row 1
        btn_rsa = new JButton("3.2 RSA (Swing)");
        btn_rsa.setPreferredSize(btnDim);
        btn_rsa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_rsa.addActionListener(evt -> btn_rsaActionPerformed(evt));
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(btn_rsa, gbc);

        btn_ecc = new JButton("3.3 ECC");
        btn_ecc.setPreferredSize(btnDim);
        btn_ecc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_ecc.addActionListener(evt -> btn_eccActionPerformed(evt));
        gbc.gridx = 1; gbc.gridy = 0;
        gridPanel.add(btn_ecc, gbc);

        btn_rsaaes = new JButton("3.4 RSA + AES");
        btn_rsaaes.setPreferredSize(btnDim);
        btn_rsaaes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_rsaaes.addActionListener(evt -> btn_rsaaesActionPerformed(evt));
        gbc.gridx = 2; gbc.gridy = 0;
        gridPanel.add(btn_rsaaes, gbc);

        // Row 2
        btn_signature = new JButton("3.5 Chữ ký ECDSA");
        btn_signature.setPreferredSize(btnDim);
        btn_signature.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_signature.addActionListener(evt -> btn_signatureActionPerformed(evt));
        gbc.gridx = 0; gbc.gridy = 1;
        gridPanel.add(btn_signature, gbc);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setPreferredSize(btnDim);
        btn_sql.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_sql.addActionListener(evt -> btn_sqlActionPerformed(evt));
        gbc.gridx = 1; gbc.gridy = 1;
        gridPanel.add(btn_sql, gbc);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setPreferredSize(btnDim);
        btn_history.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_history.addActionListener(evt -> btn_historyActionPerformed(evt));
        gbc.gridx = 2; gbc.gridy = 1;
        gridPanel.add(btn_history, gbc);

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        pack();
    }

    private void btn_rsaActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA().setVisible(true);
    }

    private void btn_eccActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC().setVisible(true);
    }

    private void btn_rsaaesActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA_AES().setVisible(true);
    }

    private void btn_signatureActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC_Signature().setVisible(true);
    }

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_KetNoiSQL().setVisible(true);
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}
