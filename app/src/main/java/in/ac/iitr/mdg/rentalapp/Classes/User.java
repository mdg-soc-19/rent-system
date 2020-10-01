package in.ac.iitr.mdg.rentalapp.Classes;

public class User {

    private String Name, Email, City, ContactNo;

    public User() {
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getContactNo() {
        return ContactNo;
    }

     public String getName() {
        return Name;
    }

     public String getEmail() {
        return Email;
    }

     public String getCity() {
        return City;
    }
}
