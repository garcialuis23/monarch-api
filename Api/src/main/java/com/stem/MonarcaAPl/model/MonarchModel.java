package com.stem.MonarcaAPl.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MonarchModel {

    // Se crea una instancia de la clase MyConnection para manejar la conexión a la
    // base de datos.
    private MyConnection myConnection = new MyConnection();

    // Método para obtener los nombres asociados a una dinastía específica.
    public ArrayList<String> getDinastyNames(String dinasty) throws SQLException {
        // Se crea una lista para almacenar los nombres de la dinastía
        ArrayList<String> dinastyNames = new ArrayList<>();

        // Se define la consulta SQL para obtener los nombres de la dinastía.
        String query = "SELECT * FROM DINASTIA_TIENE_NOMBRE WHERE NOMBRE_DINASTIA = ?";

        // Se establece la conexión a la base de datos y se prepara la declaración SQL.
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        // Se asigna el nombre de la dinastía al parámetro de la consulta SQL.
        statement.setString(1, dinasty);

        // Se ejecuta la consulta SQL y se obtiene un conjunto de resultados.
        ResultSet resultSet = statement.executeQuery();

        // Se recorren los resultados y se agregan los nombres a la lista.
        while (resultSet.next()) {
            dinastyNames.add(resultSet.getString("NOMBRE_PROPIO"));
        }

        // Se cierran el conjunto de resultados, la declaración y la conexión a la base
        // de datos.
        resultSet.close();
        statement.close();
        myConnection.disconnect();

        // Se devuelve la lista de nombres de la dinastía.
        return dinastyNames;
    }

    /**
     * Método para ver cuántos monarcas hay con el mismo nombre
     * 
     * @param monarchName
     * @param monarchDynasty
     * @return
     * @throws SQLException
     */
    public int countMonarchsSameName(String monarchName, String monarchDynasty) throws SQLException {

        String query = "SELECT COUNT(*) FROM MONARCA WHERE NOMBRE = ? AND NOMBRE_DINASTIA = ?";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        statement.setString(1, monarchName);
        statement.setString(2, monarchDynasty);
        ResultSet resultSet = statement.executeQuery();

        int sameNameMonarchsCount = 0;
        if (resultSet.next()) {
            sameNameMonarchsCount = resultSet.getInt(1);
        }

        resultSet.close();
        statement.close();

        return sameNameMonarchsCount;
    }

    // Update monarch -------------

    public boolean updateMonarch(Monarch updatedMonarch) throws SQLException {

        String query = "UPDATE MONARCA "
                + "SET NIVEL_ESTRATEGIA = ?, "
                + "NIVEL_DIPLOMACIA = ?, "
                + "NIVEL_EXPERIENCIA = ? "
                + "WHERE ID_MONARCA = ? ";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setInt(1, updatedMonarch.getStrategyLevel());
        statement.setInt(2, updatedMonarch.getDiplomacyLevel());
        statement.setInt(3, updatedMonarch.getExperienceLevel());
        statement.setInt(4, updatedMonarch.getId());

        int affectedrRows = statement.executeUpdate();

        statement.close();
        myConnection.disconnect();

        return affectedrRows > 0;
    }

    // Insert morach -----------------

    public int insertMonarch(Monarch insertedMonarch) throws SQLException {

        String query = "INSERT INTO MONARCA (NOMBRE, NOMBRE_DINASTIA, ESCUDO, DESCRIPCION, NIVEL_ESTRATEGIA, NIVEL_DIPLOMACIA, NIVEL_EXPERIENCIA, ORDINAL_NOMBRE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setString(1, insertedMonarch.getName());
        statement.setString(2, insertedMonarch.getDynasty());
        statement.setString(3, insertedMonarch.getShield());
        statement.setString(4, insertedMonarch.getDescription());
        statement.setInt(5, insertedMonarch.getStrategyLevel());
        statement.setInt(6, insertedMonarch.getDiplomacyLevel());
        statement.setInt(7, insertedMonarch.getExperienceLevel());
        statement.setString(8, insertedMonarch.getOrdinal());
        int affectedRows = statement.executeUpdate();

        int result = 0;

        if (affectedRows>0) result = getLastInsertedMonarchId();

        statement.close();

        myConnection.disconnect();

        return result;
    }

    // Método para inserat
    // SACA EL UTLIMO ID
    public int getLastInsertedMonarchId() throws SQLException {
        String query = "SELECT MAX(ID_MONARCA) FROM MONARCA";
        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        int lastInsertedId = 0;
        if (resultSet.next()) {
            lastInsertedId = resultSet.getInt(1);
        }
    
        // Se cierran los recursos
        resultSet.close();
        statement.close();
    
        return lastInsertedId;
    }
    
    // Lista de monarcas -----------------

    private ArrayList<Monarch> resultSetToArrayList(ResultSet inputResultSet) throws SQLException {
        ArrayList<Monarch> monarchList = new ArrayList<Monarch>();

        while (inputResultSet.next()) {
            int id = inputResultSet.getInt("ID_MONARCA");
            String name = inputResultSet.getString("NOMBRE");
            String ordinal = inputResultSet.getString("ORDINAL_NOMBRE");
            String shield = inputResultSet.getString("ESCUDO");
            String dynasty = inputResultSet.getString("NOMBRE_DINASTIA");
            String description = inputResultSet.getString("DESCRIPCION");
            int strategyLevel = inputResultSet.getInt("NIVEL_ESTRATEGIA");
            int diplomacyLevel = inputResultSet.getInt("NIVEL_DIPLOMACIA");
            int experienceLevel = inputResultSet.getInt("NIVEL_EXPERIENCIA");
            boolean alive = inputResultSet.getBoolean("VIVO");

            Monarch monarch = new Monarch(id, name, ordinal, shield, dynasty, description,
                    strategyLevel, diplomacyLevel, experienceLevel, alive);

            monarchList.add(monarch);
        }

        return monarchList;
    }

    // sacar Los monarcas getMonarchList

    public ArrayList<Monarch> getMonarchList() throws SQLException {
        Statement statement = myConnection.connect().createStatement();
        String query = "SELECT * FROM MONARCA";

        ResultSet resultSet = statement.executeQuery(query);

        // Se llama al método resultSetToArrayList para convertir el ResultSet en una
        // lista de objetos Monarca.
        ArrayList<Monarch> resultMonarchList = resultSetToArrayList(resultSet);

        // Se cierra el objeto Statement para liberar recursos.
        statement.close();

        // Se desconecta de la base de datos para liberar la conexión.
        myConnection.disconnect();

        // Se devuelve la lista que contiene todos los objetos Monarca creados.
        return resultMonarchList;
    }

    // getMonarchById  ------------

    public Monarch getMonarchById(int monarchId) throws SQLException {
        Monarch monarch = new Monarch();
        String query = "SELECT * FROM MONARCA WHERE ID_MONARCA = ?";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setInt(1, monarchId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("ID_MONARCA");
            String name = resultSet.getString("NOMBRE");
            String ordinal = resultSet.getString("ORDINAL_NOMBRE");
            String shield = resultSet.getString("ESCUDO");
            String dynasty = resultSet.getString("NOMBRE_DINASTIA");
            String description = resultSet.getString("DESCRIPCION");
            int strategyLevel = resultSet.getInt("NIVEL_ESTRATEGIA");
            int diplomacyLevel = resultSet.getInt("NIVEL_DIPLOMACIA");
            int experienceLevel = resultSet.getInt("NIVEL_EXPERIENCIA");
            boolean alive = resultSet.getBoolean("VIVO");

            monarch = new Monarch(id, name, ordinal, shield, dynasty, description,
                    strategyLevel, diplomacyLevel, experienceLevel, alive);
        }

        statement.close();
        myConnection.disconnect();
        return monarch;
    }

    // Borrar monarca ---------------------

    public boolean deleteMonarch(Monarch deletedMonarch) throws SQLException {

        String query = "UPDATE MONARCA "
        +"SET VIVO = FALSE " 
        + "WHERE ID_MONARCA = ? ";

        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        statement.setInt(1, deletedMonarch.getId());
        int affectedRows = statement.executeUpdate();
        
        statement.close();
        myConnection.disconnect();

        return affectedRows > 0;
    }


    // Sacar una lista con los nombres de los monarcas.
    public ArrayList<String> getMonarchNames() throws SQLException {
        ArrayList<String> monarchNames = new ArrayList<>();

        String query = "SELECT NOMBRE FROM MONARCA";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            monarchNames.add(resultSet.getString("NOMBRE"));

        }
        resultSet.close();
        statement.close();
        myConnection.disconnect();

        return monarchNames;
    }

}
