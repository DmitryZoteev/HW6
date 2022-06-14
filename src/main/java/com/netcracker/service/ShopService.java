package com.netcracker.service;

import com.netcracker.component.JsonPatch;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.repository.ShopRepository;
import com.netcracker.responce.payload.NameOfShopInDistrict3B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    JsonPatch jsonPatch;

    public List<Shop> findAll(){
        return shopRepository.findAll();
    }

    public Shop findById(Long id) throws ResourceNotFoundException {
        return shopRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Shop not found for id: " + id));
    }

    public Shop save(Shop shop){
        return shopRepository.save(shop);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Shop shop = findById(id);
        shopRepository.delete(shop);
    }

    public Shop updateContact(Long id, JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Shop shop = findById(id);
        Shop shopPatched = jsonPatch.mergePatch(mergePatchDocument, shop, Shop.class);
        return shopRepository.save(shopPatched);
    }

    // Задание 3.b
    public List<NameOfShopInDistrict3B> getNameLocatedIn(String district){
        List<Shop> shops = shopRepository.findCustomerByDistrict(district);
        List<NameOfShopInDistrict3B> responceList = shops.stream()
                .map(shop -> {
                    return NameOfShopInDistrict3B.builder()
                            .name(shop.getName())
                            .build();
                })
                .distinct()
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 5.c
    public List<Shop> getShopWihtoutDistrictAndWhereBuyerHaveDiscount(String district, byte minDiscount, byte maxDiscount){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Shop> responceList = purchases.stream()
                .filter(purchase -> {
                    return !purchase
                            .getShop()
                            .getDistrict()
                            .equals(district)
                            && purchase.getCustomer().getDiscount() > minDiscount
                            && purchase.getCustomer().getDiscount() < maxDiscount;
                })
                .map(purchase -> purchase.getShop())
                .collect(Collectors.toList());
        return responceList;
    }
}
