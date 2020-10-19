import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing .*;
import java.awt .*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ProductInsert {


    public DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    JComboBox jTextField1;
    JFrame jFrame;
    JPanel jp1;
    String name;
    JButton jButton;
    JButton jButton2;
    HashSet<String> arr = new HashSet<>();
    ProductInsert(String title,JTable tab){
        try {
            Accessor ac = Accessor.getInstance();
            if (ac != null)
                System.out.println("Connection successful");
            else {
                System.out.println("Connection faild");
                System.exit(0);
            }






        jFrame = new JFrame("Добавление продукции");
        jFrame.setSize(new Dimension(520, 200));
        jFrame.setResizable(false);
        jp1 = new JPanel();
        jFrame.setLocation(610, 340);
        jFrame.add(jp1);
        GridBagLayout gridBagLayout = new GridBagLayout();

        jp1.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        gridBagLayout.setConstraints(jp1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(0, 200, 0, 0);
        JLabel jLabel1 = new JLabel("Название cырья");
        jp1.add(jLabel1, constraints);
        jTextField1 = new JComboBox();
        defaultComboBoxModel1 = new DefaultComboBoxModel();
        ArrayList<String> componentsNames = ac.getComponentsName();

        defaultComboBoxModel1.addAll(componentsNames);
        jTextField1.setModel(defaultComboBoxModel1);
        jTextField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                 name = (String)box.getSelectedItem();
            }
        });


        constraints.gridx = 2;
        constraints.gridy = 1;
        //constraints.gridwidth=GridBagConstraints.REMAINDER;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 200, 0, 200);
        jp1.add(jTextField1, constraints);

        jButton = new JButton("Добавить еще");
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 0, 0, 0);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name != "") {
                    System.out.println(name);
                    arr.add(name);


                    JOptionPane.showMessageDialog(new JFrame(), "ADDED!");
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(), "CAN NOT BE ADDED");
                }

            }

        });

        jp1.add(jButton, constraints);

        jButton2 = new JButton("Создать");
        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        jButton2.addActionListener(new ActionListener() {
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


                    if (!arr.isEmpty() ) {


                        if (!ac.addProduct(arr,title)) {
                            JFrame errorFrame = new JFrame("ERROR!");
                            JOptionPane.showMessageDialog(errorFrame, "CAN NOT BE ADDED");
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "CREATED!");
                            ArrayList<String> dataProduct = ac.getProductsName();
                            HashMap<String,ArrayList<String>> prodComposition = ac.getComposition(dataProduct);

                            tab.setModel(new ProductTable(dataProduct,prodComposition));
                            ((ProductTable)tab.getModel()).fireTableDataChanged();


                        }
                    }
                    jFrame.dispose();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });

        jp1.add(jButton2, constraints);


        jFrame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



}
