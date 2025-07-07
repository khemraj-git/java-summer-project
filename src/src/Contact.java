// Contact.java
import java.io.Serializable;

class Contact {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String dob;

    public Contact(String name, String phone, String email, String address, String dob) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getDob() { return dob; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setDob(String dob) { this.dob = dob; }


    @Override
    public String toString() {
        return name + " - " + phone + " - " + email + " - " + address + " - " + dob;
    }

}


