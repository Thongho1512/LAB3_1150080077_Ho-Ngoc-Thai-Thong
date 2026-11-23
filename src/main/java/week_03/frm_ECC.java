package week_03;

import java.awt.BorderLayout;
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

public class frm_ECC extends JFrame {

    private final ECCCipher ecc = new ECCCipher();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JLabel lbl_status;

    public frm_ECC() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("ECC Cipher");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("MẬT MÃ ĐƯỜNG CONG ELLIPTIC (ECC - secp256r1)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Public key label & area
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

        // Private key label & area
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

        // Plaintext label & area
        gbc.gridy = 5;
        centerPanel.add(new JLabel("Plaintext:"), gbc);
        txt_plaintext = new JTextArea(2, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        JScrollPane scrollPlain = new JScrollPane(txt_plaintext);
        scrollPlain.setPreferredSize(new Dimension(600, 56));
        gbc.gridy = 6;
        centerPanel.add(scrollPlain, gbc);

        // Ciphertext label & area
        gbc.gridy = 7;
        centerPanel.add(new JLabel("Ciphertext:"), gbc);
        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        scrollCipher.setPreferredSize(new Dimension(600, 80));
        gbc.gridy = 8;
        centerPanel.add(scrollCipher, gbc);

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));
        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setPreferredSize(new Dimension(110, 30));
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed(evt));
        actionPanel.add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setPreferredSize(new Dimension(110, 30));
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed(evt));
        actionPanel.add(btn_decrypt);

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
        txt_public.setText(ecc.getPublicKeyBase64());
        txt_private.setText(ecc.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecc.generateKeys();
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
        String path = FileTextIO.saveString(this, content, "ecc_keys.txt");
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
            ecc.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = ecc.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa ECC (" + ciphertext.length() + " ký tự Base64). "
                    + HistoryDao.tryInsert("ECC", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("ECC", "ENCRYPT", plaintext.length(), false);
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = ecc.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã ECC. " + HistoryDao.tryInsert("ECC", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("ECC", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã ECC THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                    "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_ECC().setVisible(true));
    }
}
