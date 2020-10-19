import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteProduct {
    DeleteProduct(JTable tab) {
        JFrame warn = new JFrame("Warning!!!");

        warn.setSize(new Dimension(300, 200));
        //jf1.setMaximumSize(new Dimension(500,200));
        warn.setResizable(false);
        warn.setLocation(610, 340);
        JPanel jp2 = new JPanel();
        jp2.setSize(new Dimension(300, 200));
        warn.add(jp2);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jp2.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        gridBagLayout.setConstraints(jp2, constraints);


        JLabel jTextField2 = new JLabel();
        jTextField2.setText("<html>  &#8195 &#8195 &#8195 &#8195 &#8195 Warning!  <br>The following actions will delete the raw from the database!</html>");

        jTextField2.setFont(new Font("Arial", Font.BOLD, 15));
        //jTextField2.setMinimumSize(new Dimension(250,30));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 0, 0, 0);


        jp2.add(jTextField2, constraints);
        JButton jButtonw1 = new JButton("OK");
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        jButtonw1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Accessor ac = Accessor.getInstance();
                    if (ac != null)
                        System.out.println("Connection successful");
                    else {
                        System.out.println("Connection faild");
                        System.exit(0);
                    }
                    ProductTable a = (ProductTable) tab.getModel();
                    String com = (a).getRowName(tab.getSelectedRow());


                    if (!ac.deleteProduct(com)) {

                        JOptionPane.showMessageDialog(new JFrame(), "CAN NOT BE DELETED");


                    } else {

                        ArrayList<String> dataProduct = ac.getProductsName();
                        HashMap<String,ArrayList<String>> prodComposition = ac.getComposition(dataProduct);

                        tab.setModel(new ProductTable(dataProduct,prodComposition));
                        ((ProductTable)tab.getModel()).fireTableDataChanged();

                    }
                    warn.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        jp2.add(jButtonw1, constraints);
        JButton jButtonw2 = new JButton("UNDO");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        jButtonw2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                warn.dispose();
            }
        });
        jp2.add(jButtonw2, constraints);

        warn.setVisible(true);

    }
}

