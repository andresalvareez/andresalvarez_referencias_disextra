package dis.ufv.PokemoRestApi.AASM;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class LectorJson {
    public ArrayList<Pokemon> LeerFicheroJson(){
        try {
            //lee el fichero que le pasamos y lo carga en un reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pokemonConId.json")));

            // convierte el array JSON a un arraylist de users
            ArrayList<Pokemon> listaPokemon =
                    new Gson().fromJson(reader, new TypeToken<ArrayList<Pokemon>>() {}.getType());
            reader.close();// close reader
            return listaPokemon;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<>(); //si no ha leido nada, devuelve un array vacio
        }
    }
}
