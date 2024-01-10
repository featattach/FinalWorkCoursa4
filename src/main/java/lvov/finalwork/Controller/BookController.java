package lvov.finalwork.Controller;

import lombok.extern.slf4j.Slf4j;

import lvov.finalwork.config.Auth;
import lvov.finalwork.entity.Book;

import lvov.finalwork.entity.BookShop;
import lvov.finalwork.entity.Shop;
import lvov.finalwork.repository.BookRepository;
import lvov.finalwork.repository.BookShopRepository;
import lvov.finalwork.repository.ShopRepository;
import lvov.finalwork.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private BookShopRepository bookShopRepository;

    @Autowired
    private Auth authentication;

    @Autowired
    private LoggingService logService;

    @GetMapping("/list")
    public ModelAndView getAllBooks() {
        log.info("/list-books -> connection");
        ModelAndView mav = new ModelAndView("list-book");
        mav.addObject("books", bookRepository.findAll());
        mav.addObject("shops", shopRepository.findAll());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm() {
        if (authentication.getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("READ_ONLY"))) {
            return new ModelAndView("redirect:/list");
        } else {
            ModelAndView mav = new ModelAndView("add-book-form");
            Book book = new Book();
            mav.addObject("book", book);
            mav.addObject("shops", shopRepository.findAll());
            return mav;
        }
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book, @RequestParam List<Long> shopIds) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        book.setCreated(currentPrincipalName);
        bookRepository.save(book);

        // Связываем книгу с магазинами
        for (Long shopId : shopIds) {
            Shop shop = shopRepository.findById(shopId).orElseThrow();
            bookShopRepository.save(new BookShop(book, shop));
        }

        logService.logAction(currentPrincipalName, "Добавил книгу");
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (book.getCreated().equals(currentPrincipalName))) {
                // Очищаем связанные магазины
                book.getBookShops().clear();

                ModelAndView mav = new ModelAndView("add-book-form");
                mav.addObject("book", book);
                mav.addObject("shops", shopRepository.findAll());
                logService.logAction(currentPrincipalName, "Изменил книгу");
                return mav;
            } else {
                return new ModelAndView("redirect:/list");
            }
        } else {
            return new ModelAndView("redirect:/list");
        }
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (book.getCreated().equals(currentPrincipalName))) {

                book.getBookShops().clear();
                bookRepository.deleteById(bookId);
                logService.logAction(currentPrincipalName, "Удалил книгу");
            }
        }
        return "redirect:/list";
    }
}