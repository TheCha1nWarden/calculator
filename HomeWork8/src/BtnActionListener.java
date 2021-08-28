import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtnActionListener implements ActionListener {
    private JTextField textField;
    private StringBuilder sb;
    public BtnActionListener(JTextField textField) {
        this.textField = textField;
        sb = new StringBuilder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        sb.append(textField.getText()).append(btn.getText());
        textField.setText(sb.toString());

        sb.setLength(0);
    }
}
