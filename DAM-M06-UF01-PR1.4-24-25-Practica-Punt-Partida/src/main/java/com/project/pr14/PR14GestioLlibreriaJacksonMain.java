package com.project.pr14;

import com.project.objectes.Llibre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Classe principal que gestiona la lectura i el processament de fitxers JSON per obtenir dades de llibres.
 */
public class PR14GestioLlibreriaJacksonMain {

    private final File dataFile;

    /**
     * Constructor de la classe PR14GestioLlibreriaMain.
     *
     * @param dataFile Fitxer on es troben els llibres.
     */
    public PR14GestioLlibreriaJacksonMain(File dataFile) {
        this.dataFile = dataFile;
    }

    public static void main(String[] args) {
        File dataFile = new File(System.getProperty("user.dir"), "data/pr14" + File.separator + "llibres_input.json");
        PR14GestioLlibreriaJacksonMain app = new PR14GestioLlibreriaJacksonMain(dataFile);
        app.processarFitxer();
    }

    /**
     * Processa el fitxer JSON per carregar, modificar, afegir, esborrar i guardar les dades dels llibres.
     */
    public void processarFitxer() {
        List<Llibre> llibres = carregarLlibres();
        if (llibres != null) {
            modificarAnyPublicacio(llibres, 1, 1995);
            afegirNouLlibre(llibres, new Llibre(4, "Històries de la ciutat", "Miquel Soler", 2022));
            esborrarLlibre(llibres, 2);
            guardarLlibres(llibres);
        }
    }

    /**
     * Carrega els llibres des del fitxer JSON.
     *
     * @return Llista de llibres o null si hi ha hagut un error en la lectura.
     */
    public List<Llibre> carregarLlibres() {
        // *************** CODI PRÀCTICA **********************/
        try {
            List<Llibre> lisFin = new ArrayList<>();
            JsonReader jsonReader = Json.createReader(new FileReader(dataFile));
            JsonArray jsonAr = jsonReader.readArray();
            for (int i = 0;i<jsonAr.size();i++){
                JsonObject jobj = jsonAr.getJsonObject(i);
                //System.out.println(jobj.getInt("id")+" "+jobj.getString("titol")+" "+jobj.getString("autor")+" "+jobj.getInt("any"));
                lisFin.add(i,new Llibre(jobj.getInt("id"),jobj.getString("titol"),jobj.getString("autor"),jobj.getInt("any")));
            }
            return lisFin;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Substitueix pel teu
        }

    }

    /**
     * Modifica l'any de publicació d'un llibre amb un id específic.
     *
     * @param llibres Llista de llibres.
     * @param id Identificador del llibre a modificar.
     * @param nouAny Nou any de publicació.
     */
    public void modificarAnyPublicacio(List<Llibre> llibres, int id, int nouAny) {
        // *************** CODI PRÀCTICA **********************/
        for (Llibre libro : llibres){
            if (libro.getId() == id){
                libro.setAny(nouAny);
            }
        }
    }

    /**
     * Afegeix un nou llibre a la llista de llibres.
     *
     * @param llibres Llista de llibres.
     * @param nouLlibre Nou llibre a afegir.
     */
    public void afegirNouLlibre(List<Llibre> llibres, Llibre nouLlibre) {
        // *************** CODI PRÀCTICA **********************/
        llibres.add(nouLlibre);
    }

    /**
     * Esborra un llibre amb un id específic de la llista de llibres.
     *
     * @param llibres Llista de llibres.
     * @param id Identificador del llibre a esborrar.
     */
    public void esborrarLlibre(List<Llibre> llibres, int id) {
        // *************** CODI PRÀCTICA **********************/
        for (int i = 0;i<llibres.size();i++){
            if (llibres.get(i).getId() == id){
                llibres.remove(i);
            }
        }
    }

    /**
     * Guarda la llista de llibres en un fitxer nou.
     *
     * @param llibres Llista de llibres a guardar.
     */
    public void guardarLlibres(List<Llibre> llibres) {
        // *************** CODI PRÀCTICA **********************/
        File dataFile = new File(System.getProperty("user.dir"), "data/pr14" + File.separator + "llibres_output_jakarta.json");
        JsonArrayBuilder jar = Json.createArrayBuilder();
        for (Llibre libro:llibres){

            JsonObject llibreJson = Json.createObjectBuilder()
                    .add("id", libro.getId())
                    .add("titol", libro.getTitol())
                    .add("autor", libro.getAutor())
                    .add("any", libro.getAny())
                    .build();
            System.out.println(llibreJson);

            jar.add(llibreJson);
        }
        try (JsonWriter jsonWriter = Json.createWriter(new FileWriter(dataFile))) {
            JsonArray jsonArray = jar.build();
            System.out.println(jsonArray);
            jsonWriter.writeArray(jsonArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
