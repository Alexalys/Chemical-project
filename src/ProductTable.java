import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductTable extends AbstractTableModel {
    ArrayList<String> components;
    HashMap<String,ArrayList<String>> stringJList;


    public ProductTable(ArrayList<String> components, HashMap<String,ArrayList<String>> stringJList) {
        this.components = components;
        this.stringJList = stringJList;
    }


    public String getRowName(int row){
        return components.get(row);
    }

    /*public StockTable getModel(){
        StockTable ob = new StockTable(this.components);
        return ob;

    }*/
    @Override
    public int getRowCount(){
        if(components.isEmpty())
            return 1;

        return components.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if(!components.isEmpty()) {
            switch (columnIndex) {
                case 0:
                    return components.get(rowIndex);
                case 1:
                    return stringJList.get(components.get(rowIndex));
                default:
                    return "";
            }
        }
        else{
            return "";
        }

    }

    @Override
    public String getColumnName(int column) {
        String result="";
        switch (column){
            case 0:
                result="Название";
                break;
            case 1:
                result= "Состав";
                break;

        }
        return result;
    }
    public void removeRow(int row){

        components.remove(row);
        stringJList.remove(components.remove(row));

    }
    public Class<? extends Object> getColumnClass(int c)
    {
        if(c == 1) return JScrollPane.class;
        else return getValueAt(0, c).getClass();
    }

}
