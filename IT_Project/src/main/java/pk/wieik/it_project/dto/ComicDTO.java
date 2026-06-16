package pk.wieik.it_project.dto;

/**
 * Representa una fila de la tabla `comics`.
 * Tabla añadida por nosotros para cubrir el dominio del proyecto.
 */
public class ComicDTO {
    private int id;
    private String title;
    private String series;
    private String cartoonist;   // autor del dibujo
    private String writer;       // guionista
    private String publisher;
    private String releaseDate;  // formato ISO YYYY-MM-DD
    private String dateAdded;    // formato ISO YYYY-MM-DD
    private String description;

    public ComicDTO() {
    }

    public ComicDTO(String title, String series, String cartoonist, String writer,
                    String publisher, String releaseDate, String description) {
        this.title = title;
        this.series = series;
        this.cartoonist = cartoonist;
        this.writer = writer;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getCartoonist() { return cartoonist; }
    public void setCartoonist(String cartoonist) { this.cartoonist = cartoonist; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "ComicDTO{id=" + id +
                ", title='" + title + '\'' +
                ", series='" + series + '\'' +
                ", cartoonist='" + cartoonist + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseDate='" + releaseDate + '\'' + '}';
    }
}
