import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.function.*;

public class Grafico {
    StudioFunzione sf;

    public void visualizza() {
        JFrame frame = new JFrame(); // creazione finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton b = new JButton("Visualizza");

        JTextField t0 = new JTextField(40); // 40 indica lo spazio
        t0.setText("-pow(x, 3) + 6*pow(x, 2) - 11 * x+6");
        JTextField t1 = new JTextField(40);
        t1.setText("-3*pow(x, 2) + 12*x - 11");

        JTextField t2 = new JTextField(40);
        t2.setText("-6.0*x + 12.0");
        JTextField t3 = new JTextField(40);
        t3.setText("-6.0");
        // creazione panel che conterrà le caselle di testo
        JPanel contenitore = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints(); // vincoli

        // si stabilisce come disporre gli elementi
        contenitore.setLayout(layout);
        /////////////////////////////////////////////
        JLabel l0 = new JLabel("f(x) = ");
        lim.gridx = 0;
        lim.gridy = 0;
        lim.insets.top = 0;
        lim.insets.bottom = 0;
        lim.insets.left = 0;
        lim.insets.right = 0;
        lim.fill = GridBagConstraints.NONE;
        lim.anchor = GridBagConstraints.EAST;

        layout.setConstraints(l0, lim);
        contenitore.add(l0, lim);
        //// fine aggiunta etichetta
        lim.gridwidth = 2;

        lim.gridx = 1;
        lim.gridy = 0;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(t0, lim);
        contenitore.add(t0);

        /////////////////////////////////////////////
        JLabel l1 = new JLabel("f'(x) = ");
        lim.gridx = 0;
        lim.gridy = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(l1, lim);
        contenitore.add(l1, lim);
        lim.gridwidth = 2;

        lim.gridx = 1;
        lim.gridy = 1;
        lim.anchor = GridBagConstraints.WEST;

        layout.setConstraints(t1, lim);
        contenitore.add(t1);

        JLabel l2 = new JLabel("f''(x) = ");
        lim.gridx = 0;
        lim.gridy = 2;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;

        layout.setConstraints(l2, lim);
        contenitore.add(l2, lim);
        lim.gridwidth = 2;

        lim.gridx = 1;
        lim.gridy = 2;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(t2, lim);
        contenitore.add(t2);

        JLabel l3 = new JLabel("f'''(x) = ");
        lim.gridx = 0;
        lim.gridy = 3;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(l3, lim);
        contenitore.add(l3, lim);
        lim.gridwidth = 2;

        lim.gridx = 1;
        lim.gridy = 3;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(t3, lim);
        contenitore.add(t3);

        ///////////////////////////
        JTextField ta = new JTextField(5);
        ta.setText("0.7");
        JLabel la = new JLabel("a = ");
        lim.gridx = 4;
        lim.gridy = 0;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(la, lim);
        contenitore.add(la, lim);
        lim.gridwidth = 1;

        lim.gridx = 5;
        lim.gridy = 0;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(ta, lim);
        contenitore.add(ta);

        JTextField tb = new JTextField(5);
        tb.setText("3.4");
        JLabel lb = new JLabel("b = ");
        lim.gridx = 4;
        lim.gridy = 1;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(lb, lim);
        contenitore.add(lb, lim);
        lim.gridwidth = 1;

        lim.gridx = 5;
        lim.gridy = 1;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(tb, lim);
        contenitore.add(tb);

        JTextField th = new JTextField(5);
        th.setText("0.0001");
        JLabel lh = new JLabel("h = ");
        lim.gridx = 4;
        lim.gridy = 2;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(lh, lim);
        contenitore.add(lh, lim);
        lim.gridwidth = 1;

        lim.gridx = 5;
        lim.gridy = 2;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(th, lim);
        contenitore.add(th);

        JTextField te = new JTextField(5);
        te.setText("1.0e-6");
        JLabel le = new JLabel("e = ");
        lim.gridx = 4;
        lim.gridy = 3;
        lim.gridwidth = 1;
        lim.anchor = GridBagConstraints.EAST;
        layout.setConstraints(le, lim);
        contenitore.add(le, lim);
        lim.gridwidth = 1;

        lim.gridx = 5;
        lim.gridy = 3;
        lim.anchor = GridBagConstraints.WEST;
        layout.setConstraints(te, lim);
        contenitore.add(te);

        lim.gridwidth = 1;
        lim.fill = GridBagConstraints.NONE;
        lim.anchor = GridBagConstraints.NORTH;
        lim.gridx = 6;
        lim.gridy = 3;
        layout.setConstraints(b, lim);
        contenitore.add(b);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Expression e0 = new ExpressionBuilder(t0.getText()).variable("x").build();
                Expression e1 = new ExpressionBuilder(t1.getText()).variables("x").build();
                Expression e2 = new ExpressionBuilder(t2.getText()).variables("x").build();
                Expression e3 = new ExpressionBuilder(t3.getText()).variables("x").build();
                System.out.println(t0.getText());
                Function<Double, Double> f = x -> e0.setVariable("x", x).evaluate(); // -Math.pow(x, 3) + 6*Math.pow(x,
                                                                                     // 2) - 11 * x+6;//simbolo
                // della funzione lambda
                Function<Double, Double> f1 = (var x) -> e1.setVariable("x", x).evaluate();// -3*Math.pow(x, 2) + 12*x -
                                                                                           // 11;
                Function<Double, Double> f2 = x -> e2.setVariable("x", x).evaluate();// -6*x + 12;
                Function<Double, Double> f3 = x -> e3.setVariable("x", x).evaluate();// -6.0;

                if (sf != null)
                    frame.remove(sf);
                sf = new StudioFunzione(t0.getText(), Double.parseDouble(ta.getText()),
                        Double.parseDouble(tb.getText()), Double.parseDouble(th.getText()),
                        Double.parseDouble(te.getText()), f, f1, f2, f3).scansioneFx();
                frame.add(sf, BorderLayout.CENTER);
                frame.pack();
                frame.setSize(800, 800);
            }
        });

        frame.add(contenitore, BorderLayout.NORTH);

        frame.setSize(800, 200);
        frame.setVisible(true);

    }
}