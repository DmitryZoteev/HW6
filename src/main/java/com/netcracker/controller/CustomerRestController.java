package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Customer;
import com.netcracker.responce.DeleteResponse;
import com.netcracker.responce.payload.DistrictWhereCustomerLives2B;
import com.netcracker.responce.payload.SurnameAndDiscountOfCustomerWhoLivesInDistrict3A;
import com.netcracker.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class CustomerRestController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    public List<Customer> getAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @PatchMapping(path = "/customer/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Customer> updateContact(
            @PathVariable Long id,
            @RequestBody JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Customer customerPatched = customerService.updateContact(id, mergePatchDocument);
        return ResponseEntity.ok(customerPatched);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<DeleteResponse> deleteCustomerById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        customerService.deleteById(id);
        return ResponseEntity.ok(new DeleteResponse("Customer with id: " + id + " was delete"));
    }

    // Задание 2.b
    @GetMapping("/customer/resp/2/b")
    public List<DistrictWhereCustomerLives2B> getDistrictOfCustomer(){
        List<DistrictWhereCustomerLives2B> responceList = customerService.getDistrictOfCustomer();
        return responceList;
    }

    // Задание 3.a
    @GetMapping("/customer/resp/3/a/{district}")
    public List<SurnameAndDiscountOfCustomerWhoLivesInDistrict3A> getSurnameDiscountOfCustomerLivedIn(@PathVariable(value = "district") String district) throws ResourceNotFoundException {
        List<SurnameAndDiscountOfCustomerWhoLivesInDistrict3A> responceList = customerService.getSurnameDiscountOfCustomerLivedIn(district);
        return responceList;
    }
}
