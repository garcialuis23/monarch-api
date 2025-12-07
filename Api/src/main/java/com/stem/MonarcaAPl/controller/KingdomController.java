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

import com.stem.MonarcaAPl.model.Kingdom;
import com.stem.MonarcaAPl.model.KingdomModel;
import com.stem.MonarcaAPl.model.MonarchModel;
import com.stem.MonarcaAPl.model.Monarch;

@RestController
@RequestMapping("/kingdom")
public class KingdomController {

    private KingdomModel MyKingdomModel = new KingdomModel();
    private MonarchModel MyMonarchModel = new MonarchModel();
    private MonarchController MyMonarchController = new MonarchController();

    // ---------------------------- UPDATE KINGDOM
    // ---------------------------------------
    @PutMapping
    @CrossOrigin(origins = "*")    @CrossOrigin
    public boolean updateKingdom(@RequestBody Kingdom updatedKingdom) throws SQLException {
        boolean result = false;
        Kingdom trueKingdom = getKingdomById(updatedKingdom.getId());
        Monarch searchedMonarch = MyMonarchController.getMonarchById(updatedKingdom.getMonarchKingdom().getId());
        if (trueKingdom != null && searchedMonarch != null && updatedKingdom.validateCapitalName())
            result = MyKingdomModel.updateKingdom(updatedKingdom);
        return result;
    }

    // ---------------------------- GET KINGDOM BY ID
    // -----------------------------------
    @GetMapping("/{id}")
    @CrossOrigin
    public Kingdom getKingdomById(@PathVariable int id) throws SQLException {
        return MyKingdomModel.getKingdomById(id);
    }

    // ----------------------------- DELETE KINGDOM
    // --------------------------------------
    @DeleteMapping
    @CrossOrigin
    public boolean deleteKingdom(@RequestBody Kingdom deletedKingdom) throws SQLException {
        return MyKingdomModel.deleteKingdom(deletedKingdom);
    }

    // ----------------------------- GET KINGDOM LIST
    // ------------------------------------
    @GetMapping
    @CrossOrigin
    public ArrayList<Kingdom> getKingdomList() throws SQLException {
        return MyKingdomModel.getKingdomList();
    }

    // ----------------------------- INSERT KINGDOM
    // -------------------------------------
    @PostMapping
    @CrossOrigin
kingdomInserted = 0;

        Monarch searchedMonarch = MyMonarchController.getMonarchById(newKingdom.getMonarchKingdom().getId());
        boolean nameOK = newKingdom.validateKingdomName();
        boolean capitalOK = newKingdom.validateCapitalName();
        boolean levelsOK = newKingdom.comprobarAge();

        if (searchedMonarch != null && nameOK && capitalOK && levelsOK) {
            newKingdom.setQuantityStoneKingdom(calculateWoodAndStone(newKingdom.getId()));
            newKingdom.setQuantityWoodKingdom(calculateWoodAndStone(newKingdom.getId()));
            newKingdom.setQuantityGoldKingdom(calculateGold(newKingdom.getId()));
            kingdomInserted = MyKingdomModel.insertKingdom(newKingdom);
        }

        return kingdomInserted;
    }

    // COMPROBACIONES:
    // ------------------------------------------------------------------
    // CALCULAR MADERA Y PIEDRA.
    private int calculateWoodAndStone(int kingdomId) throws SQLException {
        double area = MyKingdomModel.getSurface(kingdomId);
        double maxArea = MyKingdomModel.maxSurface();
        int num = 500;

        return calculateFuction(area, maxArea, num);
    }

    // Calcula un número entero aleatorio basado en el área de la superficie.
    private int calculateFuction(double surfaceArea, double maxSurfaceArea, int constant) {
        int min = 1;

        // Calcula el valor máximo basado en la relación entre el área de la superficie
        // actual y el área de superficie máxima, multiplicado por la constante y luego
        // sumando la constante.
        int max = (int) ((surfaceArea / maxSurfaceArea) * constant) + constant;
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    // CALCULAR ORO.
    private int calculateGold(int kingdomId) throws SQLException {
        int population = MyKingdomModel.getPopulation(kingdomId);
        int maxPopulation = MyKingdomModel.maxPopulation();
        int num = 500;

        return calculateFuction2(population, maxPopulation, num);
    }

    private int calculateFuction2(int population, int maxPopulation, int constant) {
        int min = 1;
        int max = (population / maxPopulation * constant) + constant;
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    // ----------------------------- PASAR TURNO
    // -----------------------------------------
    private int generateNumberRamdon(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    private void monarchDeath(Kingdom kingdom) throws SQLException {

        Monarch oldMonarch = kingdom.getMonarchKingdom();
        // Matar.
        MyMonarchController.deleteMonarch(oldMonarch);

        // Generar.
        Monarch newMonarch = MyMonarchController.createNewMonarch(oldMonarch);

        // Hacerle un id nuevo.
        int newMonarchId = MyMonarchController.insertMonarch(newMonarch);
        newMonarch.setId(newMonarchId);

        // Este nuevo monarca será ahora el FEJE del reino.
        kingdom.setMonarchKingdom(newMonarch);
    }

    public void probabilityDeathMorach(Kingdom kingdom) throws SQLException {

        // Uno de los valores es un número aleatorio 🎲 comprendido entre el 0 y el 100.
        // El otro valor es un número aleatorio 🎲 comprendido entre 0 y la edad del
        // monarca ⌛ ○ Si el segundo valor es mayor que el primero, el monarca muere �

        int valorOne = generateNumberRamdon(0, 100);
        // Sacamos la experiencia del monarca.
        int monarchAge = kingdom.getMonarchKingdom().getExperienceLevel();
        int valorTwo = generateNumberRamdon(0, monarchAge);

        if (valorTwo > valorOne) {
            monarchDeath(kingdom);
        }

    }

    // La cantidad de piedra 🪨 que tiene un reino aumenta un valor aleatorio 🎲
    // entre el 1 y el valor obtenido a partir de la siguiente fórmula. Y tambien lo
    // mismo para la medera incrementar para los 2.
    private void incrementStoneAndWood(Kingdom kingdom) throws SQLException {
        double surface = MyKingdomModel.getSurface(kingdom.getId());
        double maxSurface = MyKingdomModel.maxSurface();
        int constant = 500;

        int stoneIncrement = calculateFuction(surface, maxSurface, constant);
        int woodIncrement = calculateFuction(surface, maxSurface, constant);

        kingdom.setQuantityStoneKingdom(kingdom.getQuantityStoneKingdom() + stoneIncrement);
        kingdom.setQuantityWoodKingdom(kingdom.getQuantityWoodKingdom() + woodIncrement);
    }

    // La cantidad de oro 💰 que tiene un reino aumenta un valor aleatorio 🎲 entre
    // el 1 y el valor obtenido a partir de la siguiente fórmula:
    private void incrementGold(Kingdom kingdom) throws SQLException {
        int population = MyKingdomModel.getPopulation(kingdom.getId());
        int maxPopulation = MyKingdomModel.maxPopulation();
        int constant = 500;

        int goldIncrease = calculateFuction2(population, maxPopulation, constant);

        kingdom.setQuantityGoldKingdom(kingdom.getQuantityGoldKingdom() + goldIncrease);

    }

    // La cantidad de población 🌍 que tiene un reino aumenta un valor aleatorio 🎲
    // entre el 1 y el valor obtenido a partir de la siguiente fórmula:
    private void increemntPopulation(Kingdom kingdom) throws SQLException {
        int population = MyKingdomModel.getPopulation(kingdom.getId());
        int maxPopulation = MyKingdomModel.maxPopulation();
        int constant = 5000;

        int populationIncrease = calculateFuction2(population, maxPopulation, constant);

        kingdom.setPopulationKingdom(kingdom.getPopulationKingdom() + populationIncrease);

    }

    @GetMapping("/pass")
    @CrossOrigin
    public void changeRound() throws SQLException {

        ArrayList<Kingdom> kingdomList = MyKingdomModel.getKingdomList();

        for (Kingdom kingdom : kingdomList) {
            int years = generateNumberRamdon(1, 10);
            Monarch monarch = kingdom.getMonarchKingdom();
            monarch.setExperienceLevel(monarch.getExperienceLevel() + years);

            MyMonarchController.updateMonarch(monarch);
            probabilityDeathMorach(kingdom);

            incrementStoneAndWood(kingdom);
            incrementGold(kingdom);
            increemntPopulation(kingdom);

            MyKingdomModel.updatedKingdomFull(kingdom);
        }

    }

    // ------------------------------------ ATACAR
    // --------------------------------------

    // Estos métodos me ha ha explciado mari.
    private double calculateSurface(double surface, double maxSurface, double minSurface) {
        return (surface - minSurface) / (double) (maxSurface - minSurface) * 9 + 1;
    }

    private int calculateScore(Kingdom kingdom) throws SQLException {

        Monarch monarch = kingdom.getMonarchKingdom();

        // Hace la puntuacion por poblacion
        int population = kingdom.getPopulationKingdom();
        int maxPopulation = MyKingdomModel.maxPopulation();
        int minPopulation = MyKingdomModel.minPopulation();
        double populationScore = calculateFuction2(population, maxPopulation, minPopulation);

        // Hace la puntuacion de territorio
        double surface = kingdom.getSurface();
        double maxSurface = MyKingdomModel.maxSurface();
        double minSurface = MyKingdomModel.minSurface();
        double surfaceScore = calculateSurface(surface, maxSurface, minSurface);

        // Puntuación de estrategia
        int strategyScore = monarch.getStrategyLevel();

        // Puntuación por experiencia
        int monarchAge = monarch.getExperienceLevel();
        double experienceScore;
        if (monarchAge >= 80) {
            experienceScore = 0;
        } else {
            experienceScore = -(1 / 160.0) * Math.pow(monarchAge - 40, 2) + 10;
        }

        // aleatorio
        int randomScore = generateNumberRamdon(0, 30);

        // Puntuación total suma total
        double totalScore = populationScore + surfaceScore + strategyScore + experienceScore + randomScore;

        // Redondear
        return (int) Math.round(totalScore);
    }

    @GetMapping("/attack/{attackingKingdomId}/{attackedKingdomId}")
    @CrossOrigin
    public int kingdomsFigth(@PathVariable int attackingKingdomId, @PathVariable int attackedKingdomId)
            throws SQLException {
        // tener los reinos
        Kingdom attackingKingdom = getKingdomById(attackingKingdomId);
        Kingdom defendingKingdom = getKingdomById(attackedKingdomId);
        int returnedId = 0;

        if (attackingKingdomId != attackedKingdomId) {

            // Calcur puntos sus puntos
            int attackingScore = calculateScore(attackingKingdom);
            int attackedScore = calculateScore(defendingKingdom);

            Kingdom winner;
            Kingdom loser;

            if (attackingScore > attackedScore) {
                winner = attackingKingdom;
                loser = defendingKingdom;
            } else {
                winner = defendingKingdom;
                loser = attackingKingdom;
            }

            // Al terminar la guerra muerte al reino
            deleteKingdom(loser);

            // El nivel de estrategia del monarca ganador sube no mas de 10
            Monarch winningMonarch = winner.getMonarchKingdom();
            winningMonarch.setStrategyLevel(winningMonarch.getStrategyLevel() + 1);
            MyMonarchController.updateMonarch(winningMonarch);

            // Actualizamos la poblacion del ganador será un 70% de los dos reinos 0.7 
            int populationWinner = (int) (0.7 * (winner.getPopulationKingdom() + loser.getPopulationKingdom()));
            winner.setPopulationKingdom(populationWinner);

            // meter del reino perdedor al ganador
            winner.setSurface(winner.getSurface() + loser.getSurface());
            winner.setQuantityStoneKingdom(winner.getQuantityStoneKingdom() + loser.getQuantityStoneKingdom());
            winner.setQuantityWoodKingdom(winner.getQuantityWoodKingdom() + loser.getQuantityWoodKingdom());
            winner.setQuantityGoldKingdom(winner.getQuantityGoldKingdom() + loser.getQuantityGoldKingdom());

            // El monarca del reino perdedor muere
            MyMonarchController.deleteMonarch(loser.getMonarchKingdom());

            // Actualizar el reino nuevoo 
            updateKingdomWinner(winner);

            // Devolver el ID del reino ganador
            returnedId = winner.getId();
        }

        return returnedId;

    }

    // MéTODOOOO
    public boolean updateKingdomWinner(Kingdom updatedKingdom) throws SQLException {
        boolean result = false;
        Kingdom searcheKingdom = getKingdomById(updatedKingdom.getId());
        Monarch searchedMonarch = MyMonarchModel.getMonarchById(updatedKingdom.getMonarchKingdom().getId());
        if (searcheKingdom!=null && searchedMonarch != null && updatedKingdom.validateCapitalName())
            result = MyKingdomModel.updatedKingdomFull(updatedKingdom);

        return result;
    }

}
