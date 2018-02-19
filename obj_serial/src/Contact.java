import java.io.Serializable;

class Contact implements Serializable {

    private String  name;
    private int     phone;
    private String  email;
    private boolean family;

    Contact(String name, int phone) {
        this.name = name;
        if (phone < 0)
            this.phone = 0;
        else
            this.phone = phone;
        this.email = "";
        this.family = false;
    }
    Contact() {
        this("", 0);
    }

    String getName() {
        return name;
    }

    int getPhone() {
        return phone;
    }

    String getEmail() {
        return email;
    }

    boolean isFamily() {
        return family;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        if (phone >= 0)
            this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", family=" + family +
                '}';
    }
}
