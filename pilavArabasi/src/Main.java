import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class Main {

    private JCheckBox cbTavuk, cbNohut, cbTursu, cbKavurma;
    private JLabel lblDesc, lblPrice;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new Main().createAndShow());
    }

    private void createAndShow() {
        JFrame f = new JFrame("Pilav Arabası");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(920, 560));

        JPanel root = new GradientPanel(new Color(0xF7F9FC), new Color(0xEEF2F7));
        root.setLayout(new BorderLayout());
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        root.add(buildHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(16, 0, 0, 0);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0; gc.weighty = 1;

        gc.gridx = 0; gc.weightx = 0.55;
        content.add(buildOptionsCard(), gc);

        gc.gridx = 1; gc.weightx = 0.45;
        content.add(buildSummaryCard(), gc);

        root.add(content, BorderLayout.CENTER);

        f.setContentPane(root);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        updateSummary();
    }

    private JComponent buildHeader() {
        RoundedPanel header = new RoundedPanel(20, true);
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(255,255,255,180));
        header.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel banner = new JLabel();
        banner.setOpaque(false);
        banner.setIcon(loadScaled("/images/header.jpg", 1200, 160));
        banner.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        JLabel title = new JLabel("Pilav Arabası");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setForeground(new Color(0x111827));
        JLabel subtitle = new JLabel("Malzemeleri seç → anlık fiyat ve açıklama → siparişi tamamla");
        subtitle.setForeground(new Color(0x5A5F6A));

        JPanel textBox = new JPanel();
        textBox.setOpaque(false);
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.add(title);
        textBox.add(Box.createVerticalStrut(4));
        textBox.add(subtitle);

        JLabel bowl = new JLabel(loadScaled("/images/pilav.png", 56, 56));
        if (bowl.getIcon() == null) bowl.setText("🍚");

        bar.add(textBox, BorderLayout.WEST);
        bar.add(bowl, BorderLayout.EAST);
        bar.setBorder(new EmptyBorder(10, 12, 4, 12));

        header.add(banner, BorderLayout.CENTER);
        header.add(bar, BorderLayout.SOUTH);
        return header;
    }

    private JComponent buildOptionsCard() {
        RoundedPanel card = new RoundedPanel(20, false);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(16, 16, 16, 16));
        card.setLayout(new BorderLayout(0, 12));

        JLabel h = new JLabel("Malzeme Seçimi");
        h.setFont(h.getFont().deriveFont(Font.BOLD, 18f));
        h.setBorder(new EmptyBorder(0, 0, 6, 0));

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new GridLayout(0, 1, 8, 8));

        cbTavuk   = buildNiceCheck("Tavuk (+7 TL)",   "/images/tavuk.png");
        cbNohut   = buildNiceCheck("Nohut (+5 TL)",   "/images/nohut.jpg");
        cbTursu   = buildNiceCheck("Turşu (+3 TL)",   "/images/tursu.jpg");
        cbKavurma = buildNiceCheck("Kavurma (+10 TL)","/images/kavurma.jpg");

        ActionListener listener = e -> updateSummary();
        cbTavuk.addActionListener(listener);
        cbNohut.addActionListener(listener);
        cbTursu.addActionListener(listener);
        cbKavurma.addActionListener(listener);

        list.add(cbTavuk);
        list.add(cbNohut);
        list.add(cbTursu);
        list.add(cbKavurma);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        actions.setOpaque(false);
        JButton btnClear = new JButton("Sıfırla");
        JButton btnOrder = new JButton("Siparişi Tamamla");
        stylizeSecondary(btnClear);
        stylizePrimary(btnOrder);

        btnClear.addActionListener(e -> {
            cbTavuk.setSelected(false);
            cbNohut.setSelected(false);
            cbTursu.setSelected(false);
            cbKavurma.setSelected(false);
            updateSummary();
        });

        btnOrder.addActionListener(e -> {
            Pilav p = buildPilav();
            JOptionPane.showMessageDialog(card,
                    "Siparişiniz: " + p.getDescription() + "\nToplam: " +
                            String.format("%.2f TL", p.getCost()),
                    "Sipariş Alındı", JOptionPane.INFORMATION_MESSAGE);
        });

        actions.add(btnClear);
        actions.add(btnOrder);

        card.add(h, BorderLayout.NORTH);
        card.add(list, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }

    private JComponent buildSummaryCard() {
        RoundedPanel card = new RoundedPanel(20, false);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(16, 16, 16, 16));
        card.setLayout(new BorderLayout(0, 12));

        JLabel h = new JLabel("Sipariş Özeti");
        h.setFont(h.getFont().deriveFont(Font.BOLD, 18f));
        h.setBorder(new EmptyBorder(0, 0, 6, 0));

        lblDesc  = new JLabel("Seçim: -");
        lblPrice = new JLabel("Toplam: 0,00 TL");
        lblPrice.setFont(lblPrice.getFont().deriveFont(Font.BOLD, 20f));

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(lblDesc);
        body.add(Box.createVerticalStrut(6));
        body.add(lblPrice);

        JLabel bowl = new JLabel(loadScaled("/images/pilav.jpg", 100, 100));
        bowl.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel mid = new JPanel(new BorderLayout());
        mid.setOpaque(false);
        mid.add(body, BorderLayout.CENTER);
        mid.add(bowl, BorderLayout.EAST);

        card.add(h, BorderLayout.NORTH);
        card.add(mid, BorderLayout.CENTER);
        return card;
    }

    private JCheckBox buildNiceCheck(String text, String iconPath) {
        JCheckBox cb = new JCheckBox(text);
        cb.setOpaque(false);
        cb.setFont(cb.getFont().deriveFont(15f));
        Icon ic = loadScaled(iconPath, 28, 28);
        if (ic != null) cb.setIcon(ic);
        cb.setFocusPainted(false);
        return cb;
    }

    private void stylizePrimary(JButton b) {
        Color bg      = new Color(0x2563EB);
        Color bgHover = new Color(0x1D4ED8);
        Color bgPress = new Color(0x1E40AF);

        b.setFont(b.getFont().deriveFont(Font.BOLD, 15f));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorder(new RoundedBorder(12));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bgHover); }
            @Override public void mouseExited (java.awt.event.MouseEvent e) { b.setBackground(bg); }
            @Override public void mousePressed(java.awt.event.MouseEvent e) { b.setBackground(bgPress); }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                b.setBackground(b.getBounds().contains(e.getPoint()) ? bgHover : bg);
            }
        });
    }

    private void stylizeSecondary(JButton b) {
        b.setFont(b.getFont().deriveFont(Font.PLAIN, 14f));
        b.setBackground(Color.WHITE);
        b.setForeground(new Color(0x1F2937));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorder(new RoundedBorder(12));
        b.setFocusPainted(false);
    }

    private void updateSummary() {
        Pilav p = buildPilav();
        lblDesc.setText("Seçim: " + p.getDescription());
        lblPrice.setText("Toplam: " + String.format("%.2f TL", p.getCost()));
    }

    private Pilav buildPilav() {
        Pilav p = new SadePilav();
        if (cbTavuk.isSelected())   p = new TavukDecorator(p);
        if (cbNohut.isSelected())   p = new NohutDecorator(p);
        if (cbTursu.isSelected())   p = new TursuDecorator(p);
        if (cbKavurma.isSelected()) p = new KavurmaDecorator(p);
        return p;
    }

    private Icon loadScaled(String path, int w, int h) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) return null;
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    /** Yumuşak köşeli panel */
    static class RoundedPanel extends JPanel {
        private final int arc;
        private final boolean drawShadow;
        RoundedPanel(int arc, boolean shadow) {
            this.arc = arc;
            this.drawShadow = shadow;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = 0, y = 0, w = getWidth(), h = getHeight();
            if (drawShadow) {
                g2.setColor(new Color(0,0,0,30));
                g2.fillRoundRect(x+4,y+6,w-8,h-8, arc+4, arc+4);
            }
            g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(x, y, w-1, h-1, arc, arc));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Yuvarlak köşeli buton kenarlığı */
    static class RoundedBorder extends javax.swing.border.LineBorder {
        RoundedBorder(int radius) { super(new Color(0,0,0,0), 0, true); }
        @Override public Insets getBorderInsets(Component c) { return new Insets(8, 12, 8, 12); }
        @Override public boolean isBorderOpaque() { return false; }
    }

    /** Arkaplanda yumuşak gradient */
    static class GradientPanel extends JPanel {
        private final Color c1, c2;
        GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
