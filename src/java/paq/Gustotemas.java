package paq;

import java.util.List;


public class Gustotemas {
    
    private String id  ;
    private String idUsuario ;
    private String idtema ;
    private String tema ;

    public Gustotemas(String id, String idUsuario, String idtema, String tema) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idtema = idtema;
        this.tema = tema;
    }
    
     public Gustotemas( String idUsuario, String idtema, String tema) {  
        this.idUsuario = idUsuario;
        this.idtema = idtema;
        this.tema = tema;
    }
    
    
    
    Gustotemas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    Gustotemas(List<Gustotemas> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdtema() {
        return idtema;
    }

    public void setIdtema(String idtema) {
        this.idtema = idtema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String toString() {
        return "Gustotemas{" + "id=" + id + ", idUsuario=" + idUsuario + ", idtema=" + idtema + ", tema=" + tema + '}';
    }

}
