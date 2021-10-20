package ejemplojaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class Libro {
 
    private String titulo;
          
    private int paginas;
     
    public String getTitulo() {
        return titulo;
    }
     
    public Libro(String titulo, int paginas) {
        super();
        this.titulo = titulo;
        this.paginas = paginas;
    }
    public Libro() {
        super();
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @XmlElement(name="numeroPaginas")
    public int getPaginas() {
        return paginas;
    }
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
 
}