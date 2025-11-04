package ma.enset.billingservice.web;

import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.feign.CustomerServiceRestClient;
import ma.enset.billingservice.feign.InventoryServiceRestClient;
import ma.enset.billingservice.model.Customer;
import ma.enset.billingservice.repository.BillRepository;
import ma.enset.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerServiceRestClient customerServiceRestClient;
    @Autowired
    private InventoryServiceRestClient inventoryServiceRestClient;

    @GetMapping("/bills/{id}")
    public Bill getBillById(@PathVariable long id) {
        Bill bill = billRepository.findById(id).get();
        Customer customer =
                customerServiceRestClient.findCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryServiceRestClient.getProduct(pi.getProductId()));

        });
        return bill;

    }
}
