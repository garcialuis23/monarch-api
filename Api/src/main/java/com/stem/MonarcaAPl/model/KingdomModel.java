package com.stem.MonarcaAPl.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KingdomModel {

    private MyConnection myConnection = new MyConnection();
    private MonarchModel myMonarchModel = new MonarchModel();

    public boolean updateKingdom(Kingdom updatedKingdom) throws SQLException {

        String query = "UPDATE REINO SET CAPITAL = ?, "
                + "DESCRIPCION = ?, "
                + "ID_MONARCA = ?, "
                + "BANDERA_URL = ? "
                + "WHERE ID_REINO = ? ";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setString(1, updatedKingdom.getCapitalKingdom());
        statement.setString(2, updatedKingdom.getDescription());
        statement.setInt(3, updatedKingdom.getMonarchKingdom().getId());
        statement.setString(4, updatedKingdom.getFlagKingdom());
        statement.setInt(5, updatedKingdom.getId());

        int affectedrRows = statement.executeUpdate();

        statement.close();
        myConnection.disconnect();

        return affectedrRows > 0;
    }

    // getKingdomById -----------------

    public Kingdom getKingdomById(int kingdomId) throws SQLException {
        Kingdom kingdom = null;
        String query = "SELECT * FROM REINO WHERE ID_REINO = ?";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setInt(1, kingdomId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("ID_REINO");
            String kingdomName = resultSet.getString("NOMBRE");
            String description = resultSet.getString("DESCRIPCION");
            int foundationYear = resultSet.getInt("ANO_FUNDACION");
            String capitalKingdom = resultSet.getString("CAPITAL");
            int monarchId = resultSet.getInt("ID_MONARCA");
            Monarch monarchKingdom = myMonarchModel.getMonarchById(monarchId);
            int surface = resultSet.getInt("SUPERFICIE");
            int populationKingdom = resultSet.getInt("POBLACION");
            ArrayList<Monarch> listAncientMonarchs = getMonarchListFromKingdom(kingdomId);
            String flagKingdom = resultSet.getString("BANDERA_URL");
            int quantityStoneKingdom = resultSet.getInt("PIEDRA");
            int quantityWoodKingdom = resultSet.getInt("MADERA");
            int quantityGoldKingdom = resultSet.getInt("ORO");

            kingdom = new Kingdom(id, kingdomName, description, foundationYear, capitalKingdom, monarchKingdom,
                    surface, populationKingdom, listAncientMonarchs, flagKingdom, quantityStoneKingdom,
                    quantityWoodKingdom, quantityGoldKingdom);
        }

        resultSet.close();
        statement.close();
        myConnection.disconnect();

        return kingdom;
    }

    // MÉTODO AUXILIAR getKingdomById
    // --------------------------------------------------

    public ArrayList<Monarch> getMonarchListFromKingdom(int kingdomID) throws SQLException {
        ArrayList<Monarch> monarchList = new ArrayList<>();
        String query = "SELECT * FROM MONARCA JOIN MONARCA_REINA_REINO ON MONARCA.ID_MONARCA=MONARCA_REINA_REINO.ID_MONARCA WHERE MONARCA_REINA_REINO.ID_REINO = ? ORDER BY MONARCA_REINA_REINO.ID_REINO ASC;";

        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        statement.setInt(1, kingdomID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int monarchId = resultSet.getInt("ID_MONARCA");
            Monarch monarch = myMonarchModel.getMonarchById(monarchId);
            monarchList.add(monarch);
        }

        resultSet.close();
        statement.close();
        myConnection.disconnect();

        return monarchList;
    }

    // deleteKingdom ----------------

    public boolean deleteKingdom(Kingdom deletedKingdom) throws SQLException {
        String query = "DELETE FROM REINO WHERE ID_REINO = ?";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setInt(1, deletedKingdom.getId());

        int affectedrRows = statement.executeUpdate();

        statement.close();
        myConnection.disconnect();

        return affectedrRows > 0;
    }

    // getKingdomList -------------

    public ArrayList<Kingdom> getKingdomList() throws SQLException {
        Statement statement = myConnection.connect().createStatement();
        String query = "SELECT * FROM REINO";

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Kingdom> resultKingdomList = resultSetToArrayList(resultSet);

        statement.close();

        myConnection.disconnect();

        return resultKingdomList;
    }

    // MÉTODO AUXILIAR getKingdomList
    // --------------------------------------------------

    private ArrayList<Kingdom> resultSetToArrayList(ResultSet inputResultSet) throws SQLException {
        ArrayList<Kingdom> kingdomList = new ArrayList<Kingdom>();

        while (inputResultSet.next()) {
            int id = inputResultSet.getInt("ID_REINO");
            String kingdomName = inputResultSet.getString("NOMBRE");
            String description = inputResultSet.getString("DESCRIPCION");
            int foundationYear = inputResultSet.getInt("ANO_FUNDACION");
            String capitalKingdom = inputResultSet.getString("CAPITAL");
            int monarchId = inputResultSet.getInt("ID_MONARCA");
            Monarch monarchKingdom = myMonarchModel.getMonarchById(monarchId);
            int surface = inputResultSet.getInt("SUPERFICIE");
            int populationKingdom = inputResultSet.getInt("POBLACION");
            ArrayList<Monarch> listAncientMonarchs = getMonarchListFromKingdom(id);
            String flagKingdom = inputResultSet.getString("BANDERA_URL");
            int quantityStoneKingdom = inputResultSet.getInt("PIEDRA");
            int quantityWoodKingdom = inputResultSet.getInt("MADERA");
            int quantityGoldKingdom = inputResultSet.getInt("ORO");

            Kingdom kingdom = new Kingdom(id, kingdomName, description, foundationYear, capitalKingdom, monarchKingdom,
                    surface, populationKingdom, listAncientMonarchs, flagKingdom, quantityStoneKingdom,
                    quantityWoodKingdom, quantityGoldKingdom);

            kingdomList.add(kingdom);
        }

        return kingdomList;
    }

    // INSERTAR KINGDOM
    // ------------------------------------------------------------------
    public int insertKingdom(Kingdom insertedKingdom) throws SQLException {

        String query = "INSERT INTO REINO (NOMBRE, DESCRIPCION, ANO_FUNDACION, CAPITAL, ID_MONARCA, SUPERFICIE, POBLACION, BANDERA_URL, PIEDRA, MADERA, ORO)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        System.out.println(insertedKingdom);

        statement.setString(1, insertedKingdom.getKingdomName());
        statement.setString(2, insertedKingdom.getDescription());
        statement.setInt(3, insertedKingdom.getFoundationYear());
        statement.setString(4, insertedKingdom.getCapitalKingdom());
        statement.setInt(5, insertedKingdom.getMonarchKingdom().getId());
        statement.setDouble(6, insertedKingdom.getSurface());
        statement.setInt(7, insertedKingdom.getPopulationKingdom());
        statement.setString(8, insertedKingdom.getFlagKingdom());
        statement.setInt(9, insertedKingdom.getQuantityStoneKingdom());
        statement.setInt(10, insertedKingdom.getQuantityWoodKingdom());
        statement.setInt(11, insertedKingdom.getQuantityGoldKingdom());

        int affectedRows = statement.executeUpdate();
        statement.close();
        myConnection.disconnect();

        int returnResult = 0;
        if (affectedRows > 0)
            returnResult = getInsertedKingdomId();

        return returnResult;
    }

    public int getInsertedKingdomId() throws SQLException {
        String query = "SELECT MAX(ID_REINO) FROM REINO";
        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        int lastInsertedId = 0;
        if (resultSet.next()) {
            lastInsertedId = resultSet.getInt(1);
        }

        resultSet.close();
        statement.close();

        return lastInsertedId;
    }

    // MÉTODO AUXILIAR getKingdomList
    // -----------------------------------------------------------------------------------

    public double getSurface(int kingdomId) throws SQLException {

        double surface = 0;
        String query = "SELECT SUPERFICIE FROM REINO WHERE ID_REINO = ?";

        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        statement.setInt(1, kingdomId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            surface = resultSet.getDouble("SUPERFICIE");
        }
        resultSet.close();
        statement.close();
        myConnection.disconnect();

        return surface;
    }

    public int getPopulation(int kingdomId) throws SQLException {

        int population = 0;
        String query = "SELECT POBLACION FROM REINO WHERE ID_REINO = ?";

        PreparedStatement statement = myConnection.connect().prepareStatement(query);
        statement.setInt(1, kingdomId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            population = resultSet.getInt("POBLACION");
        }
        resultSet.close();
        statement.close();
        myConnection.disconnect();

        return population;
    }

    public int maxPopulation() throws SQLException {

        int maxPopulation = 0;
        String query = "SELECT MAX(POBLACION) AS POBLACION_MAXIMA FROM REINO";

        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            maxPopulation = resultSet.getInt("POBLACION_MAXIMA");
        }

        resultSet.close();
        statement.close();

        return maxPopulation;
    }

    // PASAR TURNO
    // -----------------------------------------------------------------------

    public boolean updatedKingdomFull(Kingdom updatedKingdom) throws SQLException {
        String query = "UPDATE REINO "
                + "SET CAPITAL = ?, "
                + "DESCRIPCION = ?, "
                + "ID_MONARCA = ?, "
                + "SUPERFICIE = ?, "
                + "POBLACION = ?, "
                + "BANDERA_URL = ?, "
                + "PIEDRA = ?, "
                + "MADERA = ?, "
                + "ORO = ? "
                + "WHERE ID_REINO = ? ";
        PreparedStatement statement = myConnection.connect().prepareStatement(query);

        statement.setString(1, updatedKingdom.getCapitalKingdom());
        statement.setString(2, updatedKingdom.getDescription());
        statement.setInt(3, updatedKingdom.getMonarchKingdom().getId());
        statement.setDouble(4, updatedKingdom.getSurface());
        statement.setInt(5, updatedKingdom.getPopulationKingdom());
        statement.setString(6, updatedKingdom.getFlagKingdom());
        statement.setInt(7, updatedKingdom.getQuantityStoneKingdom());
        statement.setInt(8, updatedKingdom.getQuantityWoodKingdom());
        statement.setInt(9, updatedKingdom.getQuantityGoldKingdom());
        statement.setInt(10, updatedKingdom.getId());

        int affectedrRows = statement.executeUpdate();

        statement.close();
        myConnection.disconnect();

        return affectedrRows > 0;
    }

    // ATACAR ---------------------------------------------------------
    public int minPopulation() throws SQLException {

        int minPopulation = 0;
        String query = "SELECT MIN(POBLACION) AS POBLACION_MINIMA FROM REINO";

        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            minPopulation = resultSet.getInt("POBLACION_MINIMA");
        }
    
        resultSet.close();
        statement.close();
    
        return minPopulation;
    }

    public double maxSurface() throws SQLException {

        double maxSurface = 0;
        String query = "SELECT MAX(SUPERFICIE) AS SUPERFICIE_MAXIMA FROM REINO";

        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            maxSurface = resultSet.getDouble("SUPERFICIE_MAXIMA");
        }
    
        resultSet.close();
        statement.close();
    
        return maxSurface;
    }

    public double minSurface() throws SQLException {

        double minSurface = 0;
        String query = "SELECT MIN(SUPERFICIE) AS SUPERFICIE_MINIMA FROM REINO";

        Statement statement = myConnection.connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            minSurface = resultSet.getDouble("SUPERFICIE_MINIMA");
        }
    
        resultSet.close();
        statement.close();
    
        return minSurface;
    }
}
