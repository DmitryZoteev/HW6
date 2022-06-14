package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Book;
import com.netcracker.responce.DeleteResponse;
import com.netcracker.responce.payload.BookInfo2A;
import com.netcracker.responce.payload.TitleAndPriceOfBookWithParameters3C;
import com.netcracker.responce.payload.TitleRepoCountWhereQuantityOfBookLessValue5D;
import com.netcracker.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class BookRestController {
    @Autowired
    BookService bookService;

    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @PatchMapping(path = "/book/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Book> updateContact(
            @PathVariable Long id,
            @RequestBody JsonMergePatch mergePatchDocument) throws ResourceNotFoundException {
        Book bookPatched = bookService.updateContact(id, mergePatchDocument);
        return ResponseEntity.ok(bookPatched);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<DeleteResponse> deleteBookById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        bookService.deleteById(id);
        return ResponseEntity.ok(new DeleteResponse("Book with id: " + id + " was delete"));
    }

    // Задание 2.a
    @GetMapping("/book/resp/2/a")
    public List<BookInfo2A> getTitleAndPriceOfBook(){
        List<BookInfo2A> responceList = bookService.getTitleAndPriceOfBook();
        return responceList;
    }

    // Задание 3.c
    @GetMapping("/book/resp/3/c/{title}/{price}")
    public List<TitleAndPriceOfBookWithParameters3C> findBookByTitleOrPriceGreaterThan(
            @PathVariable(value = "title") String title,
            @PathVariable(value = "price") int price) throws ResourceNotFoundException {
        List<TitleAndPriceOfBookWithParameters3C> responceList = bookService.findBookByTitleOrPriceGreaterThan(title, price);
        return responceList;
    }

    // Задание 5.d
    @GetMapping("/book/resp/5/d/{count}")
    public List<TitleRepoCountWhereQuantityOfBookLessValue5D> getBooksWhereCountLessThan(
            @PathVariable(value = "count") int count) {
        List<TitleRepoCountWhereQuantityOfBookLessValue5D> responceList = bookService.getBooksWhereCountLessThan(count);
        return responceList;
    }
}
