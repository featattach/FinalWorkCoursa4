package lvov.finalwork.Controller;

import lombok.extern.slf4j.Slf4j;

import lvov.finalwork.entity.Book;

import lvov.finalwork.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public ModelAndView getAllBooks(){
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-book");
        mav.addObject("books", bookRepository.findAll());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm(){
        ModelAndView mav = new ModelAndView("add-book-form");
        Book book = new Book();
        mav.addObject("book", book);
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId){
        ModelAndView mav = new ModelAndView("add-book-form");
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()){
            book = optionalBook.get();
        }
        mav.addObject("book", book);
        return mav;
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId){
        bookRepository.deleteById(bookId);
        return "redirect:/list";
    }
}
