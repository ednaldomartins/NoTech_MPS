package projetomps.com.notech.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//baseado no google news
public class Noticia implements Parcelable {

    private String fonteId, fonteNome, autor, titulo, descricao, url, urlImagem, data;
    private List<Integer> comentarios;

    public Noticia() {
        this.fonteId = "";
        this.fonteNome = "";
        this.autor = "";
        this.titulo = "";
        this.descricao = "";
        this.url = "";
        this.urlImagem = "";
        this.data = "";
        this.comentarios = null;
    }

    public Noticia(String autor, String titulo, String descricao, String url, String urlImagem, String data) {
        this.autor = autor;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.urlImagem = urlImagem;
        this.data = data;
    }

    protected Noticia(Parcel in) {
        fonteId = in.readString();
        fonteNome = in.readString();
        autor = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        url = in.readString();
        urlImagem = in.readString();
        data = in.readString();
    }

    public static final Creator<Noticia> CREATOR = new Creator<Noticia>() {
        @Override
        public Noticia createFromParcel(Parcel in) {
            return new Noticia(in);
        }

        @Override
        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };

    public String getFonteId() {
        return fonteId;
    }

    public void setFonteId(String fonteId) {
        this.fonteId = fonteId;
    }

    public String getFonteNome() {
        return fonteNome;
    }

    public void setFonteNome(String fonteNome) {
        this.fonteNome = fonteNome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Integer> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Integer> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Parser de noticia no Json
     * @param artigo Json contendo artigo
     * @return Instancia de Noticia
     */
    public static Noticia fromJson(JSONObject artigo) {
        Noticia noticia = new Noticia();
        JSONObject fonte = null;

        try {
            fonte = artigo.getJSONObject("source");

            noticia.fonteId = fonte.getString("id");
            noticia.fonteNome = fonte.getString("name");

            noticia.autor = artigo.getString("author");
            noticia.titulo = artigo.getString("title");
            noticia.descricao = artigo.getString("description");
            noticia.url = artigo.getString("url");
            noticia.urlImagem = artigo.getString("urlToImage");
            noticia.data = artigo.getString("publishedAt");
        } catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }

        return noticia;
    }

    /**
     * Parser de noticas no JsonArray
     * @param artigos Json contendo array de artigos
     * @return Lista de Noticias
     */
    public static ArrayList<Noticia> fromJson(JSONArray artigos) {
        ArrayList<Noticia> noticias = new ArrayList<Noticia>(artigos.length());

        for(int i = 0; i < artigos.length(); i++) {
            JSONObject noticiaJson = null;
            try {
                noticiaJson = artigos.getJSONObject(i);
            } catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Noticia noticia = Noticia.fromJson(noticiaJson);
            if(noticia != null) {
                noticias.add(noticia);
            }
        }

        return noticias;
    }

    /**
     * Metodo para recuperar texto da noticia em fonte
     * @return Texto da noticia
     */
    public String getTexto() {
        return Crawler.getNoticiaByUrl(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fonteId);
        parcel.writeString(fonteNome);
        parcel.writeString(autor);
        parcel.writeString(titulo);
        parcel.writeString(descricao);
        parcel.writeString(url);
        parcel.writeString(urlImagem);
        parcel.writeString(data);
    }
}
