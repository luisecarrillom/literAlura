
import javax.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import java.util.Scanner;

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String language;
    private Integer downloadCount;

    // Constructors, getters, and setters
    public Book() {}

    public Book(String title, String author, String language, Integer downloadCount) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}

interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
}

@Service
class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }
}

@Component
class BookController implements CommandLineRunner {
    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book title to search:");
        String title = scanner.nextLine();

        Book book = bookService.findBookByTitle(title);
        if (book != null) {
            System.out.println("Book found: " + book.getTitle() + " by " + book.getAuthor());
        } else {
            System.out.println("Book not found, registering new book.");
            // Simulate book data
            book = new Book(title, "Austen, Jane", "EN", 6493);
            bookService.registerBook(book);
        }
    }
}

@SpringBootApplication
public class LiterAluraApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }
}
