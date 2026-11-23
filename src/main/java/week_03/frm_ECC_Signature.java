package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_ECC_Signature extends JFrame {

    private final ECCSignature ecdsa = new ECCSignature();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JTextArea txt_message;
    private JTextArea txt_signature;
    private JButton btn_sign;
    private JButton btn_verify;
    private JLabel lbl_result;
    private JLabel lbl_status;

    public frm_ECC_Signature() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("ECDSA Signature");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("CHỮ KÝ SỐ ECDSA (SHA256withECDSA)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Public key
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(new JLabel("Public key:"), gbc);
        txt_public = new JTextArea(2, 40);
        txt_public.setLineWrap(true);
        txt_public.setWrapStyleWord(true);
        txt_public.setEditable(false);
        JScrollPane scrollPub = new JScrollPane(txt_public);
        scrollPub.setPreferredSize(new Dimension(600, 46));
        gbc.gridy = 1;
        centerPanel.add(scrollPub, gbc);

        // Private key
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Private key:"), gbc);
        txt_private = new JTextArea(2, 40);
        txt_private.setLineWrap(true);
        txt_private.setWrapStyleWord(true);
        txt_private.setEditable(false);
        JScrollPane scrollPri = new JScrollPane(txt_private);
        scrollPri.setPreferredSize(new Dimension(600, 46));
        gbc.gridy = 3;
        centerPanel.add(scrollPri, gbc);

        // Key Buttons
        JPanel keyBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 3));
        btn_generate = new JButton("Generate Keys");
        btn_generate.setPreferredSize(new Dimension(140, 30));
        btn_generate.addActionListener(evt -> btn_generateActionPerformed(evt));
        keyBtnPanel.add(btn_generate);

        btn_savekeys = new JButton("Save Keys");
        btn_savekeys.setPreferredSize(new Dimension(120, 30));
        btn_savekeys.addActionListener(evt -> btn_savekeysActionPerformed(evt));
        keyBtnPanel.add(btn_savekeys);

        btn_loadkeys = new JButton("Load Keys");
        btn_loadkeys.setPreferredSize(new Dimension(120, 30));
        btn_loadkeys.addActionListener(evt -> btn_loadkeysActionPerformed(evt));
        keyBtnPanel.add(btn_loadkeys);

        gbc.gridy = 4;
        centerPanel.add(keyBtnPanel, gbc);

        // Message
        gbc.gridy = 5;
        centerPanel.add(new JLabel("Message:"), gbc);
        txt_message = new JTextArea(2, 40);
        txt_message.setLineWrap(true);
        txt_message.setWrapStyleWord(true);
        JScrollPane scrollMsg = new JScrollPane(txt_message);
        scrollMsg.setPreferredSize(new Dimension(600, 56));
        gbc.gridy = 6;
        centerPanel.add(scrollMsg, gbc);

        // Signature
        gbc.gridy = 7;
        centerPanel.add(new JLabel("Signature:"), gbc);
        txt_signature = new JTextArea(2, 40);
        txt_signature.setLineWrap(true);
        txt_signature.setWrapStyleWord(true);
        JScrollPane scrollSig = new JScrollPane(txt_signature);
        scrollSig.setPreferredSize(new Dimension(600, 56));
        gbc.gridy = 8;
        centerPanel.add(scrollSig, gbc);

        // Action Buttons & Result
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));
        btn_sign = new JButton("Sign");
        btn_sign.setPreferredSize(new Dimension(110, 30));
        btn_sign.addActionListener(evt -> btn_signActionPerformed(evt));
        actionPanel.add(btn_sign);

        btn_verify = new JButton("Verify");
        btn_verify.setPreferredSize(new Dimension(110, 30));
        btn_verify.addActionListener(evt -> btn_verifyActionPerformed(evt));
        actionPanel.add(btn_verify);

        lbl_result = new JLabel("", SwingConstants.LEFT);
        lbl_result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl_result.setPreferredSize(new Dimension(300, 38));
        actionPanel.add(lbl_result);

        gbc.gridy = 9;
        centerPanel.add(actionPanel, gbc);

        // Status
        lbl_status = new JLabel("Bấm Generate Keys hoặc Load Keys.", SwingConstants.CENTER);
        lbl_status.setPreferredSize(new Dimension(600, 36));
        gbc.gridy = 10;
        centerPanel.add(lbl_status, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        pack();
    }

    private void showKeys() {
        txt_public.setText(ecdsa.getPublicKeyBase64());
        txt_private.setText(ecdsa.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecdsa.generateKeys();
            showKeys();
            lbl_status.setText("Đã tạo cặp khóa mới. Bản mã/chữ ký cũ sẽ không dùng được với khóa mới.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        if (txt_public.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có khóa để lưu.");
            return;
        }
        String content = CryptoSupport.toKeyFile(txt_public.getText(), txt_private.getText());
        String path = FileTextIO.saveString(this, content, "ecdsa_keys.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu khóa vào: " + path + " (giữ bí mật dòng PRIVATE).");
        }
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] keys = CryptoSupport.fromKeyFile(content);
            ecdsa.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_signActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            txt_signature.setText(ecdsa.sign(message));
            lbl_result.setText("");
            lbl_status.setText("Đã ký bằng KHÓA RIÊNG. " + HistoryDao.tryInsert("ECDSA", "SIGN", message.length(), true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ký thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            boolean ok = ecdsa.verify(message, txt_signature.getText());
            lbl_result.setText(ok ? "VALID" : "INVALID");
            lbl_result.setForeground(ok ? new Color(0, 128, 0) : Color.RED);
            lbl_status.setText("Đã xác minh bằng KHÓA CÔNG KHAI. " + HistoryDao.tryInsert("ECDSA", "VERIFY", message.length(), ok));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xác minh thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_ECC_Signature().setVisible(true));
    }
}
