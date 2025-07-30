package com.stem.MonarcaAPl.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stem.MonarcaAPl.model.Monarch;
import com.stem.MonarcaAPl.model.MonarchModel;

@RestController
@RequestMapping("/monarch")
public class MonarchController {

    private MonarchModel MyMonarchModel = new MonarchModel();

    // Método para validar si un nombre específico pertenece a una dinastía
    // específica.
    public boolean validateName(String name, String dinasty) throws SQLException {
        // Se obtienen todos los nombres asociados a la dinastía especificada.
        ArrayList<String> dinastyNames = MyMonarchModel.getDinastyNames(dinasty);

        // Se verifica si la lista de nombres de la dinastía contiene el nombre
        // especificado.
        return dinastyNames.contains(name);
    }

    public static String convertToRomanNumeral(int number) {
        // Se define un arreglo de cadenas que contiene las representaciones en números
        // romanos de las unidades, decenas, centenas y miles.
        String[] romanosUnidades = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
        String[] romanosDecenas = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
        String[] romanosCentenas = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
        String[] romanosMiles = { "", "M", "MM", "MMM" };

        // Se calculan los valores de los dígitos de miles, centenas, decenas y unidades
        // del número proporcionado.
        int miles = number / 1000;
        int centenas = (number % 1000) / 100;
        int decenas = (number % 100) / 10;
        int unidades = number % 10;

        // Se construye la representación en números romanos del número proporcionado
        // concatenando las cadenas correspondientes de los arreglos.
        String resultado = romanosMiles[miles] + romanosCentenas[centenas] + romanosDecenas[decenas]
                + romanosUnidades[unidades];

        // Se devuelve el resultado.
        return resultado;

    }

    public String calculateOrdinal(String name, String dinasty) throws SQLException {
        int sameNameMonarchs = MyMonarchModel.countMonarchsSameName(name, dinasty) + 1;
        // Más uno porque es el siguiente el número que le vamos a poner.

        String ordinal = "";
        if (sameNameMonarchs > 0) {
            // Si hay más de un monarca con el mismo nombre, generamos el ordinal romano
            // correspondiente
            ordinal = convertToRomanNumeral(sameNameMonarchs);
        } else {
            // Si no hay otros monarcas con el mismo nombre, el ordinal es "I"
            ordinal = "I";
        }
        return ordinal;
    }

    @GetMapping("/{id}")
    public Monarch getMonarchById(@PathVariable int id) throws SQLException {
        return MyMonarchModel.getMonarchById(id);
    }

    @GetMapping
    public ArrayList<Monarch> getMonarchList() throws SQLException {
        return MyMonarchModel.getMonarchList();
    }

    @DeleteMapping
    public boolean deleteMonarch(@RequestBody Monarch deletedMonarch) throws SQLException {
        return MyMonarchModel.deleteMonarch(deletedMonarch);
    }

    @PutMapping
    public boolean updateMonarch(@RequestBody Monarch updatedMonarch) throws SQLException {
        return MyMonarchModel.updateMonarch(updatedMonarch);
    }

    // Método para insertar un Monarca.
    @PostMapping
    @CrossOrigin
    public int insertMonarch(@RequestBody Monarch newMonarch) throws SQLException {
        boolean validLevels = newMonarch.validateLevels(newMonarch.getStrategyLevel(), newMonarch.getDiplomacyLevel());

        // validar el nombre lo podemos gestionar directamente con una consulta, pero
        // ¿COMO?
        boolean nameIsValid = validateName(newMonarch.getName(), newMonarch.getDynasty());
        String ordinal = calculateOrdinal(newMonarch.getName(), newMonarch.getDynasty());

        int monarchInserted = 0;
        if (validLevels && nameIsValid) {
            newMonarch.setOrdinal(ordinal);
            newMonarch.generateRandomAge();
            newMonarch.setLife(nameIsValid);
            ;

            monarchInserted = MyMonarchModel.insertMonarch(newMonarch);
        }

        return monarchInserted;
    }

    // Se debe generar un nuevo monarca que pertenecerá a la misma dinastía.
    // Se le asignará un nombre aleatorio de entre la lista de nombres
    // proporcionados para dicha dinastía
    private int generateNumberRamdon(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    // CREAR UN NUEVO MONARCA.
    public Monarch createNewMonarch(Monarch oldMonarch) throws SQLException {
        // Se debe generar un nuevo monarca que pertenecerá a la misma dinastía.
        String dynasty = oldMonarch.getDynasty();
        // Se le asignará un nombre aleatorio de entre la lista de nombres
        // proporcionados para dicha dinastía.
        String newName = getRandomNameForDynasty(dynasty);

        Monarch newMonarch = new Monarch();
        newMonarch.setDynasty(dynasty);
        newMonarch.setName(newName);
        int randomLevelValue = generateNumberRamdon(1, 4);
        newMonarch.setDiplomacyLevel(randomLevelValue);
        newMonarch.setStrategyLevel(5 - randomLevelValue);
        newMonarch.setShield(oldMonarch.getShield());
        newMonarch.setDescription("Mueren unos nacen otros aún más grandes.");

        return newMonarch;
    }

    // CREAR EL NOMBRE ALEATORIO DE LA DINASTIA.
    private String getRandomNameForDynasty(String dinasty) throws SQLException {
        ArrayList<String> dinastyNames = MyMonarchModel.getDinastyNames(dinasty);

        int randomIndex = generateNumberRamdon(0, dinastyNames.size());

        // Obtener el nombre de monarca.
        String randomName = dinastyNames.get(randomIndex);

        return randomName;
    }

}
