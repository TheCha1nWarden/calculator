import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Calculator extends JFrame {
    private JTextField textField;
    private JFrame frame;
    private Character[] arrayOper = {'-', '+', '*', '/'};
    private byte select;
    private JButton btnSqrt;
    private JButton btnLeftBracket;
    private JButton btnRightBracket;
    private JButton btnDivByMod;
    public Calculator(String title) {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setBounds(50,50,300,500);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(createTop(), BorderLayout.NORTH);
        frame.add(ctreateBotoom(), BorderLayout.CENTER);
        frame.add(createOperBtn(), BorderLayout.EAST);
        frame.add(craateSelectBtn(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    private JPanel createTop() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));

        textField = new JTextField();
        panel.add(textField);
        textField.setText("Please select the mode at the bottom of the app");
        textField.setEditable(false);

        return panel;

    }

    private JPanel ctreateBotoom() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,4));

        BtnActionListener btnActionListener = new BtnActionListener(textField);


        for (int i = 0; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.addActionListener(btnActionListener);
            panel.add(btn);
        }



        JButton btnClear = new JButton("C");
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });
        panel.add(btnClear);
        JButton btnEquals = new JButton("=");
        btnEquals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calculation();
                } catch (ScriptException a) {
                    textField.setText("Error");
                }
            }
        });
        panel.add(btnEquals);
        return panel;
    }

    private JPanel createOperBtn() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8,1));
        BtnActionListener btnActionListener = new BtnActionListener(textField);
        for (int i = 0; i < arrayOper.length; i++) {
            JButton btn = new JButton(String.valueOf(arrayOper[i]));
            btn.addActionListener(btnActionListener);
            panel.add(btn);
        }
        btnSqrt = new JButton("sqrt");
        btnSqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + "^(1/2)");
            }
        });
        panel.add(btnSqrt);

        btnDivByMod = new JButton("%");
        btnDivByMod.addActionListener(btnActionListener);
        panel.add(btnDivByMod);

        btnLeftBracket = new JButton("(");
        btnLeftBracket.addActionListener(btnActionListener);
        panel.add(btnLeftBracket);

        btnRightBracket = new JButton(")");
        btnRightBracket.addActionListener(btnActionListener);
        panel.add(btnRightBracket);

        return panel;
    }

    private JPanel craateSelectBtn() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));
        JRadioButton btn1 = new JRadioButton("Simple mode");
        JRadioButton btn2 = new JRadioButton("Expression mode");
        JRadioButton btn3 = new JRadioButton("ScriptEngine mode");
        JRadioButton btn4 = new JRadioButton("Expression with precedence mode");
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton btn = (JRadioButton) e.getSource();
                if (btn.isSelected()) {
                    textField.setText("");
                    btnDivByMod.setVisible(false);
                    btnSqrt.setVisible(true);
                    btnLeftBracket.setVisible(false);
                    btnRightBracket.setVisible(false);
                    select = 0;
                    btn2.setSelected(false);
                    btn3.setSelected(false);
                    btn4.setSelected(false);
                } else {

                }
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton btn = (JRadioButton) e.getSource();
                if (btn.isSelected()) {
                    textField.setText("");
                    btnDivByMod.setVisible(false);
                    btnSqrt.setVisible(false);
                    btnLeftBracket.setVisible(false);
                    btnRightBracket.setVisible(false);
                    select = 1;
                    btn1.setSelected(false);
                    btn3.setSelected(false);
                    btn4.setSelected(false);
                } else {

                }
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton btn = (JRadioButton) e.getSource();
                if (btn.isSelected()) {
                    textField.setText("");
                    btnDivByMod.setText("%");
                    btnDivByMod.setVisible(true);
                    btnSqrt.setVisible(false);
                    btnLeftBracket.setVisible(true);
                    btnRightBracket.setVisible(true);
                    select = 2;
                    btn2.setSelected(false);
                    btn1.setSelected(false);
                    btn4.setSelected(false);
                } else {

                }
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                btnDivByMod.setText(".");
                btnSqrt.setVisible(false);
                btnDivByMod.setVisible(true);
                btnLeftBracket.setVisible(false);
                btnRightBracket.setVisible(false);
                select = 3;

                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(false);
            }
        });


        return panel;
    }

    public void calculation() throws ScriptException {
        String tmp = textField.getText();
        char[] array = tmp.toCharArray();

        switch (select) {
            case 0 :
                String var1 = "";
                String var2 = "";
                String operation = "";
                boolean flagOper = false;
                boolean flagSqrt = false;
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < arrayOper.length; j++) {
                        if (array[i] == arrayOper[j]) {
                            operation += arrayOper[j];
                            flagOper = true;
                            break;
                        } else {
                            if (array[i] == '^') {
                                flagSqrt = true;
                                break;
                            }
                        }
                    }
                    if (flagSqrt) {
                        break;
                    }
                    if (!flagOper) {
                        if (Character.isDigit(array[i])) {
                            var1 += array[i];
                        }
                    } else {
                        if (Character.isDigit(array[i])) {
                            var2 += array[i];
                        }
                    }
                }
                int varF = Integer.parseInt(var1);
                if (!flagSqrt) {
                    int varS = Integer.parseInt(var2);
                    double calcul = 1.0;
                    switch (operation) {
                        case "+":
                            calcul = varF + varS;
                            break;
                        case "-":
                            calcul = varF - varS;
                            break;
                        case "*":
                            calcul = varF * varS;
                            break;
                        case "/":
                            calcul = varF * 1.0 / varS;
                            break;
                    }
                    textField.setText(textField.getText() + "=" + calcul);
                } else {
                    textField.setText(textField.getText() + "=" + Math.sqrt(varF));
                }
                break;
            case 1 :
                String tmpDigit = "";
                char tmpOper = ' ';
                Stack<Double> stack = new Stack<>();
                for (int i = 0; i < array.length; i++) {
                    if (Character.isDigit(array[i]) && i != array.length - 1) {
                        tmpDigit += array[i];
                    } else {
                        if (i == array.length - 1) {
                            tmpDigit += array[i];
                        }
                        if (stack.isEmpty()) {
                            stack.push(Double.parseDouble(tmpDigit));
                            tmpDigit = "";
                            tmpOper = array[i];
                        } else {
                            switch (tmpOper) {
                                case '+' :
                                    double tmpSum = stack.pop() + Double.parseDouble(tmpDigit);
                                    stack.push(tmpSum);
                                    tmpDigit = "";
                                    tmpOper = array[i];
                                    break;
                                case '-' :
                                    double tmpSub = stack.pop() - Double.parseDouble(tmpDigit);
                                    stack.push(tmpSub);
                                    tmpDigit = "";
                                    tmpOper = array[i];
                                    break;
                                case '*' :
                                    double tmpMul = stack.pop() * Double.parseDouble(tmpDigit);
                                    stack.push(tmpMul);
                                    tmpDigit = "";
                                    tmpOper = array[i];
                                    break;
                                case '/' :
                                    double tmpDiv = stack.pop() / Integer.parseInt(tmpDigit);
                                    stack.push(tmpDiv);
                                    tmpDigit = "";
                                    tmpOper = array[i];
                                    break;
                            }
                        }
                    }
                }
                textField.setText(textField.getText() + "=" + String.valueOf(stack.pop()));
                break;
            case 2 :
                ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
                ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
                Object result = scriptEngine.eval(textField.getText());
                textField.setText(textField.getText() + "=" + result);
                break;
            case 3 :
                String str = textField.getText();
                String num1 = "";
                String num2 = "";
                ArrayList<Character> priority = new ArrayList<>(Arrays.asList(arrayOper));
                while (!priority.isEmpty()) {
                    char[] arrayChars = str.toCharArray();
                    boolean flagNegativeNumber1 = false;
                    boolean flagNegativeNumber2 = false;
                    boolean flagOneNegativeNumber = false;
                    boolean flagCheckForAddPlus = false;
                    int counter = 0;
                    for (int i = 0; i < arrayChars.length; i++) {
                        if (priority.get(priority.size()-1).equals(arrayChars[i])) {
                            counter++;
                            if (arrayChars[i] == '-' && i == 0) {
                                flagOneNegativeNumber = true;
                                continue;
                            }
                            int tmpI = i - 1;
                            while (Character.isDigit(arrayChars[tmpI]) || arrayChars[tmpI] == '.' || arrayChars[tmpI] == '-') {
                                if (arrayChars[tmpI] == '-') {
                                    flagNegativeNumber1 = true;
                                    if (tmpI != 0) {
                                        if (Character.isDigit(arrayChars[tmpI - 1])) {
                                            flagCheckForAddPlus = true;
                                        }
                                    }
                                    break;
                                }
                                num1 = arrayChars[tmpI] + num1;
                                tmpI--;
                                if (tmpI == -1) {
                                    break;
                                }
                            }
                            tmpI = i + 1;
                            if (arrayChars[tmpI] == '-') {
                                flagNegativeNumber2 = true;
                                tmpI++;
                            }
                            while (Character.isDigit(arrayChars[tmpI]) || arrayChars[tmpI] == '.') {
                                num2 += arrayChars[tmpI];
                                tmpI++;
                                if (tmpI == arrayChars.length) {
                                    break;
                                }
                            }

                            String tmpStr = "";
                            switch (arrayChars[i]) {
                                case '+' :
                                    if (flagNegativeNumber1) {
                                        num1 = "-" + num1;
                                    }
                                    if (flagNegativeNumber2) {
                                        num2 = "-" + num2;
                                    }
                                    tmpStr = String.valueOf(Double.parseDouble(num1) + Double.parseDouble(num2));
                                    break;
                                case '-' :
                                    if (flagNegativeNumber1) {
                                        num1 = "-" + num1;
                                    }
                                    if (flagNegativeNumber2) {
                                        num2 = "-" + num2;
                                    }
                                    tmpStr = String.valueOf(Double.parseDouble(num1) - Double.parseDouble(num2));
                                    break;
                                case '*' :
                                    if (flagNegativeNumber1) {
                                        num1 = "-" + num1;
                                    }
                                    if (flagNegativeNumber2) {
                                        num2 = "-" + num2;
                                    }
                                    tmpStr = String.valueOf(Double.parseDouble(num1) * Double.parseDouble(num2));
                                    break;
                                case '/' :
                                    if (flagNegativeNumber1) {
                                        num1 = "-" + num1;
                                    }
                                    if (flagNegativeNumber2) {
                                        num2 = "-" + num2;
                                    }
                                    tmpStr = String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));
                                    break;
                            }

                            if (flagNegativeNumber1 && Double.parseDouble(tmpStr) > 0 && str.indexOf(num1) > 0 && flagCheckForAddPlus) {
                                    str = str.replace(num1 + arrayChars[i] + num2, "+" + tmpStr);
                            } else {
                                str = str.replace(num1 + arrayChars[i] + num2, tmpStr);
                            }

                            num1 = "";
                            num2 = "";
                        }
                    }
                    if (counter == 0 || (counter == 1 && flagOneNegativeNumber)) {
                        priority.remove(priority.size() - 1);
                    }
                }
                textField.setText(textField.getText() + "=" + str);
        }
    }

    public Calculator() {
        this("Calculator v1.0");
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
