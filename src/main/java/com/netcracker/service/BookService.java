package com.netcracker.service;

import com.netcracker.component.JsonPatch;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Book;
import com.netcracker.model.Purchase;
import com.netcracker.repository.BookRepository;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.responce.payload.BookInfo2A;
import com.netcracker.responce.payload.TitleAndPriceOfBookWithParameters3C;
import com.netcracker.responce.payload.TitleRepoCountWhereQuantityOfBookLessValue5D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    JsonPatch jsonPatch;

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id) throws ResourceNotFoundException {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book not found for id: " + id));
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    public Book updateContact(Long id, JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Book book = findById(id);
        Book bookPatched = jsonPatch.mergePatch(mergePatchDocument, book, Book.class);
        return bookRepository.save(bookPatched);
    }

    // Задание 2.a
    public List<BookInfo2A> getTitleAndPriceOfBook(){
        List<Book> books = bookRepository.findAll();
        List<BookInfo2A> responceList = books.stream()
                .map(book -> {
                    return BookInfo2A.builder()
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .build();
                })
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 3.c
    public List<TitleAndPriceOfBookWithParameters3C> findBookByTitleOrPriceGreaterThan(String title, int price){
        List<Book> books = bookRepository.findAll();
        List<TitleAndPriceOfBookWithParameters3C> responceList = books.stream()
                .filter(book -> {
                    return book
                            .getTitle()
                            .contains(title)
                            || book.getPrice() > price;
                })
                .map(book -> {
                    return TitleAndPriceOfBookWithParameters3C.builder()
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .build();
                })
                .sorted((el1, el2) -> el2.getPrice() - el1.getPrice())
                .collect(Collectors.toList());
        return responceList;
    }

    // Задание 5.d
    public List<TitleRepoCountWhereQuantityOfBookLessValue5D> getBooksWhereCountLessThan(int count){
        List<Purchase> purchases = purchaseRepository.findAll();
        List<TitleRepoCountWhereQuantityOfBookLessValue5D> responceList = purchases.stream()
                .filter(purchase -> {
                    return purchase
                            .getShop()
                            .getDistrict()
                            .equals(purchase.getBook().getRepo())
                            && purchase.getBook().getQuantity() <= count;
                })
                .map(purchase -> {
                    return TitleRepoCountWhereQuantityOfBookLessValue5D.builder()
                            .title(purchase.getBook().getTitle())
                            .district(purchase.getBook().getRepo())
                            .count(purchase.getBook().getQuantity())
                            .price(purchase.getBook().getPrice())
                            .build();
                })
                .sorted(Comparator.comparingInt(TitleRepoCountWhereQuantityOfBookLessValue5D::getPrice))
                .collect(Collectors.toList());
        return responceList;
    }
}
