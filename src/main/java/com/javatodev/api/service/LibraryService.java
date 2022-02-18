package com.javatodev.api.service;

import com.javatodev.api.exception.EntityNotFoundException;
import com.javatodev.api.exception.InvalidMemberStatusException;
import com.javatodev.api.model.*;
import com.javatodev.api.model.request.AuthorCreationRequest;
import com.javatodev.api.model.request.BookCreationRequest;
import com.javatodev.api.model.request.BookLendRequest;
import com.javatodev.api.model.request.MemberCreationRequest;
import com.javatodev.api.repository.AuthorRepository;
import com.javatodev.api.repository.BookRepository;
import com.javatodev.api.repository.LendRepository;
import com.javatodev.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book readBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book.orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    public Book createBook(BookCreationRequest book) {
        Optional<Author> authorOptional = authorRepository.findById(book.getAuthorId());
        Author author = authorOptional.orElseThrow(() -> new EntityNotFoundException("Author"));

        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author);
        return bookRepository.save(bookToCreate);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember(String id, MemberCreationRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = optionalMember.orElseThrow(() -> new EntityNotFoundException("Member"));
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }

    public Author createAuthor(AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }

    public List<String> lendABook(BookLendRequest request) {

        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        Member member = memberForId.orElseThrow(() -> new EntityNotFoundException("Member"));
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new InvalidMemberStatusException("User is not active to proceed a lending.");
        }

        List<String> booksApprovedToBorrow = new ArrayList<>();
        request.getBookIds().forEach(bookId -> {
            Optional<Book> bookForId = bookRepository.findById(bookId);
            Book book = bookForId.orElseThrow(() -> new EntityNotFoundException("Book"));
            Optional<Lend> borrowedBook = lendRepository.findByBookAndStatus(book, LendStatus.BORROWED);
            if (!borrowedBook.isPresent()) {
                booksApprovedToBorrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setStatus(LendStatus.BORROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }

        });
        return booksApprovedToBorrow;
    }

    public Book updateBook(String bookId, BookCreationRequest request) {
        Optional<Author> optionalAuthor = authorRepository.findById(request.getAuthorId());
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Author author = optionalAuthor.orElseThrow(() -> new EntityNotFoundException("Author"));
        Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException("Book"));
        book.setIsbn(request.getIsbn());
        book.setName(request.getName());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public List<Member> readMembers() {
        return memberRepository.findAll();
    }


}
