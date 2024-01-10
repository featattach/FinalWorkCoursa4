
package lvov.finalwork.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

 @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<BookShop> bookShops = new ArrayList<>();

    // Методы 'addBook' и 'removeBook' для удобной работы с ассоциациями
    public void addBookShop(BookShop bookShop) {
        bookShops.add(bookShop);
        bookShop.setShop(this);
    }

    public void removeBookShop(BookShop bookShop) {
        bookShops.remove(bookShop);
        bookShop.setShop(null);
    }
}
