import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteProdType {
    JComboBox jTextField1;
    JFrame jFrame;
    JPanel jp1;
    JButton jButton;
    String name;

    DeleteProdType(Contract ins, JTable tab) {
        try {
            Accessor ac = Accessor.getInstance();
            if (ac != null)
                System.out.println("Connection successful");
            else {
                System.out.println("Connection faild");
                System.exit(0);
            }

            jFrame = new JFrame("Удаление поставщика");
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
            JLabel jLabel1 = new JLabel("Название поставщика");
            jp1.add(jLabel1, constraints);
            jTextField1 = new JComboBox();
            DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
            ArrayList<String> arr = ac.getProducerName();
            defaultComboBoxModel1.addAll(arr);
            jTextField1.setModel(defaultComboBoxModel1);
            jTextField1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox box = (JComboBox) e.getSource();
                    name = (String) box.getSelectedItem();

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

            jButton = new JButton("Удалить");
            constraints.gridy = 2;
            constraints.gridx = 2;
            constraints.gridwidth = 2;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.anchor = GridBagConstraints.NORTH;
            constraints.insets = new Insets(0, 0, 0, 0);
            jButton.addActionListener(new ActionListener() {
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


                        if (!ac.deleteProducer(name)) {
                            JFrame errorFrame = new JFrame("ERROR!");
                            JOptionPane.showMessageDialog(errorFrame, "CAN NOT BE DELETED");

                        } else {
                            if (ins != null)
                                ins.getDefaultComboBoxModel1().removeElement(name);
                            ArrayList<ProducerCompos> dataProd = ac.getProducers();
                            tab.setModel( new ProducerTable(dataProd));
                            ((ProducerTable)tab.getModel()).fireTableDataChanged();

                        }

                        jFrame.dispose();


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

            });

            jp1.add(jButton, constraints);


            jFrame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}


