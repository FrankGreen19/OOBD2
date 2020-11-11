package Homeworks.Lab1;

import org.postgresql.util.PGobject;
import org.postgresql.util.PGtokenizer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.SQLException;

@Entity
@Table(name = "product")
public class Product extends PGobject implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String productType;

    @Column
    private String productBrand;

    @Column
    private double productPrice;

    public Product(String productType, String productBrand, double productPrice) {
        this.productType = productType;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
    }

    public Product() {
        setType("product");
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product: \n" +
                "type:" + this.productType + "\n" +
                "brand:" + this.productBrand + "\n" +
                "price:" + this.productPrice + "\n";
    }

    @Override
    public int compareTo(Object obj) {
        Product tmp = (Product) obj;
        if(this.getProductPrice() < tmp.getProductPrice())
        {
            /* текущее меньше полученного */
            return -1;
        }
        else if(this.productPrice > tmp.productPrice)
        {
            /* текущее больше полученного */
            return 1;
        }
        /* текущее равно полученному */
        return 0;
    }

    public void setValue(String value) throws SQLException {
        String s = value.substring(1, value.length()-1);
        PGtokenizer t = new PGtokenizer(s, ',');
        this.productType = t.getToken(0);
        this.productBrand = t.getToken(1);
        this.productPrice = Double.parseDouble(t.getToken(2));
    }

    public String getValue() {
        return "('" + productType + "','" + productBrand + "'," + productPrice +")";
    }
}
