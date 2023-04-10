package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.BuyCharacterResult;
import ir.sharif.math.ap2023.supermario.logic.ShoppingHandler;
import ir.sharif.math.ap2023.supermario.models.GameCharacter;

import javax.swing.*;
import java.awt.*;

public class StoreMenuCharacterPanel extends JPanel {

    private JLabel errorLabel = new JLabel();
    private JButton buyButton = new JButton();
    private GameCharacter forCharacter;
    public StoreMenuCharacterPanel(GameCharacter c) {
        super();
        forCharacter = c;
        setPreferredSize(new Dimension(320, 240));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        JLabel nameLabel = new JLabel(c.getName());
        nameLabel.setFont(new Font(
                nameLabel.getFont().getName(),
                Font.BOLD,
                20
        ));

        JLabel moveSpeedLabel = new JLabel("Speed: " + String.valueOf(c.getMoveSpeed()));

        JLabel jumpSpeedLabel = new JLabel("Jump Strength: " + String.valueOf(c.getJumpSpeed()));

        JLabel shotSpeedLabel = new JLabel("Shooting Power: " + String.valueOf(c.getShotSpeed()));

        JLabel coinMagnetLabel = new JLabel(c.getCoinRadiusMultiplier() == 1 ? "" : "Has coin magnet.");

        errorLabel.setForeground(Color.RED);

        JLabel priceLabel = new JLabel("Price: " + String.valueOf(c.getPrice()));

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


        this.add(nameLabel);
        this.add(moveSpeedLabel);
        this.add(jumpSpeedLabel);
        this.add(shotSpeedLabel);
        this.add(coinMagnetLabel);
        this.add(errorLabel);
        this.add(Box.createRigidArea(new Dimension(getWidth(), 20)));
        this.add(priceLabel);
        this.add(buyButton);
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
