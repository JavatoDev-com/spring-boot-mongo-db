package com.javatodev.api.controller;

import com.javatodev.api.model.Author;
import com.javatodev.api.model.Book;
import com.javatodev.api.model.Member;
import com.javatodev.api.model.request.AuthorCreationRequest;
import com.javatodev.api.model.request.BookCreationRequest;
import com.javatodev.api.model.request.BookLendRequest;
import com.javatodev.api.model.request.MemberCreationRequest;
import com.javatodev.api.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity readBooks(@RequestParam(required = false) String isbn) {
        if (isbn == null) {
            return ResponseEntity.ok(libraryService.readBooks());
        }
        return ResponseEntity.ok(libraryService.readBook(isbn));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> readBook(@PathVariable String bookId) {
        return ResponseEntity.ok(libraryService.readBookById(bookId));
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    @PatchMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") String bookId, @RequestBody BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.updateBook(bookId, request));
    }

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorCreationRequest request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Member> createMember(@RequestBody @Valid MemberCreationRequest request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> readMembers() {
        return ResponseEntity.ok(libraryService.readMembers());
    }

    @PatchMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember(@RequestBody @Valid MemberCreationRequest request, @PathVariable String memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }

    @PostMapping("/book/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody @Valid BookLendRequest bookLendRequests) {
        return ResponseEntity.ok(libraryService.lendABook(bookLendRequests));
    }
}
