package org.sid.buillingservice.web;

import org.sid.buillingservice.Feign.CustomerRestClient;
import org.sid.buillingservice.Feign.ProductItemRestClient;
import org.sid.buillingservice.entities.Bill;
import org.sid.buillingservice.model.Customer;
import org.sid.buillingservice.model.Product;
import org.sid.buillingservice.repository.BillReposetory;
import org.sid.buillingservice.repository.ProductItemReposetory;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Currency;

@RestController
public class BillingRestController {
    private BillReposetory billReposetory;
    private ProductItemReposetory productItemReposetory;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

 public BillingRestController(BillReposetory billReposetory, ProductItemReposetory productItemReposetory,
                              CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billReposetory = billReposetory;
        this.productItemReposetory = productItemReposetory;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
  public Bill getBill(@PathVariable(name="id") Long id){

        Bill bill=billReposetory.findById(id).get();
 Customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi ->{
            Product product=productItemRestClient.getProductById(pi.getProductID());
            pi.setProduct(product);
        } );

        return  bill;
  }


}
