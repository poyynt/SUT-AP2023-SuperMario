package supermario.views;

import supermario.logic.BuyCharacterResult;
import supermario.logic.ShoppingHandler;
import supermario.logic.SpriteLoader;
import supermario.models.GameCharacter;

import javax.swing.*;
import java.awt.*;

public class StoreMenuCharacterPanel extends JPanel {

    private final JLabel errorLabel = new JLabel();
    private final JButton buyButton = new JButton();
    private final GameCharacter forCharacter;
    @SuppressWarnings("FieldCanBeLocal")
    private final JPanel leftPanel = new JPanel();
    @SuppressWarnings("FieldCanBeLocal")
    private final JPanel rightPanel = new JPanel();
    public StoreMenuCharacterPanel(GameCharacter c) {
        super();
        forCharacter = c;
        setPreferredSize(new Dimension(320, 240));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel(c.getName());
        nameLabel.setFont(new Font(
                nameLabel.getFont().getName(),
                Font.BOLD,
                20
        ));

        JLabel moveSpeedLabel = new JLabel("Speed: " + c.getMoveSpeed());

        JLabel jumpSpeedLabel = new JLabel("Jump Strength: " + c.getJumpSpeed());

        JLabel shotSpeedLabel = new JLabel("Shooting Power: " + c.getShotSpeed());

        JLabel coinMagnetLabel = new JLabel(c.getCoinRadiusMultiplier() == 1 ? "" : "Has coin magnet.");

        errorLabel.setForeground(Color.RED);

        JLabel priceLabel = new JLabel("Price: " + c.getPrice());

        buyButton.addActionListener(e -> {
            BuyCharacterResult result = ShoppingHandler.buyCharacter(c);
            if (result == BuyCharacterResult.SUCCESS) {
                buyButton.setText("Owned");
                buyButton.setEnabled(false);
            }
            else if (result == BuyCharacterResult.ERROR_NOT_ENOUGH_COINS) {
                errorLabel.setText("Not enough coins.");
            }
        });

        if (ShoppingHandler.characterCanBeBought(c)) {
            buyButton.setText("Buy");
            buyButton.setEnabled(true);
        }
        else {
            buyButton.setText("Owned");
            buyButton.setEnabled(false);
        }

        JLabel spriteLabel = new JLabel(new ImageIcon(SpriteLoader.loadSpriteForCharacter(c, 0)));

        leftPanel.add(spriteLabel);


        rightPanel.add(nameLabel);
        rightPanel.add(moveSpeedLabel);
        rightPanel.add(jumpSpeedLabel);
        rightPanel.add(shotSpeedLabel);
        rightPanel.add(coinMagnetLabel);
        rightPanel.add(errorLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(getWidth(), 8)));
        rightPanel.add(priceLabel);
        rightPanel.add(buyButton);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    public void reset() {
        errorLabel.setText("");
        if (ShoppingHandler.characterCanBeBought(forCharacter)) {
            buyButton.setText("Buy");
            buyButton.setEnabled(true);
        }
        else {
            buyButton.setText("Owned");
            buyButton.setEnabled(false);
        }
    }
}
