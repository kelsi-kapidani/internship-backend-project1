package com.gisdev.library.service;

import com.gisdev.library.dto.request.book.BaseBookRequestDTO;
import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.BookRepository;
import com.gisdev.library.service.iservice.IAuthService;
import com.gisdev.library.service.iservice.IBookService;
import com.gisdev.library.service.iservice.ILibraryUserService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    private final ILibraryUserService userService;
    private final IAuthService authService;

    @Override
    public void existsByTitle(String title) {
        if (bookRepository.existsByTitle(title)) {
            throw new BadRequestException("Book with this title already exists");
        }
    }

    @Override
    public void existsById(Long id) {
        if(bookRepository.existsById(id)) {
            throw new BadRequestException("Book with this id already exists");
        }
    }

    @Override
    public void existsNotById(Long id) {
        if(!bookRepository.existsById(id)) {
            throw new BadRequestException("No book with this id exists");
        }
    }

    @Override
    public Optional<Book> getBookById(Long id) {return bookRepository.findById(id); }

    @Override
    public List<Book> getAllWithLibraryBooks() {
        return bookRepository.findAllWithLibraryBooks();
    }

    @Override
    public Long createBook(BaseBookRequestDTO request) {
        existsByTitle(request.getTitle());

        Book book = mapper.map(request, Book.class);
        bookRepository.save(book);

        return book.getId();
    }

    @Override
    public Long updateBook(Long id, BaseBookRequestDTO request) {
        Book book = getBookById(id).orElseThrow(() -> new BadRequestException("Book with this id does not exist"));
        if(!request.getTitle().equals(book.getTitle())) {
            existsByTitle(request.getTitle());
        }

        mapper.map(request, book);
        bookRepository.save(book);

        return id;
    }

    @Override
    public Long deleteBook(Long id) {
        existsNotById(id);
        bookRepository.deleteById(id);
        return id;
    }

    @Override
    public List<BaseBookResponseDTO> getAllBooks(List<String> filters, String sort) {

        LibraryUser currentUser = authService.getUserByToken();
        List<BaseBookResponseDTO> response = new ArrayList<>();

        Long libraryId;
        if (currentUser.getRole().name().equals("ADMIN")) {
            libraryId = (long)-1;
        } else {
            libraryId = currentUser.getId();
        }

        for (Book book: bookRepository.findAll(genSpecs(filters, libraryId), genSort(sort))) {
            response.add(mapper.map(book, BaseBookResponseDTO.class));
        }
        /*
        for (Book book: bookRepository.findAll(genSpecs(filters), genSort(sort))) {
            response.add(mapper.map(book, FullBookResponseDTO.class));
        }
        */
        return response;
    }


    public Sort genSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("title").descending();
        }
        String[] parts = sort.split(":");
        if(!allowedFields.contains(parts[0])) {
            throw new BadRequestException("The sorting field is not legal");
        }
        if(parts[1].equals("asc")) {
            return Sort.by(parts[0]).descending();
        }

        return Sort.by(parts[0]).ascending();
    }

    public Specification<Book> genSpecs(List<String> filters, Long libraryId) {
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
                throw new BadRequestException("The filtering field"+ parts[0] +"is not legal");
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

        if (libraryId != null && libraryId != -1) {
            specs = specs.and((root, query, cb) -> {
                Join<Object, Object> librariesJoin = root.join("libraries");
                Join<Object, Object> libraryJoin = librariesJoin.join("library");

                return cb.equal(libraryJoin.get("id"), libraryId);
            });
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