import jdk.jfr.Enabled;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StockTable extends AbstractTableModel{
    ArrayList<ComponentCompos> components;
    StockTable(){
        super();
        this.components = new ArrayList<ComponentCompos>();
    }
    StockTable(ArrayList<ComponentCompos> comp){
        super();
        this.components=comp;
    }
    public ComponentCompos getRow(int row){
        return components.get(row);
    }

    public StockTable getModel(){
        StockTable ob = new StockTable(this.components);
        return ob;

    }
    @Override
    public int getRowCount(){
        if(components.isEmpty())
            return 1;

        return components.size();
        }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(!components.isEmpty()) {
            switch (columnIndex) {
                case 0:
                    return components.get(rowIndex).getGkey();
                case 1:
                    return components.get(rowIndex).getName();
                case 2:
                    return components.get(rowIndex).getPrice_per_kilo();
                case 3:
                    return components.get(rowIndex).getAmount();
                case 4:
                    return components.get(rowIndex).getProducer();
                case 5:
                    return components.get(rowIndex).getDate();
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
                result="Идентификационный номер";
                break;
            case 1:
                result= "Название";
                break;
            case 2:
                result= "Цена за кг";
                break;
            case 3:
                result= "Количество";
                break;
            case 4:
                result= "Производитель";
                break;
            case 5:
                result = "Дата поставки";
                break;
        }
        return result;
    }
    public void removeRow(int row){

            components.remove(row);

    }
    public void setRow(int row,ComponentCompos component){
        components.set(row,component);
    }
    public int getRowNumber(ComponentCompos component){
        for(int i=0;i<components.size();i++){
            if(components.get(i).equals(component)){
                return i;
            }
        }
        return -1;

    }


}
