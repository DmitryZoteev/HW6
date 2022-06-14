package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Purchase;
import com.netcracker.responce.DeleteResponse;
import com.netcracker.responce.payload.*;
import com.netcracker.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PurchaseRestController {
    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/purchase")
    public List<Purchase> getAllPurchases(){
        return purchaseService.findAll();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Purchase purchase = purchaseService.findById(id);
        return ResponseEntity.ok(purchase);
    }

    @PostMapping("/purchase")
    public Purchase createPurchase(@RequestBody Purchase purchase){
        return purchaseService.save(purchase);
    }

    @PatchMapping(path = "/purchase/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Purchase> updateContact(
            @PathVariable Long id,
            @RequestBody JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Purchase purchasePatched = purchaseService.updateContact(id, mergePatchDocument);
        return ResponseEntity.ok(purchasePatched);
    }

    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<DeleteResponse> deletePurchaseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        purchaseService.deleteById(id);
        return ResponseEntity.ok(new DeleteResponse("Purchase with id: " + id + " was delete"));
    }

    // Задание 2.c
    @GetMapping("/purchase/resp/2/c")
    public List<MonthWhenWasMadePurchase2C> getMontOfPurchase(){
        List<MonthWhenWasMadePurchase2C> responceList = purchaseService.getMonthOfPurchase();
        return responceList;
    }

    // Задание 4.a
    @GetMapping("/purchase/resp/4/a")
    public List<SurnameOfCustomerAndNameOfShopWhereWasPurchase4A> getSurnameCustomerAndNameShop(){
        List<SurnameOfCustomerAndNameOfShopWhereWasPurchase4A> responceList = purchaseService.getSurnameCustomerAndNameShop();
        return responceList;
    }

    // Задание 4.b
    @GetMapping("/purchase/resp/4/b")
    public List<DateSurnameDiscountTitleAndQuantityOfBook4B> getDateSurnameDiscountTitleBookQuantity(){
        List<DateSurnameDiscountTitleAndQuantityOfBook4B> responceList = purchaseService.getDateSurnameDiscountTitleBookQuantity();
        return responceList;
    }

    // Задание 5.a
    @GetMapping("/purchase/resp/5/a/{price}")
    public List<SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A> getPurchaseIdSurnameDateByPriceGreaterThan(@PathVariable(value = "price") int price){
        List<SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A> responceList = purchaseService.getPurchaseIdSurnameDateByPriceGreaterThan(price);
        return responceList;
    }

    // Задание 5.b
    @GetMapping("/purchase/resp/5/b/{month}")
    public List<SurnameDistrictDateWherePurchaseAfterMonth5B> getSurnameDistrictDateBeforeMonth(@PathVariable(value = "month")int month){
        List<SurnameDistrictDateWherePurchaseAfterMonth5B> responceList = purchaseService.getSurnameDistrictDateBeforeMonth(month);
        return responceList;
    }
}
