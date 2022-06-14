package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Shop;
import com.netcracker.responce.DeleteResponse;
import com.netcracker.responce.payload.NameOfShopInDistrict3B;
import com.netcracker.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShopRestController {
    @Autowired
    ShopService shopService;

    @GetMapping("/shop")
    public List<Shop> getAllShops(){
        return shopService.findAll();
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Shop shop = shopService.findById(id);
        return ResponseEntity.ok(shop);
    }

    @PostMapping("/shop")
    public Shop createShop(@RequestBody Shop shop){
        return shopService.save(shop);
    }

    @PatchMapping(path = "/shop/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Shop> updateContact(
            @PathVariable Long id,
            @RequestBody JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Shop shopPatched = shopService.updateContact(id, mergePatchDocument);
        return ResponseEntity.ok(shopPatched);
    }

    @DeleteMapping("/shop/{id}")
    public ResponseEntity<DeleteResponse> deleteShopById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        shopService.deleteById(id);
        return ResponseEntity.ok(new DeleteResponse("Shop with id: " + id + " was delete"));
    }

    // Задание 3.b
    @GetMapping("/shop/resp/3/b/{district}")
    public List<NameOfShopInDistrict3B> getNameLocatedIn(@PathVariable(value = "district") String district) throws ResourceNotFoundException {
        List<NameOfShopInDistrict3B> responceList = shopService.getNameLocatedIn(district);
        return responceList;
    }

    // Задание 5.c
    @GetMapping("/shop/resp/5/c/{district}/{minDiscount}/{maxDiscount}")
    public List<Shop> getShopWihtoutDistrictAndWhereBuyerHaveDiscount(
            @PathVariable(value = "district") String district,
            @PathVariable(value = "minDiscount") byte minDiscount,
            @PathVariable(value = "maxDiscount") byte maxDiscount){
        List<Shop> responceList = shopService.getShopWihtoutDistrictAndWhereBuyerHaveDiscount(district, minDiscount, maxDiscount);
        return responceList;
    }
}
