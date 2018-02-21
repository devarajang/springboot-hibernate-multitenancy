package cloudinvoice.wildfire.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;    
    
    @ColumnTransformer(read = "aes_decrypt(from_base64(?),'12345678')", write = "to_base64(aes_encrypt(?,'12345678'))")
    private String idnumber;

    protected Customer() {}

    public Customer(String firstName, String lastName,String idnumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idnumber = idnumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s' , idnumber='%s']",
                id, firstName, lastName,idnumber);
    }

}