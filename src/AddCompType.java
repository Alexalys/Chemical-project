import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCompType {
    JTextField jTextField1;
    JFrame jFrame;
    JPanel jp1;
    JButton jButton;
    String name;

    AddCompType(Contract ins) {


            jFrame = new JFrame("Добавление типа сырья");
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
            JLabel jLabel1 = new JLabel("Название сырья");
            jp1.add(jLabel1, constraints);
            jTextField1 = new JTextField();

            constraints.gridx = 2;
            constraints.gridy = 1;
            //constraints.gridwidth=GridBagConstraints.REMAINDER;
            constraints.gridwidth = 2;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.anchor = GridBagConstraints.NORTH;
            constraints.insets = new Insets(0, 200, 0, 200);
            jp1.add(jTextField1, constraints);

            jButton = new JButton("Добавить");
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

                        name = jTextField1.getText();
                        if(name!=""){
                        if (!ac.addComponent(name)) {
                            JFrame errorFrame = new JFrame("ERROR!");
                            JOptionPane.showMessageDialog(errorFrame, "SUCH COMPONENT TYPE ALREADY EXISTS");
                        } else {


                            if(ins!=null)
                                ins.getDefaultComboBoxModel1().addElement(name);
                        }}
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
