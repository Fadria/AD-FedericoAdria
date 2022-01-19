package paquete;

public class Chapter {
	private String id;
	private String chapter_name;
	private Book book;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Chapter(String id, String chapter_name, Book book) {
		super();
		this.id = id;
		this.chapter_name = chapter_name;
		this.book = book;
	}
	
}
