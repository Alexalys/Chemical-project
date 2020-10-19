import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ChangeContract {
    JTextField jTextField1;
    JFrame jFrame;
    JPanel jp1;
    JButton jButton;
    String count;
    ChangeContract(JTable tab, java.sql.Date dateX, String comboValueDelete, JTable JtabStock, ArrayList<ComponentCompos> writeoffData, HashMap<ComponentCompos,Float> mainWriteOff) {



        jFrame = new JFrame("Списание сырья");
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
        constraints.insets = new Insets(0, 190, 0, 0);
        JLabel jLabel1 = new JLabel("Количество для списания");
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

                    count = jTextField1.getText();
                    StockTable a = (StockTable) tab.getModel();
                    ComponentCompos com = (a).getRow(tab.getSelectedRow());
                    if (count != "") {

                        float newCount = Float.parseFloat(count);
                       /* if (!ac.subtractPrice(com.getGkey(),newCount)) {
                            JFrame errorFrame = new JFrame("ERROR!");
                            JOptionPane.showMessageDialog(errorFrame, "ERROR!");
                        } else {*/

/**************************************************При выборе других дат в комбобоксе перед двойным щелчком мыши возможны проблемы******************************************************************/

                            float amount_old = com.getAmount();
                            com.setAmount(amount_old-newCount);
                            int k = a.getRowNumber(a.getRow(tab.getSelectedRow()));

                            a.setRow(k,a.getRow(tab.getSelectedRow()));
                            tab.setModel(a);
                            ((StockTable)tab.getModel()).fireTableDataChanged();

                            ArrayList<ComponentCompos> arrP = new ArrayList<>();
                            if(!mainWriteOff.isEmpty()) {
                                //mainWriteOff.put(com,newCount);
                                Set<ComponentCompos> s = mainWriteOff.keySet();
                                Iterator<ComponentCompos> iterator = s.iterator();

                                while(iterator.hasNext()) {
                                    ComponentCompos p = iterator.next();
                                    if (p.getGkey() == com.getGkey()) {
                                        float per = mainWriteOff.get(p) + newCount;
                                        arrP.add(p);

                                        mainWriteOff.put(com, per);

                                    } else {
                                        mainWriteOff.put(com, newCount);

                                    }
                                }
                            }
                            else
                                mainWriteOff.put(com, newCount);

                            for(int i=0;i<arrP.size();i++){
                                mainWriteOff.remove(arrP.get(i));
                            }

                           /* for(int i=0;i<writeoffData.size();i++){
                                if(writeoffData.get(i).getGkey()==com.getGkey()){
                                    writeoffData.set(i,com);
                                }
                            else{
                                    writeoffData.add(com);
                                    writeoffAmount.add(newCount);

                                }
                            }*/

                            //System.out.println(writeoffData);
                            ArrayList<ComponentCompos> perList = new ArrayList<>();
                            for(ComponentCompos i:mainWriteOff.keySet()) {

                                 perList.add(i);
                            }
                            ArrayList<ComponentCompos> perList2 = new ArrayList<>(perList);
                            for(ComponentCompos i1:perList2){
                                if(i1.getGkey()==com.getGkey()){
                                    i1.setAmount(newCount);
                                }
                            }
                            StockTable ModelStockJTab = new StockTable(perList2);
                            JtabStock.setModel(ModelStockJTab);
                            ((StockTable)JtabStock.getModel()).fireTableDataChanged();





                        //}
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
