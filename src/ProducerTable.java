import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProducerTable extends AbstractTableModel {
    ArrayList<ProducerCompos> components;
    ProducerTable(ArrayList<ProducerCompos> comp){
        super();
        this.components = comp;
    }

    public ProducerCompos getRow(int row){
        return components.get(row);
    }

    public ProducerTable getModel(){
        ProducerTable ob = new ProducerTable(this.components);
        return ob;

    }
    @Override
    public int getRowCount() {
        if(components.isEmpty())
            return 0;
        return components.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        String result="";
        switch (column){
            case 0:
                result="Название";
                break;
            case 1:
                result= "Город";
                break;
            case 2:
                result= "Контактный номер";
                break;
            case 3:
                result= "Контактное лицо";
                break;
        }
        return result;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(!components.isEmpty()) {
            switch (columnIndex) {
                case 0:
                    return components.get(rowIndex).getName();
                case 1:
                    return components.get(rowIndex).getCity();
                case 2:
                    return components.get(rowIndex).getPhone();
                case 3:
                    return components.get(rowIndex).getContact_face();
                default:
                    return "";
            }
        }
        else{
            return "";
        }

    }
    public void removeRow(int row){

        components.remove(row);

    }
}
