package com.netcracker.service;

import com.netcracker.component.JsonPatch;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.responce.payload.DistrictWhereCustomerLives2B;
import com.netcracker.responce.payload.SurnameAndDiscountOfCustomerWhoLivesInDistrict3A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JsonPatch jsonPatch;

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found for id: " + id));
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    public Customer updateContact(Long id, JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Customer customer = findById(id);
        Customer customerPatched = jsonPatch.mergePatch(mergePatchDocument, customer, Customer.class);
        return customerRepository.save(customerPatched);
    }

    // Задание 2.b
    public List<DistrictWhereCustomerLives2B> getDistrictOfCustomer(){
        List<Customer> customers = customerRepository.findAll();
        List<DistrictWhereCustomerLives2B> responceList = customers.stream()
                .map(customer -> {
                    return DistrictWhereCustomerLives2B.builder()
                            .district(customer.getDistrict())
                            .build();
                })
                .distinct()
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 3.a
    public List<SurnameAndDiscountOfCustomerWhoLivesInDistrict3A> getSurnameDiscountOfCustomerLivedIn(String district) throws ResourceNotFoundException {
        List<Customer> customers = customerRepository.findCustomerByDistrict(district);
        List<SurnameAndDiscountOfCustomerWhoLivesInDistrict3A> responceList = customers.stream()
                .map(customer -> {
                    return SurnameAndDiscountOfCustomerWhoLivesInDistrict3A.builder()
                            .surname(customer.getSurname())
                            .discount(customer.getDiscount())
                            .build();
                })
                .collect(Collectors.toList());
        return responceList;
    }
}
