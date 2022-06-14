package com.netcracker.service;

import com.netcracker.component.JsonPatch;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Purchase;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.responce.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    JsonPatch jsonPatch;

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long id) throws ResourceNotFoundException {
        return purchaseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Purchase not found for id: " + id));
    }

    public Purchase save(Purchase purchase){
        return purchaseRepository.save(purchase);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Purchase purchase = findById(id);
        purchaseRepository.delete(purchase);
    }

    public Purchase updateContact(Long id, JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Purchase purchase = findById(id);
        Purchase purchasePatched = jsonPatch.mergePatch(mergePatchDocument, purchase, Purchase.class);
        return purchaseRepository.save(purchasePatched);
    }

    // Задание 2.c
    public List<MonthWhenWasMadePurchase2C> getMonthOfPurchase(){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<MonthWhenWasMadePurchase2C> responceList = purchases.stream()
                .map(purchase -> {
                    return MonthWhenWasMadePurchase2C.builder()
                            .month(getMonth(purchase.getDate()))
                            .build();
                })
                .distinct()
                .collect(Collectors.toList());
        return responceList;
    }

    private String getMonth(Date date){            ;
        switch (date.getMonth()){
            case (0): return "Январь";
            case (1): return "Февраль";
            case (2): return "Март";
            case (3): return "Апрель";
            case (4): return "Май";
            case (5): return "Июнь";
            case (6): return "Июль";
            case (7): return "Август";
            case (8): return "Сентябрь";
            case (9): return "Октябрь";
            case (10): return "Ноябрь";
            case (11): return "Декабрь";
            default: return "Неизвестный месяц";
        }
    }

    // Задание 4.a
    public List<SurnameOfCustomerAndNameOfShopWhereWasPurchase4A> getSurnameCustomerAndNameShop(){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<SurnameOfCustomerAndNameOfShopWhereWasPurchase4A> responceList = purchases.stream()
                .map(purchase -> {
                    return SurnameOfCustomerAndNameOfShopWhereWasPurchase4A.builder()
                            .titleBook(purchase.getBook().getTitle())
                            .nameShop(purchase.getShop().getName())
                            .build();
                })
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 4.b
    public List<DateSurnameDiscountTitleAndQuantityOfBook4B> getDateSurnameDiscountTitleBookQuantity(){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<DateSurnameDiscountTitleAndQuantityOfBook4B> responceList = purchases.stream()
                .map(purchase -> {
                    return DateSurnameDiscountTitleAndQuantityOfBook4B.builder()
                            .date(purchase.getDate())
                            .surname(purchase.getCustomer().getSurname())
                            .discount(purchase.getCustomer().getDiscount())
                            .titleBook(purchase.getBook().getTitle())
                            .quantity(purchase.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 5.a
    public List<SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A> getPurchaseIdSurnameDateByPriceGreaterThan(int price){
        List<Purchase> purchases = purchaseRepository.findPurchasesByTotalPriceGreaterThan(price);
        List<SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A> responceList = purchases.stream()
                .map(purchase -> {
                    return SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A.builder()
                            .purchaseId(purchase.getId())
                            .surname(purchase.getCustomer().getSurname())
                            .date(purchase.getDate())
                            .build();
                })
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 5.b
    public List<SurnameDistrictDateWherePurchaseAfterMonth5B> getSurnameDistrictDateBeforeMonth(int month){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<SurnameDistrictDateWherePurchaseAfterMonth5B> responceList = purchases.stream()
                .filter(purchase -> {
                    return purchase
                            .getCustomer()
                            .getDistrict()
                            .equals(purchase.getShop().getDistrict())
                            && purchase.getDate().getMonth() >= month-1;
                })
                .map(purchase -> {
                    return SurnameDistrictDateWherePurchaseAfterMonth5B.builder()
                            .surname(purchase.getCustomer().getSurname())
                            .district(purchase.getCustomer().getDistrict())
                            .date(purchase.getDate())
                            .build();
                })
                .sorted()
                .collect(Collectors.toList());
        return responceList;
    }
}
