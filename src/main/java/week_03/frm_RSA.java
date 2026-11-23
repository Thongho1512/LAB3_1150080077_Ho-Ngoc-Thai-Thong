package week_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_RSA extends JFrame {

    private final RSACipher rsa = new RSACipher();

    private JComboBox<String> cbo_bits;
    private JButton btn_generate;
    private JLabel lbl_keyinfo;
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JLabel lbl_status;

    public frm_RSA() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("RSA Cipher");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("MẬT MÃ RSA (OAEP - SHA-256)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Key size, combo, generate, keyinfo
        JPanel keySizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        keySizePanel.add(new JLabel("Key size:"));
        cbo_bits = new JComboBox<>(new String[] {"2048", "3072"});
        cbo_bits.setPreferredSize(new Dimension(110, 28));
        keySizePanel.add(cbo_bits);

        btn_generate = new JButton("Generate Keys");
        btn_generate.setPreferredSize(new Dimension(140, 30));
        btn_generate.addActionListener(evt -> btn_generateActionPerformed(evt));
        keySizePanel.add(btn_generate);

        lbl_keyinfo = new JLabel("Chưa có khóa.");
        lbl_keyinfo.setPreferredSize(new Dimension(330, 22));
        keySizePanel.add(lbl_keyinfo);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(keySizePanel, gbc);

        // Row 1: Plaintext label
        gbc.gridy = 1; gbc.gridwidth = 2;
        centerPanel.add(new JLabel("Plaintext:"), gbc);

        // Row 2: Plaintext text area
        txt_plaintext = new JTextArea(2, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        JScrollPane scrollPlain = new JScrollPane(txt_plaintext);
        scrollPlain.setPreferredSize(new Dimension(600, 60));
        gbc.gridy = 2;
        centerPanel.add(scrollPlain, gbc);

        // Row 3: Ciphertext label
        gbc.gridy = 3;
        centerPanel.add(new JLabel("Ciphertext:"), gbc);

        // Row 4: Ciphertext text area
        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        scrollCipher.setPreferredSize(new Dimension(600, 110));
        gbc.gridy = 4;
        centerPanel.add(scrollCipher, gbc);

        // Row 5: Encrypt / Decrypt Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setPreferredSize(new Dimension(110, 30));
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed(evt));
        actionPanel.add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setPreferredSize(new Dimension(110, 30));
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed(evt));
        actionPanel.add(btn_decrypt);

        gbc.gridy = 5;
        centerPanel.add(actionPanel, gbc);

        // Row 6: Status
        lbl_status = new JLabel("Bấm Generate Keys để tạo cặp khóa.", SwingConstants.CENTER);
        lbl_status.setPreferredSize(new Dimension(600, 36));
        gbc.gridy = 6;
        centerPanel.add(lbl_status, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        pack();
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        int bits = Integer.parseInt((String) cbo_bits.getSelectedItem());
        try {
            long start = System.currentTimeMillis();
            rsa.generateKeys(bits);
            long ms = System.currentTimeMillis() - start;
            lbl_keyinfo.setText("RSA-" + bits + ": tạo khóa " + ms + " ms, tối đa " + rsa.maxPlaintextBytes() + " byte");
            lbl_status.setText("Đã tạo cặp khóa. Khóa công khai dùng để mã hóa, khóa riêng để giải mã.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
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
            String ciphertext = rsa.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa RSA (" + ciphertext.length() + " ký tự Base64). "
                    + HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), false);
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
            String plaintext = rsa.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã RSA. " + HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã RSA THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                    "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_RSA().setVisible(true));
    }
}
