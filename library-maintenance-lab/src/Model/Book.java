package Model;
import Util.DataUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Book {

    private static final Logger logger = LogManager.getLogger(Book.class);

    private String title;
    private String author;
    private int year;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private String shelfCode;
    private String isbn;
    int id;

    public Book(int id, String title, String author, int year, String category, int totalCopies, int availableCopies, String shelfCode, String isbn) {
        try {
            // 1. Validações críticas (Princípio Fail-Fast)
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Title is required and must not be blank.");
            }
            if (author == null || author.trim().isEmpty()) {
                throw new IllegalArgumentException("Author is required and must not be blank.");
            }
            if (totalCopies <= 0) {
                throw new IllegalArgumentException("Total copies must be greater than zero.");
            }
            if (availableCopies < 0) {
                throw new IllegalArgumentException("Available copies must not be negative.");
            }
            if (availableCopies > totalCopies) {
                throw new IllegalArgumentException("Available copies cannot exceed total copies.");
            }

            // 2. Atribuições com lógica de "fallback"
            this.title = title;
            this.author = author;
            this.year = (year < 0) ? 1900 : year;
            this.category = (category == null) ? "GENERAL" : category;
            this.totalCopies = totalCopies;
            this.availableCopies = availableCopies;
            this.shelfCode = DataUtil.isBlank(shelfCode) ? "X0" : shelfCode;
            this.isbn = DataUtil.isBlank(isbn) ? "NO-ISBN" : isbn;
            this.id = id;
            logger.info("Book criado com sucesso: id={} isbn={}", this.id, this.isbn);
        } catch (IllegalArgumentException exception) {
            logger.error("Falha ao criar Book id={} isbn={}", id, isbn, exception);
            throw exception;
        }
    }

    public void borrowCopy() {
        if (this.availableCopies <= 0) {
            throw new IllegalStateException("Não há cópias disponíveis para empréstimo.");
        }
        this.availableCopies--;
    }

    public void returnCopy() {
        if (this.availableCopies < this.totalCopies) {
            this.availableCopies++;
        }
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

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getShelfCode() {
        return shelfCode;
    }

    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}