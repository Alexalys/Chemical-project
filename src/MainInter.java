import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class MainInter {
    JScrollPane scrollPane;
    StockTable model;
    JTable jTabStock;
    JTable jTableStockMain;
    StockTable StockModel;
    JScrollPane jScrollPaneMain;
    DefaultComboBoxModel defaultComboBoxModel1;
    DefaultComboBoxModel defaultComboBoxModel2;
    Contract ins;
    JComboBox comboBox1;
    JScrollPane jScrollPaneProducer;
    JTable jTableProducer;
    ProducerTable ProducerModel;
    ProductTable ProductModel;
    JTable jTableProduct;
    JScrollPane jScrollPaneProduct;
    StockTable DelStockModel;
    JTable jTabDel;
    String comboValueDelete ="";
    StockTable newDelCtockModel;
    java.sql.Date  dateX;
    ArrayList<ComponentCompos> writeoffData=new ArrayList<>();
    ArrayList<Float> writeoffAmount=new ArrayList<>();
    HashMap<ComponentCompos,Float> mainWriteOff = new HashMap<>();

    public MainInter(Accessor ac) {
    }


    public void UI(Accessor ac) throws SQLException {

        JFrame frame = new JFrame("MainInter");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel1 = new JPanel();
        panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //panel1.setLayout(new GridLayout(0,3));
        /*panel1.setMinimumSize(new Dimension(100, 171));
        panel1.setPreferredSize(new Dimension(1920, 1080));*/

        panel1.setRequestFocusEnabled(true);
        panel1.setBorder(BorderFactory.createTitledBorder("НТЦ\"ПНИТ\""));


        //Initializing layout for panel1(Out main panel)
        GridBagLayout gridBagLayout1 = new GridBagLayout();
        panel1.setLayout(gridBagLayout1);
        //making constraints for our layout
        JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setPreferredSize(new Dimension(170, 130));
        tabbedPane1.setMinimumSize(new Dimension(1920, 1080));
        //Font tabbedPane1Font = this.$$$getFont$$$(null, Font.BOLD, 13, tabbedPane1.getFont());
        //if (tabbedPane1Font != null) tabbedPane1.setFont(tabbedPane1Font);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.1;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.insets = new Insets(10, 10, 0, 0);
        gridBagLayout1.setConstraints(panel1, constraints);

        panel1.add(tabbedPane1, constraints);
        java.sql.Date d2 = new java.sql.Date(System.currentTimeMillis());
        String dateTitle = new SimpleDateFormat("dd-MM-yyyy").format(d2);
        tabbedPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), dateTitle, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BOTTOM));

        final JPanel panel2 = new JPanel();
        //panel2.setLayout(new GridLayout(-1,-1));
        //panel2.setPreferredSize(new Dimension(1920,1080));

        panel2.setLayout(gridBagLayout1);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        tabbedPane1.addTab("Списание сырья", panel2);

        JLabel TextField1 = new JLabel("Списание сырья");
        //TextField.setPreferredSize(new Dimension(300,30));
        TextField1.setFont(new Font("Informal011 BT", Font.ITALIC, 20));
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        panel2.add(TextField1, constraints);




      /* MainJobButton ovBut = new MainJobButton("СПИСАТЬ");
        ovBut.setBackground(new Color(204,255,255));
        constraints.ipadx =30;
        constraints.ipady=30;
        constraints.gridwidth=2;
        constraints.weightx=1;
        constraints.fill=GridBagConstraints.NONE;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(50, 0, 0, 0);
        panel2.add(ovBut, constraints);
        ovBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        constraints.ipady=0;
        constraints.ipadx=0;*/

        comboBox1 = new JComboBox();
        defaultComboBoxModel1= new DefaultComboBoxModel();

        ArrayList<String> arr = new ArrayList<>();
        arr = ac.getProductsName();
        defaultComboBoxModel1.setSelectedItem("Списать сырье для:");
        defaultComboBoxModel1.addAll(arr);
        comboBox1.setPreferredSize(new Dimension(300, 30));
        comboBox1.setModel(defaultComboBoxModel1);
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets=new Insets(0,0,0,0);
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                comboValueDelete =(String)box.getSelectedItem();

            }
        });

        panel2.add(comboBox1, constraints);


        constraints.anchor = GridBagConstraints.EAST;

        UtilDateModel model1 = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model1, p);

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        panel2.add(datePicker,constraints);


        JButton jButtonCalendar = new JButton("Вывести необходимое сырье");
        constraints.gridy=0;
        constraints.anchor = GridBagConstraints.SOUTH;
        panel2.add(jButtonCalendar,constraints);
        jButtonCalendar.addActionListener(new ActionListener() {
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

                    dateX = new java.sql.Date(((java.util.Date) datePicker.getModel().getValue()).getTime());

                    //java.sql.Date dateNew = java.sql.Date.valueOf(dateX.toString());

                    newDelCtockModel=new StockTable(ac.getComponentFields(dateX,comboValueDelete));
                    jTabDel.setModel(newDelCtockModel);
                    DelStockModel.fireTableDataChanged();

                    //System.out.println(ac.getComponentsName());
                    //System.out.println("BlablaBLBLLBLBLBLBLBLLBLB");
                    //ac.closeConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });





/**********************************************************************************************************/

        //Date dateFor = Date.valueOf("2019-05-27");
        ArrayList<ComponentCompos> dataDel = new ArrayList<>();
        GridBagConstraints constraints1 = new GridBagConstraints();
        DelStockModel = new StockTable(dataDel);

        jTabDel = new JTable(DelStockModel);
        /***Setting Jscroll pane*/
        //JScrollPane jScrollPane= new JScrollPane(jTabStock);
        //jTabStock.setMinimumSize(new Dimension(1200,800));

        //jTabStock.setPreferredScrollableViewportSize(new Dimension(300, 300));
        //jTabStock.setPreferredScrollableViewportSize(new Dimension(1920,1080));
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints1.gridy = GridBagConstraints.RELATIVE;
        constraints1.gridx = 0;
        constraints1.weightx=1;
        constraints1.weighty=0.1;
       // constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints1.gridheight = 1;

        constraints1.fill = GridBagConstraints.BOTH;
        constraints1.insets = new Insets(0, 0, 0, 0);
        constraints1.anchor = GridBagConstraints.WEST;
        jTabDel.setRowHeight(30);
        JScrollPane delScrollP = new JScrollPane(jTabDel);
        panel2.add(delScrollP,constraints1);

        jTabDel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    new ChangeContract(jTabDel, dateX, comboValueDelete, jTabStock, writeoffData,mainWriteOff);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });






        /**************************************************************************************/
        JLabel TextField = new JLabel("Таблица Приказа на списание");
        //TextField.setPreferredSize(new Dimension(300,30));
        TextField.setFont(new Font("Dialog", Font.PLAIN, 20));
        constraints.gridx = 3;
        constraints.gridy=0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridwidth = 1;
        constraints.gridwidth = 1;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(0, 0, 0, 0);
        // TextField.setEditable(false);
        panel2.add(TextField, constraints);

        JButton jButtonOrder = new JButton("Создать приказ");
        constraints.gridy = 0;
        constraints.weighty=0.4;
        constraints.anchor=GridBagConstraints.CENTER;
        panel2.add(jButtonOrder,constraints);
        jButtonOrder.addActionListener(new ActionListener() {
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

                    if(!ac.subtractPrice(mainWriteOff))
                        JOptionPane.showMessageDialog(new JFrame(), "CAN NOT BE ADDED");
                    else{
                        if(!ac.addOrder(mainWriteOff,comboValueDelete))
                            JOptionPane.showMessageDialog(new JFrame(), "Error in making order");
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Successful!");
                            jTabStock.setModel(new StockTable(new ArrayList<>()));
                            ((StockTable)jTabStock.getModel()).fireTableDataChanged();
                            StockTable newMainStockModel = new StockTable(ac.getComponent());
                            jTableStockMain.setModel(newMainStockModel);
                            ((StockTable)jTableStockMain.getModel()).fireTableDataChanged();
                            mainWriteOff.clear();
                        }


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        ArrayList<ComponentCompos> data = new ArrayList<>();
        model = new StockTable(data);

        Object[] headers = {"Название", "Цена за кг", "Количество", "Производитель"};
        jTabStock = new JTable(model);
        /***Setting Jscroll pane*/
        //JScrollPane jScrollPane= new JScrollPane(jTabStock);
        //jTabStock.setMinimumSize(new Dimension(1200,800));
        GridBagConstraints constraints2 = new GridBagConstraints();
        //jTabStock.setPreferredScrollableViewportSize(new Dimension(300, 300));
        //jTabStock.setPreferredScrollableViewportSize(new Dimension(1920,1080));
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.gridheight = 3;
        //constraints.gridwidth =3;
        constraints2.gridx=3;
        constraints2.weighty=0.6;
        constraints2.weightx=0.9;
        constraints2.gridy = GridBagConstraints.RELATIVE;
        constraints2.gridx = 3;
        constraints2.fill = GridBagConstraints.BOTH;
        constraints2.insets = new Insets(0, 0, 0, 0);
        constraints2.anchor = GridBagConstraints.NORTHEAST;
        jTabStock.setRowHeight(30);
        jTabStock.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    new DeleteContract(jTabStock,jTableStockMain,2);


                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        scrollPane = new JScrollPane(jTabStock);
        //constraints.
        panel2.add(scrollPane, constraints2);
        ImageIcon button_icon = new ImageIcon("src/Images/Enter.png");
        JButton add_compon_b = new JButton(button_icon);
        add_compon_b.setPreferredSize(new Dimension(51, 51));
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridy = 3;
        constraints.gridx = 3;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.NONE;
        ActionListener actionListener1 = new MyListener();
        add_compon_b.addActionListener(actionListener1);
        /**************panel2.add(add_compon_b, constraints);***************************/

        final JPanel panel3 = new JPanel();
        tabbedPane1.addTab("Добавление товара", panel3);
        panel3.setLayout(gridBagLayout1);
        ArrayList<String> dataProduct = ac.getProductsName();
        HashMap<String,ArrayList<String>> prodComposition = ac.getComposition(dataProduct);
        ProductModel = new ProductTable(dataProduct,prodComposition);
        jTableProduct = new JTable(ProductModel);
        DefaultListModel model = new DefaultListModel();
        //DefaultListModel model = new DefaultListModel();

        /*for(String i:stringJList.get(components.get(rowIndex)))
            model.addElement("\t\t\t"+i);
        JList list = new JList(model);*/
        /*for(String i:dataProduct)
            model.addElement(i);
        JList list = new JList(model);
*/

        //jTableProduct.getColumn("Название").setCellRenderer(new JListRenderer());
        //jTableProduct.getColumn("Название").setCellEditor(new JListEditor());
        jTableProduct.setRowHeight(80);
        //jTableStockMain.setPreferredScrollableViewportSize(new Dimension(1920,1080));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 0;


        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;

        constraints.insets = new Insets(50, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        jTableProduct.setRowHeight(30);
        jTableProduct.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    new DeleteProduct(jTableProduct);


                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jScrollPaneProduct = new JScrollPane(jTableProduct);

        //constraints.
        panel3.add(jScrollPaneProduct, constraints);
        JButton jButtonAddProduct = new JButton("Добавить продукцию");
        constraints.gridx = 0;
        constraints.gridy=0;
        constraints.gridheight = GridBagConstraints.NONE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 10, 0, 0);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;

        panel3.add(jButtonAddProduct, constraints);
        jButtonAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductType(jTableProduct);
            }
        });

        /*JButton jButtonDeleteProduct = new JButton("Удалить продукцию");
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 1;

        constraints.insets = new Insets(0, 0, 0, 10);
        panel3.add(jButtonDeleteProduct, constraints);
        jButtonDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new DeleteProduct(jTableProduct);
            }
        });*/




        final JPanel panel4 = new JPanel();
        tabbedPane1.addTab("Добавление поставщика", panel4);
        panel4.setLayout(gridBagLayout1);
        JButton jButtonAddProducerType = new JButton("Добавить поставщика");
        constraints.gridx = 0;
        constraints.gridy=0;
        constraints.gridheight = GridBagConstraints.NONE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 10, 0, 0);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;

        panel4.add(jButtonAddProducerType, constraints);
        jButtonAddProducerType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProdType addProdType = new AddProdType(ins,jTableProducer);
            }
        });

        JButton jButtonDeleteProducer = new JButton("Удалить поставщика");
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 1;

        constraints.insets = new Insets(0, 0, 0, 10);
        panel4.add(jButtonDeleteProducer, constraints);
        jButtonDeleteProducer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteProdType delprod = new DeleteProdType(ins,jTableProducer);
            }
        });



        ArrayList<ProducerCompos> dataProd = ac.getProducers();
        ProducerModel = new ProducerTable(dataProd);
        jTableProducer = new JTable(ProducerModel);
        //jTableStockMain.setPreferredScrollableViewportSize(new Dimension(1920,1080));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 0;


        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;

        constraints.insets = new Insets(50, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        jTableProducer.setRowHeight(30);
        jTableProducer.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                new DeleteProdTypeKey(jTableProducer, ins);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jScrollPaneProducer = new JScrollPane(jTableProducer);

        //constraints.
        panel4.add(jScrollPaneProducer, constraints);



        final JPanel panel5 = new JPanel();
        //panel5.setSize(new Dimension(1920,1080));
        tabbedPane1.addTab("Таблица сырья", panel5);

        panel5.setLayout(gridBagLayout1);

/*******/
        ArrayList<ComponentCompos> dataStock = ac.getComponent();
        StockModel = new StockTable(dataStock);
        jTableStockMain = new JTable(StockModel);
        //jTableStockMain.setPreferredScrollableViewportSize(new Dimension(1920,1080));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 0;
        //constraints.gridwidth=4;
        constraints.weightx = 1;
        constraints.weighty = 0.1;

        constraints.insets = new Insets(50, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        jTableStockMain.setRowHeight(30);
        jTableStockMain.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    new DeleteContract(jTableStockMain,jTabStock,1);


                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jScrollPaneMain = new JScrollPane(jTableStockMain);
        //constraints.
        panel5.add(jScrollPaneMain, constraints);
        JButton jButtonAddContract = new JButton("Добавить");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 10, 0, 0);
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        ActionListener actionListenerButtonAdd = new MyListener();
        jButtonAddContract.addActionListener(actionListenerButtonAdd);
        panel5.add(jButtonAddContract, constraints);

        JButton jButtonAddStockType = new JButton("Добавить тип сырья");
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 0, 0, 0);
        panel5.add(jButtonAddStockType, constraints);
        jButtonAddStockType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCompType addCompType = new AddCompType(ins);
            }
        });

        JButton jButtonDeleteContract = new JButton("Удалить тип сырья");
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.insets = new Insets(0, 0, 0, 10);
        jButtonDeleteContract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteCompType delcom = new DeleteCompType(ins);
            }
        });
        panel5.add(jButtonDeleteContract, constraints);


        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    Accessor ac = Accessor.getInstance();
                    if (ac != null)
                        System.out.println("Connection successful");
                    else {
                        System.out.println("Connection faild");
                        System.exit(0);
                    }

                    ac.closeConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    class MyListener implements ActionListener {
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
                 ins = new Contract(model, jTabStock, StockModel, jTableStockMain);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}






