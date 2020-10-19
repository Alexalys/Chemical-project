import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;

public class AddProdType {
    JTextField jTextField1;
    JFrame jFrame;
    JPanel jp1;
    JButton jButton;
    String name;

    AddProdType(Contract ins, JTable tab) {


        jFrame = new JFrame("Добавление поставщика");
        jFrame.setSize(new Dimension(520, 200));
        jFrame.setResizable(false);
        jp1 = new JPanel();
        jFrame.setLocation(610, 340);
        jFrame.add(jp1);
        GridBagLayout gridBagLayout = new GridBagLayout();

        jp1.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        gridBagLayout.setConstraints(jp1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, 30, 0, 0);
        JLabel jLabel1 = new JLabel("Название поставщика");
        jp1.add(jLabel1, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, 20, 0, 0);
        JLabel jLabel2 = new JLabel("Город");
        jp1.add(jLabel2, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, 20, 0, 0);
        JLabel jLabel3 = new JLabel("Телефон");
        jp1.add(jLabel3, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, 30, 0, 10);
        JLabel jLabel4 = new JLabel("Контактное лицо");
        jp1.add(jLabel4, constraints);

        jTextField1 = new JTextField();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 10, 0, 0);
        jp1.add(jTextField1, constraints);


        JTextField jTextField2 = new JTextField();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 10, 0, 0);
        jp1.add(jTextField2, constraints);

        JTextField jTextField3 = new JTextField();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 10, 0, 0);
        jp1.add(jTextField3, constraints);

        JTextField jTextField = new JTextField();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 10, 0, 10);
        jp1.add(jTextField, constraints);


        jButton = new JButton("Добавить");
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
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
                    ArrayList<String> arr = new ArrayList<>();
                    if(!jTextField1.getText().isEmpty() ||!jTextField2.getText().isEmpty()||!jTextField3.getText().isEmpty()||!jTextField.getText().isEmpty() )
                    {
                        arr.add(jTextField1.getText());
                        arr.add(jTextField2.getText());
                        arr.add(jTextField3.getText());
                        arr.add(jTextField.getText());

                        // name = jTextField1.getText();

                        if (!ac.addProducer(arr)) {
                            JFrame errorFrame = new JFrame("ERROR!");
                            JOptionPane.showMessageDialog(errorFrame, "SUCH COMPONENT TYPE ALREADY EXISTS");
                        } else {


                            if (ins != null) {
                                ins.getDefaultComboBoxModel1().addElement(name);


                            }
                            ArrayList<ProducerCompos> dataProd = ac.getProducers();
                            tab.setModel( new ProducerTable(dataProd));
                            ((ProducerTable)tab.getModel()).fireTableDataChanged();


                        }
                    }
                    jFrame.dispose();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });

        jp1.add(jButton, constraints);


        jFrame.setVisible(true);


    }


}
