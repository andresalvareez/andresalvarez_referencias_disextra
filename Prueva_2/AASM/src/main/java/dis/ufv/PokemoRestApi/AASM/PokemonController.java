package dis.ufv.PokemoRestApi.AASM;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PokemonController {
    @GetMapping("/pokemons")
    public ArrayList<Pokemon> pokemons(){
        return new LectorJson().LeerFicheroJson();
    }

    @GetMapping("/pokemon/porNombre/{nombre}")
    public ResponseEntity<Pokemon> getPorNombre(@PathVariable String nombre){
        ArrayList<Pokemon> listaPokemons = new LectorJson().LeerFicheroJson();
        Pokemon encontrado = null;
        for (Pokemon pokemon : listaPokemons) {
            if (pokemon.getName().equalsIgnoreCase(nombre)) {
                encontrado = pokemon;
            }
        }
        return new ResponseEntity<>(encontrado, HttpStatus.OK);
    }
    @GetMapping("/pokemon/porTipo/{tipo}")
    public ArrayList<Pokemon>  getPortipo(@PathVariable String tipo){
        ArrayList<Pokemon> listaPokemons = new LectorJson().LeerFicheroJson();
        ArrayList<Pokemon> listaEncontrados = new ArrayList<>();
        for (Pokemon pokemon : listaPokemons) {
            if (pokemon.getTipo1().equalsIgnoreCase(tipo) || pokemon.getTipo2().equalsIgnoreCase(tipo)) {
                listaEncontrados.add(pokemon);
            }
        }
        return listaEncontrados;
    }
}
