public class ComponentCompos {
    private int gkey;
    private String name;
    private double price_per_kilo;
    private float amount;
    private String producer;

    private String date;


    public ComponentCompos(int gkey, String name, double price_per_kilo, float amount, String producer,String date) {
        this.name = name;
        this.price_per_kilo = price_per_kilo;
        this.amount = amount;
        this.producer = producer;
        this.gkey=gkey;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice_per_kilo() {
        return price_per_kilo;
    }

    public void setPrice_per_kilo(double price_per_kilo) {
        this.price_per_kilo = price_per_kilo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
    public int getGkey() {
        return gkey;
    }

    public void setGkey(int gkey) {
        this.gkey = gkey;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj ==this) return true;
        if(!(obj instanceof ComponentCompos)) return false;
        ComponentCompos comp = (ComponentCompos)obj;
        if((comp.name==name)&&(comp.date==date)&&(comp.amount==amount)&&(comp.gkey==gkey)&&(comp.producer==producer)&&(comp.price_per_kilo==price_per_kilo))
            return true;

        return false;
    }
}
