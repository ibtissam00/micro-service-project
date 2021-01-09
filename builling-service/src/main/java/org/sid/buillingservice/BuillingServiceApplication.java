package org.sid.buillingservice;
import org.sid.buillingservice.Feign.CustomerRestClient;
import org.sid.buillingservice.Feign.ProductItemRestClient;
import org.sid.buillingservice.entities.Bill;
import org.sid.buillingservice.entities.ProductItem;
import org.sid.buillingservice.model.Customer;
import org.sid.buillingservice.model.Product;
import org.sid.buillingservice.repository.BillReposetory;
import org.sid.buillingservice.repository.ProductItemReposetory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class BuillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuillingServiceApplication.class, args);
    }
 @Bean
    CommandLineRunner start(
            BillReposetory billReposetory,
            ProductItemReposetory productItemReposetory,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient){
     return args -> {

         Customer customer=customerRestClient.getCustomerById(1L);

         Bill bill1=billReposetory.save(new Bill(null,new Date(),null,customer.getId(),null));

         PagedModel<Product> productPagedModel=productItemRestClient.pageProducts();
         System.out.println(productPagedModel.getContent().size());
         productPagedModel.forEach(p->{
             ProductItem productItem=new ProductItem();
             productItem.setPrice(p.getPrice());
             productItem.setQuantity(1+new Random().nextInt(100));
             productItem.setProductID(p.getId());
             productItem.setBill(bill1);
             productItemReposetory.save(productItem);

         });
     };

 }



}
