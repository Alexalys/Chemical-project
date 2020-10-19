import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class Accessor {

    private static Accessor singletonAccessor=null;
    private Connection con=null;
    Statement stat=null ;

    // скрытый конструктор принимает драйвер и адрес БД
    // выбрасывает исключение Exception
    private Accessor() throws Exception {

        String server ="WIN-LO5MHPBSIBE";
        String urlDatabase ="ProjectStock1";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // формирование строки подключения

        String connectionString = "jdbc:sqlserver://;serverName="+server+";databaseName="+urlDatabase+";integratedSecurity=true;";

        // подключение к БД
        con = DriverManager.getConnection(connectionString);
        stat = con.createStatement();

    }

    // открытый метод получения единственного экземпляра аксесора
    // выбрасывает исключение Exception
    public static Accessor getInstance()
            throws Exception {
        if (singletonAccessor == null)
            singletonAccessor = new Accessor();
        return singletonAccessor;
    }

    //закрывает соединение с БД
    public  void closeConnection()
            throws SQLException {
        if (con!= null)
            con.close();
    }
    void propertyStatement()throws SQLException
    {
        //проверка, реализует ли драйвер JDBC тот или иной тип выборки
        //TYPE_FORWARD_ONLY - курсор выборки перемещается только вперед
        //TYPE_SCROLL_INSENSITIVE - курсор перемещается в обеих направлениях, выборка не изменяется
        //TYPE_SCROLL_SENSITIVE - курсор перемещается в обеих направлениях, выборка изменяется при изменении строк в БД

        boolean ro=con.getMetaData().supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);
        System.out.println("TYPE_FORWARD_ONLY - "+ro);

        ro=con.getMetaData().supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        System.out.println("TYPE_SCROLL_INSENSITIVE - "+ro);

        ro=con.getMetaData().supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
        System.out.println("TYPE_SCROLL_SENSITIVE - "+ro);

        //проверка, поддерживает ли драйвер JDBC тот или иной режим изменения выборки
        //CONCUR_READ_ONLY - выборку нельзя изменять
        //CONCUR_UPDATABLE - выборку можно изменять
        ro=con.getMetaData().supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        System.out.println("CONCUR_READ_ONLY - "+ro);

        ro=con.getMetaData().supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        System.out.println("CONCUR_UPDATABLE - "+ro);


    }


    public boolean subtractPrice(HashMap<ComponentCompos,Float> mainWriteOff) throws SQLException{
        ResultSet rs;
        for(ComponentCompos i:mainWriteOff.keySet()) {
            rs = stat.executeQuery(" Select gkey FROM Contract WHERE gkey=" + i.getGkey());
            if (!rs.next())
                return false;
        }
        try {
            con.setAutoCommit(false);
            int n=0;
                for(ComponentCompos i:mainWriteOff.keySet()) {
                    n = stat.executeUpdate("EXECUTE [dbo].[SubtractPrice] " +
                            " @ContractID=" + i.getGkey() + ", " +
                            "@amount =" + mainWriteOff.get(i) + ";");
                }

            con.commit();
         if(n>0)
            return true;
        return false;
        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");
            con.rollback();

        }finally {
            con.setAutoCommit(true);
        }
        return false;


    }
    public boolean addOrder(HashMap<ComponentCompos,Float> mainWriteOff,String prod_name)throws SQLException{
        ResultSet rs;
        for(ComponentCompos i:mainWriteOff.keySet()) {
            rs = stat.executeQuery(" Select gkey FROM Contract WHERE gkey=" + i.getGkey());
            if (!rs.next())
                return false;
        }
        rs = stat.executeQuery("SELECT gkey FROM Product WHERE name = '"+prod_name+"'");
        //Если не существует продукции
        if(!rs.next()){
            rs.close();
            return false;
        }
        int id_prod = rs.getInt(1);
            int id_order=0;
        rs = stat.executeQuery("Select max(gkey)\n" +
                "from [dbo].[Order]");
        if(rs.next())
            id_order = rs.getInt(1);
        try {
            con.setAutoCommit(false);
            int n=stat.executeUpdate("INSERT INTO [dbo].[Order](date) VALUES (cast(GETDATE() as date))");
            for(ComponentCompos i:mainWriteOff.keySet()) {
                n = stat.executeUpdate("INSERT INTO Contract_order(fkey_contract,fkey_order,fkey_product,amount) VALUES("+i.getGkey()+","+id_order+","+id_prod+"," +mainWriteOff.get(i)+")");
            }

            con.commit();
            if(n>0)
                return true;
            return false;
        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");
            con.rollback();

        }finally {
            con.setAutoCommit(true);
        }
        return false;


    }

    public  boolean addComponent(String name) throws SQLException{

        ResultSet rs = stat.executeQuery("SELECT gkey FROM Component WHERE name = '"+name+"'");
        //Если уже существует сырье
        if(rs.next()){
            rs.close();
            return false;
        }
        int n = stat.executeUpdate("INSERT into Component(name) VALUES('"+name+"')");
        if(n>0){
            return true;
        }
        return false;



    }
    public HashMap<String,ArrayList<String>> getComposition(ArrayList<String>arr)throws SQLException{
        HashMap<String,ArrayList<String>> result = new HashMap<>();

        ResultSet rs;
        /*for(int i=0;i<arr.size();i++) {
            rs = stat.executeQuery("Select gkey FROM Product WHERE count ='" + arr.get(i)+"'");
            if(!rs.next()){
                rs.close();
                return false;
            }

        }*/
        for(int i=0;i<arr.size();i++) {
            rs = stat.executeQuery("SELECT Component.name FROM Component JOIN Product_component ON fkey_component=Component.gkey JOIN Product ON fkey_product=Product.gkey WHERE Product.name ='"+arr.get(i)+"'");
            System.out.println(arr.get(i));
            ArrayList<String> components = new ArrayList<>();
            while (rs.next()) {
                components.add(rs.getString(1));
                System.out.println(components);
            }
            result.put(arr.get(i), components);

        }
        return result;



    }
    public int addContract(HashMap<String,String> map) throws SQLException{
        // ResultSet rs = stat.executeUpdate("INSERT into ");

        ResultSet rs = stat.executeQuery(" Select gkey FROM Producers WHERE name = '" + map.get("Производитель")+"'");
        if(!rs.next())
            return 3;


        // float total_price=Float.parseFloat(map.get("Цена"))*Float.parseFloat(map.get("Количество"));
        //java.sql.Date date1 = new java.sql.Date(System.currentTimeMillis());
        // boolean a=false;
        //String storedProcedureCall = "{}"
        int n = stat.executeUpdate("EXECUTE [dbo].[AddContract] " +
                " @name_comp='" +map.get("Название")+"', "+
                "@name_prod ='" +map.get("Производитель")+"', "+
                " @price_per_kilo=" +Float.parseFloat(map.get("Цена"))+", "+
                " @amount = " + Float.parseFloat(map.get("Количество"))+";");
        /*int n=stat.executeUpdate("INSERT into Contract (total_price,price_per_kilo,amount,fkey_producer,fkey_component,date) "+
                "VALUES("+total_price+","+Float.parseFloat(map.get("Цена"))+","+Float.parseFloat(map.get("Количество"))+","+id_producer+","+id_component+",{d'"+date1+"'})");
       */ if(n>0)
            return 1;
        return 0;


    }

    public boolean addProductType(String name)throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Product WHERE name='"+name+"'");
        if(rs.next()){
            rs.close();
            return false;
        }
        int n = stat.executeUpdate("INSERT into Product(name) VALUES('"+name+"')");
        if(n>0){
            return true;
        }
        return false;


    }
    public boolean addProduct(HashSet<String> components, String name)throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Product WHERE name='"+name+"'");
        if(!rs.next()){
            rs.close();
            return false;
        }
        int id_product = rs.getInt(1);
        ArrayList<Integer> arr = new ArrayList<>();
        Iterator<String> iter1 = components.iterator();
        while(iter1.hasNext()){
            rs = stat.executeQuery("SELECT gkey FROM Component WHERE name='"+iter1.next()+"'");
            while(rs.next())
                arr.add(rs.getInt(1));
        }
        int n=0;
        for(int i=0;i<arr.size();i++) {
            n = stat.executeUpdate("INSERT INTO Product_component(fkey_product,fkey_component) VALUES(" + id_product + ',' + arr.get(i) + ")");
        }
        if(n>0)
            return true;
        return false;


    }
    public boolean addProducer(ArrayList<String> arr) throws SQLException{

        ResultSet rs = stat.executeQuery("SELECT gkey FROM Producers WHERE name = '"+arr.get(0)+"'");
        //Если уже существует сырье
        if(rs.next()){
            rs.close();
            return false;
        }
        int n = stat.executeUpdate("INSERT into Producers(name,city,phone,contact_face) VALUES('"+arr.get(0)+"','"+arr.get(1)+"','"+arr.get(2)+"','"+arr.get(3)+"')");
        if(n>0){
            return true;
        }
        return false;


    }

    public boolean deleteProducer(String name) throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Producers WHERE name='" + name+"'");
        if (!rs.next())
            return false;


        rs.close();
        try {



            int n = stat.executeUpdate("DELETE FROM Producers WHERE name ='" + name+"'");//+"IF(@@error<>0) ROLLBACK"

            if(n>0)
                return true;
            return false;

        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");


            return false;

        }
    }
    public boolean deleteContract(int gkey) throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Contract WHERE gkey=" + gkey);
        if (!rs.next())
            return false;


        rs.close();
        try {



            int n = stat.executeUpdate("DELETE FROM Contract WHERE gkey =" + gkey);//+"IF(@@error<>0) ROLLBACK"

            if(n>0)
                return true;
            return false;

        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");


            return false;

        }

    }
    public boolean deleteProduct(String name) throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Product WHERE name='" + name+"'");
        if (!rs.next())
            return false;


        rs.close();
        try {



            int n = stat.executeUpdate("DELETE FROM Product WHERE name ='" + name+"'");//+"IF(@@error<>0) ROLLBACK"
            System.out.println(n);
            if(n>0)
                return true;
            return false;

        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");


            return false;

        }

    }

   /* public boolean deleteContract(String count) throws SQLException{

        ResultSet rs = stat.executeQuery("SELECT gkey FROM Component WHERE count='" + count+ "'");
        if (!rs.next())
            return false;
        int id_component = rs.getInt(1);
        rs = stat.executeQuery("");
        rs.close();
        try {
            con.setAutoCommit(false);

            int n=stat.executeUpdate(" DELETE FROM Contract WHERE fkey_producer=" + id_component);

            n = stat.executeUpdate("DELETE FROM Component WHERE count ='" + count+ "'");//+"IF(@@error<>0) ROLLBACK"
            con.commit();
            if(n>0)
                return true;
            return false;

        }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");
            con.rollback();

        }finally {
            con.setAutoCommit(true);
        }
        return false;


    }*/
    public boolean deleteComponent(String name) throws SQLException{
        ResultSet rs = stat.executeQuery("SELECT gkey FROM Component WHERE name='" + name+ "'");
        if (!rs.next())
            return false;
        int id_component = rs.getInt(1);

        rs.close();
        try {



            int n = stat.executeUpdate("DELETE FROM Component WHERE name ='" + name+ "'");//+"IF(@@error<>0) ROLLBACK"

            if(n>0)
                return true;
            return false;

       }
        catch(SQLException e){
            System.out.println("SQLException. Executing rollback.");


            return false;

        }

    }

    public ArrayList<String> getProducerName()throws SQLException{
        ArrayList<String> arr = new ArrayList<>();
        ResultSet rs = stat.executeQuery("Select distinct name from Producers");
        while(rs.next()){
            arr.add(rs.getString(1));
        }
        return arr;
    }
    public ArrayList<String> getComponentsName()throws SQLException{
        ArrayList<String> arr = new ArrayList<>();
            ResultSet rs = stat.executeQuery("Select distinct name from Component");
        while(rs.next()){
            arr.add(rs.getString(1));
        }
        return arr;
    }
    public ArrayList<String> getProductsName()throws SQLException{
        ArrayList<String> arr = new ArrayList<>();
        ResultSet rs = stat.executeQuery("Select distinct name from Product");
        while(rs.next()){
            arr.add(rs.getString(1));
        }
        return arr;
    }
    public  ArrayList<ComponentCompos> getComponent()throws SQLException{
        ArrayList<ComponentCompos> result=new ArrayList<>();
        ComponentCompos obj;
        ResultSet rs = stat.executeQuery("Select Contract.gkey,Component.name,price_per_kilo,amount,Producers.name,Contract.date from Component join Contract on fkey_component=Component.gkey JOIN Producers ON fkey_producer=Producers.gkey ");
        while(rs.next()){
            //result.add(rs.getString("name"));
            //result.add(rs.getString("amount"));
            result.add(new ComponentCompos(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getFloat(4),rs.getString(5),rs.getString(6)));

        }
        return result;

    }
    public ArrayList<ComponentCompos> getComponentFields(Date date,String nameProduct)throws SQLException{
        ArrayList<ComponentCompos> result=new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ComponentCompos obj;
        ResultSet rs = stat.executeQuery("SELECT Component.gkey FROM Component\n" +
                "JOIN Product_component ON fkey_component=Component.gkey JOIN Product ON fkey_product=Product.gkey\n" +
                "WHERE Product.name='"+nameProduct+"'");
        /*ResultSet rs = stat.executeQuery("Select Contract.gkey,Component.name,amount,price_per_kilo,Producers.name,Contract.date from Component join Contract on fkey_component=Component.gkey JOIN Producers ON fkey_producer=Producers.gkey "+
                "WHERE cast(Contract.date as date) < cast({d'"+date+"'} as date)");*/
        while(rs.next()){
            names.add(rs.getString(1));
        }
       for(int i=0;i<names.size();i++) {
           rs = stat.executeQuery("Select Contract.gkey,Component.name,price_per_kilo,amount,Producers.name,Contract.date from Component join Contract on fkey_component=Component.gkey JOIN Producers ON fkey_producer=Producers.gkey " +
                   "WHERE Component.gkey = " +names.get(i) +"AND cast(Contract.date as date) < cast({d'" + date + "'} as date)");


           while (rs.next()) {
               //result.add(rs.getString("name"));
               //result.add(rs.getString("amount"));
               result.add(new ComponentCompos(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getFloat(4), rs.getString(5), rs.getString(6)));

           }
           rs.close();
       }
        return result;
    }
    public ArrayList<ProducerCompos> getProducers()throws SQLException{
        ArrayList<ProducerCompos> result=new ArrayList<>();
        ProducerCompos obj;
        ResultSet rs = stat.executeQuery("Select name,city,phone,contact_face from Producers ");
        while(rs.next()){
            //result.add(rs.getString("name"));
            //result.add(rs.getString("amount"));
            result.add(new ProducerCompos(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));

        }
        return result;

    }



    protected void finalize ()
    {
        try{
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }


}
