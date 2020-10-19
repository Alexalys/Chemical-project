import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Contract {
    public JComboBox jtf1;
    public JTextField jtf2;
    public JTextField jtf3;
    public JComboBox jtf4;
    public DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();



    JFrame jf1;
    JPanel jp1;
    HashMap<String,String> map;
    public Contract(StockTable model, JTable jTabStock, StockTable StockModel, JTable jTableStockMain){
        try{
            Accessor ac=Accessor.getInstance();
            if(ac!=null)
                System.out.println("Connection successful");
            else
            {
                System.out.println("Connection faild");
                System.exit(0);
            }


            //System.out.println(ac.getComponents());


            map = new HashMap<>();
            jf1 = new JFrame("Добавление сырья");
            jf1.setSize(new Dimension(520, 200));
            //jf1.setMaximumSize(new Dimension(500,200));
            jf1.setResizable(false);
            jp1 = new JPanel();
            jf1.setLocation(610, 340);
            jf1.add(jp1);
            jp1.setLayout(new GridBagLayout());
            //StockTable model = new StockTable();
            // Object[] headers ={"Название","Цена за кг","Количество","Производитель"};
            //TableModel
            //JTable jTabStock=new JTable();

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.insets = new Insets(30, 0, 0, 0);
            constraints.anchor = GridBagConstraints.NORTH;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weighty = 0.1;
            constraints.weightx = 0.1;
            JLabel jl1 = new JLabel("Название");
            jp1.add(jl1, constraints);
            JLabel jl2 = new JLabel("Цена за кг");
            constraints.gridx = 1;
            jp1.add(jl2, constraints);
            JLabel jl3 = new JLabel("Количество");
            constraints.gridx = 2;
            jp1.add(jl3, constraints);
            JLabel jl4 = new JLabel("Производитель");
            constraints.gridx = 3;
            jp1.add(jl4, constraints);

            constraints.gridy = 2;
            constraints.ipadx = 0;
            constraints.ipady = 0;

            constraints.insets = new Insets(0, 5, 0, 5);
            constraints.anchor = GridBagConstraints.NORTH;
            jtf1 = new JComboBox();
            defaultComboBoxModel1 = new DefaultComboBoxModel();
            ArrayList<String> arr = ac.getComponentsName();
            defaultComboBoxModel1.addAll(arr);
            jtf1.setModel(defaultComboBoxModel1);
            constraints.gridx = 0;
            //jtf1.setPreferredSize(new Dimension(50,20));
            jtf1.setPreferredSize(new Dimension(100,25));
            jtf1.setMinimumSize(new Dimension(110,25));

            jp1.add(jtf1, constraints);


            jtf1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox box = (JComboBox)e.getSource();
                    map.put("Название",(String)box.getSelectedItem());

                }
            });
            jtf2 = new JTextField();
            constraints.gridx = 1;
            jtf2.setMinimumSize(new Dimension(100,25));
            //constraints.ipadx = 50;
            jp1.add(jtf2, constraints);
            jtf3 = new JTextField();
            constraints.gridx = 2;
            //jtf3.setPreferredSize(new Dimension(50,20));
            jtf3.setMinimumSize(new Dimension(100,25));
            jp1.add(jtf3, constraints);
            jtf4 = new JComboBox();
            arr = ac.getProducerName();
            DefaultComboBoxModel defaultComboBoxModel2= new DefaultComboBoxModel();
            defaultComboBoxModel2.addAll(arr);
            jtf4.setModel(defaultComboBoxModel2);
            constraints.gridx = 3;
            //jtf4.setPreferredSize(new Dimension(50,20));
            jtf4.setMinimumSize(new Dimension(110,25));
            jp1.add(jtf4, constraints);
            jtf4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox box = (JComboBox)e.getSource();
                    map.put("Производитель",(String)box.getSelectedItem());

                }
            });


            JButton add = new JButton("Добавить");
            constraints.gridy = 3;
            constraints.gridx = GridBagConstraints.RELATIVE;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill=GridBagConstraints.HORIZONTAL;
            constraints.ipadx = 0;
            constraints.ipady = 0;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            add.setPreferredSize(new Dimension(600, 20));
            add.setSize(new Dimension(600, 20));

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetComps(model,jTabStock,StockModel,jTableStockMain);

                }
            });

            jp1.add(add, constraints);


            jf1.setVisible(true);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        }
        public void SetComps(StockTable model,JTable jTabStock,StockTable StockModel,JTable jTableStockMain){
            try {
                Accessor ac = Accessor.getInstance();
                if (ac != null)
                    System.out.println("Connection successful");
                else {
                    System.out.println("Connection faild");
                    System.exit(0);
                }


                /*jtf1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JComboBox box = (JComboBox)e.getSource();
                        data.add((String)box.getSelectedItem());
                    }
                });*/

                map.put("Цена",jtf2.getText());
                map.put("Количество",jtf3.getText());
               /* for (int i = 0; i < data.size(); i++) {
                    System.out.println(data.get(i));
                }

                System.out.println(data);*/
                int n=ac.addContract(map);
                if(n==1) {
                    /*ArrayList<ComponentCompos> dataa = ac.getComponentFields();
                    StockTable model1 = new StockTable(dataa);
                    jTabStock.setModel(model1);
                    model.fireTableDataChanged();*/

                    ArrayList<ComponentCompos> data2 = ac.getComponent();
                    StockTable model2 = new StockTable(data2);
                    jTableStockMain.setModel(model2);
                    StockModel.fireTableDataChanged();


                    JFrame jf2 = new JFrame("Confirmation");
                    jf2.setSize(new Dimension(300, 200));
                    //jf1.setMaximumSize(new Dimension(500,200));
                    jf2.setResizable(false);
                    jf2.setLocation(610, 340);
                    JPanel jp2 = new JPanel();
                    jp2.setSize(new Dimension(300, 200));
                    jf2.add(jp2);
                    GridBagLayout gridBagLayout = new GridBagLayout();
                    jp2.setLayout(gridBagLayout);
                    GridBagConstraints constraints = new GridBagConstraints();
                    gridBagLayout.setConstraints(jp2, constraints);


                    JLabel jTextField2 = new JLabel("Do you want to add more?");

                    jTextField2.setFont(new Font("Times New Roman", Font.BOLD, 17));
                    //jTextField2.setMinimumSize(new Dimension(250,30));
                    constraints.gridx = 0;
                    constraints.gridy = 0;
                    constraints.weightx = 0.5;
                    constraints.weighty = 0.5;
                    constraints.gridwidth = 2;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.anchor = GridBagConstraints.EAST;
                    constraints.insets = new Insets(0, 45, 0, 0);


                    jp2.add(jTextField2, constraints);

                    JButton jButton2 = new JButton("OK");
                    constraints.gridwidth = 1;
                    constraints.insets = new Insets(20, 10, 0, 10);
                    constraints.gridx = 0;
                    constraints.gridy = 1;
                    constraints.fill = GridBagConstraints.NONE;
                    constraints.anchor = GridBagConstraints.WEST;
                    jButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jf2.dispose();
                            jtf1.setSelectedIndex(-1);
                            jtf2.setText("");
                            jtf3.setText("");
                            jtf4.setSelectedIndex(-1);


                        }
                    });
                    jp2.add(jButton2, constraints);

                    JButton jButton3 = new JButton("Cancel");
                    constraints.gridx = 1;
                    constraints.anchor = GridBagConstraints.EAST;
                    jButton3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jf1.dispose();
                            jf2.dispose();
                        }
                    });
                    jp2.add(jButton3, constraints);


                    jf2.setVisible(true);
                }


            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    public DefaultComboBoxModel getDefaultComboBoxModel1() {
        return defaultComboBoxModel1;
    }




}
