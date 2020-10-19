public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Accessor ac = Accessor.getInstance();
            if (ac != null)
                System.out.println("Connection successful");
            else {
                System.out.println("Connection faild");
                System.exit(0);
            }

            ac.propertyStatement();
            MainInter a = new MainInter(ac);
            a.UI(ac);
            //System.out.println(ac.getComponentsName());
            //System.out.println("BlablaBLBLLBLBLBLBLBLLBLB");
            //ac.closeConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
