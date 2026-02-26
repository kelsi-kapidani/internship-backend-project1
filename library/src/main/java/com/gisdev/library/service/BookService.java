package com.gisdev.library.service;

import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.BookDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.BookMapper;
import com.gisdev.library.repository.BookRepository;
import com.gisdev.library.service.iservice.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Long createBook(BookCUDTO request) {

        if (existsByTitle(request.title())) {
            throw new BadRequestException("Book with this title already exists");
        }
        Book book = bookMapper.toEntity(request);
        bookRepository.save(book);
        return book.getId();
    }

    @Override
    public Long updateBook(Long id, BookCUDTO request) {

        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new BadRequestException("Book with this id does not exist");
        }

        bookMapper.updateBookFromDto(request, book);
        bookRepository.save(book);
        return id;
    }

    @Override
    public Long deleteBook(Long id) {

        if (!existsById(id)) {
            throw new BadRequestException("Book with this id does not exist");
        }
        bookRepository.deleteById(id);
        return id;
    }

    @Override
    public List<BookDTO> getAllBooks(List<String> filters, String sort) {

        List<BookDTO> response = new ArrayList<>();
        for (Book book: bookRepository.findAll(genSpecs(filters), genSort(sort))) {
            response.add(bookMapper.toDto(book));
        }
        return response;
    }

    public Sort genSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("title").ascending();
        }
        String[] parts = sort.split(":");
        if(!allowedFields.contains(parts[0])) {
            throw new BadRequestException("The sorting field is not legal");
        }
        if(parts[1].equals("asc")) {
            return Sort.by(parts[0]).ascending();
        }
        return Sort.by(parts[0]).descending();
    }

    public Specification<Book> genSpecs(List<String> filters) {

        if (filters == null || filters.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        if (filters.get(0).isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String[] filtersArray = filters.getFirst().split(",");
        Specification<Book> specs = (root, query, cb) -> cb.conjunction();
        for (String filter: filtersArray) {
            String[] parts = filter.split(":");
            if(!allowedFields.contains(parts[0])) {
                throw new BadRequestException("The filtering field"+parts[0]+"is not legal");
            }
            switch (parts[1]) {
                case "eq":
                    specs = specs.and((root, query, cb) -> cb.equal(root.get(parts[0]), parts[2]));
                    break;
                case "neq":
                    specs = specs.and((root, query, cb) -> cb.notEqual(root.get(parts[0]), parts[2]));
                    break;
                case "gt":
                    specs = specs.and((root, query, cb) -> cb.greaterThan(root.get(parts[0]), parts[2]));
                    break;
                case "geq":
                    specs = specs.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(parts[0]), parts[2]));
                    break;
                case "lt":
                    specs = specs.and((root, query, cb) -> cb.lessThan(root.get(parts[0]), parts[2]));
                    break;
                case "leq":
                    specs = specs.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(parts[0]), parts[2]));
                    break;
                case "ilike":
                    specs = specs.and((root, query, cb) -> cb.like(cb.lower(root.get(parts[0])), "%" + parts[2].toLowerCase() + "%"));
                    break;
                default:
                    throw new BadRequestException("The operator"+parts[1]+"is not legal");
            }
        }
        return specs;
    }

    private static final Set<String> allowedFields = Set.of(
            "title", "author", "genre", "section", "price", "year_of_publication"
    );

    private static final Set<String> allowedOperators = Set.of(
            "eq", "neq", "gt", "geq", "lt", "leq", "ilike"
    );

}