// Указывает путь к пакету
package lvov.finalwork.entity;

// Импорты Lombok аннотаций и JPA аннотаций
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Аннотации Lombok для автоматической генерации нужного кода
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

    // Ассоциация многие-ко-многим с книгами
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "shop_books",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    // Методы 'addBook' и 'removeBook' для удобной работы с ассоциациями
    public void addBook(Book book) {
        this.books.add(book);
        book.getShops().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getShops().remove(this);
    }
}
