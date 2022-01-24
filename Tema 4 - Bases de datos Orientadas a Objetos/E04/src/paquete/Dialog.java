package paquete;

public class Dialog {
	private String id;
	private String dialog;
	private Movie movie;
	private Character character;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDialog() {
		return dialog;
	}
	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public Dialog(String id, String dialog, Movie movie, Character character) {
		super();
		this.id = id;
		this.dialog = dialog;
		this.movie = movie;
		this.character = character;
	}
	
}
