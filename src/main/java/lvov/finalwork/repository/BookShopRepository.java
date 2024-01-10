package lvov.finalwork.repository;

import lvov.finalwork.entity.Book;
import lvov.finalwork.entity.BookShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookShopRepository extends JpaRepository<BookShop, Long>{
    List<BookShop> findByShop_Name(String shopName);
    List<BookShop> findByBook(Book book);

    void deleteByBook(Book book);
}
