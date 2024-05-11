import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NovelGameGUI extends JFrame {
    private JPanel mainPanel;
    private JTextArea textArea;
    private JButton nextButton;
    private JButton toggleButton;
    private JButton choiceButton;
    private ImagePanel imagePanel;
    private CharacterPanel characterPanel;

    public NovelGameGUI() {
        setTitle("Novel Game");
        setSize(800, 700); // ボタンをはみ出させるため、縦サイズを700に拡張
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 画像表示パネルの作成
        imagePanel = new ImagePanel("bg001.jpg");
        mainPanel.add(imagePanel, BorderLayout.CENTER); // 中央配置

        // キャラクター表示パネルの作成
        characterPanel = new CharacterPanel("w001.png", imagePanel.getPreferredSize()); // 背景画像パネルのサイズを渡す
        imagePanel.add(characterPanel); // 画像表示パネルに追加

        // テキストエリアの作成
        textArea = new JTextArea();
        textArea.setEditable(false);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.SOUTH);

        // Nextボタンの作成
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // テキストの更新やシーンの遷移などの処理をここに記述
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // 下揃え

        // Toggle Choicesボタンの作成
        toggleButton = new JButton("Toggle Choices");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選択肢パネルの表示/非表示を切り替える処理を記述
            }
        });
        mainPanel.add(toggleButton, BorderLayout.WEST); // 左揃え

        // Choice 1ボタンの作成
        choiceButton = new JButton("Choice 1");
        choiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選択肢がクリックされた時の処理をここに記述
            }
        });
        mainPanel.add(choiceButton, BorderLayout.EAST); // 右揃え

        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NovelGameGUI();
            }
        });
    }
}

class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 画像サイズにパネルを設定
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // 画像を全体に描画
        }
    }
}

class CharacterPanel extends JPanel {
    private BufferedImage characterImage;
    private Dimension preferredSize; // 背景画像パネルのサイズ

    public CharacterPanel(String characterImagePath, Dimension preferredSize) {
        try {
            characterImage = ImageIO.read(new File(characterImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.preferredSize = preferredSize; // 背景画像パネルのサイズをセット
        // キャラクター画像のサイズにパネルを設定
        setPreferredSize(new Dimension(characterImage.getWidth(), characterImage.getHeight()));
        // パネルの背景を透明に設定
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (characterImage != null) {
            // キャラクター画像の背景を透過する
            Graphics2D g2d = (Graphics2D) g.create();
            int x = (getWidth() - characterImage.getWidth())/2;
            int y = getHeight() - characterImage.getHeight();
            
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // 透明度を設定
            g2d.drawImage(characterImage, x, y+70, this); // 画像をそのまま描画
            g2d.dispose();
        }
    }
    @Override
    public Dimension getPreferredSize() {
        // 背景画像パネルのサイズに合わせる
        return preferredSize;
    }
}

