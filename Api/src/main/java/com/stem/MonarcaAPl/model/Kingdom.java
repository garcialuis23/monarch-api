package com.stem.MonarcaAPl.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kingdom {

    // Atributos privados de la clase
    private int id;
    private String kingdomName;
    private String description;
    private int foundationYear;
    private String capitalKingdom;
    private Monarch monarchKingdom;
    private double surface;
    private int populationKingdom;
    private ArrayList<Monarch> listAncientMonarchs;
    private String flagKingdom;
    private int quantityStoneKingdom;
    private int quantityWoodKingdom;
    private int quantityGoldKingdom;

    // Constructor por defecto
    public Kingdom() {

    }

    // Constructor con parámetros
    public Kingdom(int id, String kingdomName, String description, int foundationYear, String capitalKingdom,
            Monarch monarchKingdom, double surface, int populationKingdom, ArrayList<Monarch> listAncientMonarchs,
            String flagKingdom, int quantityStoneKingdom, int quantityWoodKingdom, int quantityGoldKingdom) {
        this.id = id;
        this.kingdomName = kingdomName;
        this.description = description;
        this.setFoundationYear(foundationYear); // Usa el setter para validar
        this.capitalKingdom = capitalKingdom;
        this.monarchKingdom = monarchKingdom;
        this.surface = surface;
        this.populationKingdom = populationKingdom;
        this.listAncientMonarchs = listAncientMonarchs; // Usa directamente la lista de monarcas antiguos
        this.flagKingdom = flagKingdom;
        this.quantityStoneKingdom = quantityStoneKingdom;
        this.quantityWoodKingdom = quantityWoodKingdom;
        this.quantityGoldKingdom = quantityGoldKingdom;
    }

    // ---------------------------------------------------------------------------------

    // Métodos getter para acceder a los atributos privados
    public int getId() {
        return this.id;
    }

    public String getKingdomName() {
        return this.kingdomName;
    }

    public String getDescription() {
        return this.description;
    }

    public int getFoundationYear() {
        return this.foundationYear;
    }

    public String getCapitalKingdom() {
        return this.capitalKingdom;
    }

    public Monarch getMonarchKingdom() {
        return this.monarchKingdom;
    }

    public double getSurface() {
        return this.surface;
    }

    public int getPopulationKingdom() {
        return this.populationKingdom;
    }

    public ArrayList<Monarch> getListAncientMonarchs() {
        return this.listAncientMonarchs;
    }

    public String getFlagKingdom() {
        return this.flagKingdom;
    }

    public int getQuantityStoneKingdom() {
        return this.quantityStoneKingdom;
    }

    public int getQuantityWoodKingdom() {
        return this.quantityWoodKingdom;
    }

    public int getQuantityGoldKingdom() {
        return this.quantityGoldKingdom;
    }

    // ----------------------------------------------------------------------------------

    // Métodos setter para modificar los atributos privados
    public void setId(int id) {
        this.id = id;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFoundationYear(int foundationYear) {
        // Validación del año de fundación
        if (foundationYear >= 400 && foundationYear <= 1400)
            this.foundationYear = foundationYear;
    }

    public void setCapitalKingdom(String capitalKingdom) {
        this.capitalKingdom = capitalKingdom;
    }

    public void setMonarchKingdom(Monarch monarchKingdom) {
        this.monarchKingdom = monarchKingdom;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public void setPopulationKingdom(int populationKingdom) {
        this.populationKingdom = populationKingdom;
    }

    public void setListAncientMonarchs(ArrayList<Monarch> listAncientMonarchs) {
        this.listAncientMonarchs = listAncientMonarchs;
    }

    public void setFlagKingdom(String flagKingdom) {
        this.flagKingdom = flagKingdom;
    }

    public void setQuantityStoneKingdom(int quantityStoneKingdom) {
        this.quantityStoneKingdom = quantityStoneKingdom;
    }

    public void setQuantityWoodKingdom(int quantityWoodKingdom) {
        this.quantityWoodKingdom = quantityWoodKingdom;
    }

    public void setQuantityGoldKingdom(int quantityGoldKingdom) {
        this.quantityGoldKingdom = quantityGoldKingdom;
    }

    // ------------------

    public boolean validateKingdomName() {
        boolean result = false;
        String caden = "^([A-Z][a-zA-Z]*\\s?)+$";

        Pattern p = Pattern.compile(caden);
        Matcher m = p.matcher(this.kingdomName);

        boolean lengthOK = this.kingdomName.length() <= 20;

        result = m.matches() && lengthOK;

        return result;
    }

    public boolean validateCapitalName() {
        boolean result = false;
        String caden = "^([A-Z][a-zA-Z]*\\s?)+$";

        Pattern p = Pattern.compile(caden);
        Matcher m = p.matcher(this.capitalKingdom);

        boolean lengthOK = this.capitalKingdom.length() <= 20;

        result = m.matches() && lengthOK;

        return result;
    }

    public boolean comprobarAge(){
        boolean retuurn = false;
        if (foundationYear >= 400 && foundationYear <= 1400)
        retuurn = true;
        return retuurn;    
    }

    // Método toString para obtener una representación en cadena del objeto
    @Override
    public String toString() {
        return "Kingdom [kingdomName -> " + this.kingdomName + ", description -> " + this.description
                + ", foundationYear -> " + this.foundationYear + ", capitalKingdom -> " + this.capitalKingdom
                + ", monarchKingdom -> " + this.monarchKingdom + ", surface -> " + this.surface
                + ", populationKingdom -> " + this.populationKingdom + ", listAncientMonarchs -> "
                + this.listAncientMonarchs + ", flagKingdom -> " + this.flagKingdom
                + ", quantityStoneKingdom -> " + this.quantityStoneKingdom + ", quantityWoodKingdom -> "
                + this.quantityWoodKingdom + ", quantityGoldKingdom -> " + this.quantityGoldKingdom + "]";
    }
}
