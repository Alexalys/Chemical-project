public class ProducerCompos {
    private String name;
    private String city;
    private String phone;
    private String contact_face;

    public ProducerCompos(String name, String city, String phone, String contact_face) {
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.contact_face = contact_face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact_face() {
        return contact_face;
    }

    public void setContact_face(String contact_face) {
        this.contact_face = contact_face;
    }
}
