package ma.enset.billingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.billingservice.model.Customer;

import java.util.Date;
import java.util.List;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
    @Id @GeneratedValue
    private Long id;
    private Date billingDate;
    private Long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems;
    @Transient
    private Customer customer;
}
