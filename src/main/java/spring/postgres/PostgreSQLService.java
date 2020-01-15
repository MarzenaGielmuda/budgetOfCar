package spring.postgres;

import spring.type.Other;
import spring.type.Parts;
import spring.type.PetrolGas;
import spring.type.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLService  {


    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ex){
            System.err.println("Server can't find postgresql Driver class: \n" + ex);
        }
    }

    private static String POSTGRESQL_JDBC_URL = "jdbc:postgresql://localhost:5432/budget_of_auto"; //notesdb- to jest nasza baza - możemy nazwę zmieniać wg uznania

    private static String POSTGRESQL_DATABASE_USER ="postgres";
    private static String POSTGRESQL_DATABASE_PASS ="Astra123!";



    private final String ID_SERVICE = "id";
    private final String VALUE = "value";
//    private final String  DATE = "date";
    private final String DESCRIPTION = "description";
    private final String LastID = "LastID";



       public void removeService(long id) {

        final  String sqlSelectService = "DELETE FROM service WHERE  id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }




    public List<Service> getAllServices() {


        final  String sqlSelectAllServices = "SELECT * FROM service;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Service> services = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllServices);


            while (resultSet.next()){


                Service service = new Service();


//                String pattern = "MM-dd-yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//               String date;
//                date = simpleDateFormat.format(service.getData());
//                String date = new SimpleDateFormat("dd/MM/yyyy").parse(DATE);

//                    LocalDate date = LocalDate.of(2019,12,31);


                service.setId(resultSet.getInt(ID_SERVICE));
                service.setValue(resultSet.getDouble(VALUE));
//                service.setData(resultSet.getDate(String.valueOf(date)));
//                service.setData(resultSet.getDate());
                service.setDescription(resultSet.getString(DESCRIPTION));


                services.add(service);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  services;
    }

    public void addService(Service service) {

        final String sqlInsertService = "INSERT INTO service (id, value, description)" +
                "VALUES" +
                "   (?,?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        int id = postgre.getIdService()+1;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,id);
            preparedStatement.setDouble(2,service.getValue());
            preparedStatement.setString(3, service.getDescription());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    //    postgresServiceStorage.getService(service_id);
    public  Service getService(String service_id){
        final  String sqlSelectService = "SELECT * FROM service WHERE id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        int i= Integer.parseInt(service_id);
        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1, i);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Service service = new Service();
                service.setId(resultSet.getLong(ID_SERVICE));
                service.setValue(resultSet.getDouble(VALUE));
                service.setDescription(resultSet.getString(DESCRIPTION));

                return service;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }

        return null;
    }


    public void saveEditService(Service service){
        //
//        UPDATE Customers
//        SET ContactName='Alfred Schmidt', City='Frankfurt'
//        WHERE CustomerID=1;

        final String sqlInsertService =  "UPDATE service SET value=?, description=? WHERE id=?";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setDouble(1,service.getValue());
            preparedStatement.setString(2, service.getDescription());
            preparedStatement.setLong(3, service.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }


    }
    public int getIdService() {
        final  String sqlSeletService = "SELECT MAX(ID) AS LastID FROM service";
        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Service> services = new ArrayList<>();
        int x=0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSeletService);

            while (resultSet.next()){


                x= resultSet.getInt(LastID);

            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }

//        Service service= new Service();
//        service= services.get(0);
//        int i= (int) service.getId();
        return  x;
    }



    private Connection initializeDataBaseConnection(){

        try {
            return DriverManager.getConnection(POSTGRESQL_JDBC_URL,POSTGRESQL_DATABASE_USER,POSTGRESQL_DATABASE_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection");
            throw new RuntimeException("Server can't initialize database connection");

        }


    }

    private void closeDataBaseResources(Connection connection, Statement statement){
        try{
            if(statement!= null ){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch(SQLException ex){
            System.err.println( ex);
            throw new RuntimeException("Error during closing database resources");
        }
    }


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2 OTHER @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public void removeOther(long id) {

        final  String sqlSelectService = "DELETE FROM other WHERE  id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }




    public List<Other> getAllOther() {


        final  String sqlSelectAllServices = "SELECT * FROM other;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Other> others = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllServices);


            while (resultSet.next()){


                Other other = new Other();


//                String pattern = "MM-dd-yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//               String date;
//                date = simpleDateFormat.format(service.getData());
//                String date = new SimpleDateFormat("dd/MM/yyyy").parse(DATE);

//                    LocalDate date = LocalDate.of(2019,12,31);


                other.setId(resultSet.getInt(ID_SERVICE));
                other.setValue(resultSet.getDouble(VALUE));
//                service.setData(resultSet.getDate(String.valueOf(date)));
//                service.setData(resultSet.getDate());
                other.setDescription(resultSet.getString(DESCRIPTION));


                others.add(other);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  others;
    }

    public void addOther(Other other) {

        final String sqlInsertService = "INSERT INTO other (id, value, description)" +
                "VALUES" +
                "   (?,?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        int id = postgre.getIdOther()+1;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,id);
            preparedStatement.setDouble(2,other.getValue());
            preparedStatement.setString(3, other.getDescription());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    //    postgresServiceStorage.getService(service_id);
    public Other getOther(String other_id){
        final  String sqlSelectService = "SELECT * FROM other WHERE id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        int i= Integer.parseInt(other_id);
        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1, i);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Other other = new Other();
                other.setId(resultSet.getLong(ID_SERVICE));
                other.setValue(resultSet.getDouble(VALUE));
                other.setDescription(resultSet.getString(DESCRIPTION));

                return other;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }

        return null;
    }


    public void saveEditOther(Other other){
        //
//        UPDATE Customers
//        SET ContactName='Alfred Schmidt', City='Frankfurt'
//        WHERE CustomerID=1;
        final String sqlInsertService =  "UPDATE other SET value=?, description=? WHERE id=?";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setDouble(1,other.getValue());
            preparedStatement.setString(2, other.getDescription());
            preparedStatement.setLong(3, other.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }


    }
    public int getIdOther() {
        final  String sqlSeletService = "SELECT MAX(ID) AS LastID FROM other";
        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Other> others = new ArrayList<>();
        int x=0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSeletService);

            while (resultSet.next()){


                x= resultSet.getInt(LastID);

            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }

//        Service service= new Service();
//        service= services.get(0);
//        int i= (int) service.getId();
        return  x;
    }

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PARTS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public void removeParts(long id) {

        final  String sqlSelectService = "DELETE FROM parts WHERE  id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }




    public List<Parts> getAllParts() {


        final  String sqlSelectAllServices = "SELECT * FROM parts;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Parts> parts = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllServices);


            while (resultSet.next()){


                Parts part = new Parts();

                part.setId(resultSet.getInt(ID_SERVICE));
                part.setValue(resultSet.getDouble(VALUE));
                part.setDescription(resultSet.getString(DESCRIPTION));


                parts.add(part);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  parts;
    }

    public void addParts(Parts parts) {

        final String sqlInsertService = "INSERT INTO parts (id, value, description)" +
                "VALUES" +
                "   (?,?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        int id = postgre.getIdParts()+1;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,id);
            preparedStatement.setDouble(2,parts.getValue());
            preparedStatement.setString(3, parts.getDescription());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    public Parts getParts(String parts_id){
        final  String sqlSelectService = "SELECT * FROM parts WHERE id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        int i= Integer.parseInt(parts_id);
        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1, i);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Parts parts = new Parts();
                parts.setId(resultSet.getLong(ID_SERVICE));
                parts.setValue(resultSet.getDouble(VALUE));
                parts.setDescription(resultSet.getString(DESCRIPTION));

                return parts;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }

        return null;
    }


    public void saveEditParts(Parts parts){
        //
//        UPDATE Customers
//        SET ContactName='Alfred Schmidt', City='Frankfurt'
//        WHERE CustomerID=1;
        final String sqlInsertService =  "UPDATE parts SET value=?, description=? WHERE id=?";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setDouble(1,parts.getValue());
            preparedStatement.setString(2, parts.getDescription());
            preparedStatement.setLong(3, parts.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }


    }
    public int getIdParts() {
        final  String sqlSeletService = "SELECT MAX(ID) AS LastID FROM parts";
        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        int x=0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSeletService);

            while (resultSet.next()){


                x= resultSet.getInt(LastID);

            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }

//        Service service= new Service();
//        service= services.get(0);
//        int i= (int) service.getId();
        return  x;
    }



//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PETROL GAS@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public void removePetrolGas(long id) {

        final  String sqlSelectService = "DELETE FROM petrol WHERE  id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }


    public List<PetrolGas> getAllPetrolGas() {


        final  String sqlSelectAllServices = "SELECT * FROM petrol;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<PetrolGas> petrolGases = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllServices);


            while (resultSet.next()){


                PetrolGas petrolGas = new PetrolGas();

                petrolGas.setId(resultSet.getInt(ID_SERVICE));
                petrolGas.setValue(resultSet.getDouble(VALUE));
                petrolGas.setDescription(resultSet.getString(DESCRIPTION));


                petrolGases.add(petrolGas);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  petrolGases;
    }

    public void addPetrolGas(PetrolGas petrolGas) {

        final String sqlInsertService = "INSERT INTO petrol (id, value, description)" +
                "VALUES" +
                "   (?,?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        int id = postgre.getIdPetrolGas()+1;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,id);
            preparedStatement.setDouble(2,petrolGas.getValue());
            preparedStatement.setString(3, petrolGas.getDescription());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    public PetrolGas getPetrolGas(String petrolGas_id){
        final  String sqlSelectService = "SELECT * FROM petrol WHERE id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        int i= Integer.parseInt(petrolGas_id);
        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1, i);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                PetrolGas petrolGas = new PetrolGas();
                petrolGas.setId(resultSet.getLong(ID_SERVICE));
                petrolGas.setValue(resultSet.getDouble(VALUE));
                petrolGas.setDescription(resultSet.getString(DESCRIPTION));

                return petrolGas;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }

        return null;
    }


    public void saveEditPetrolGas(PetrolGas petrolGas){
        //
//        UPDATE Customers
//        SET ContactName='Alfred Schmidt', City='Frankfurt'
//        WHERE CustomerID=1;
//        long a = petrolGas.getId();
        final String sqlInsertService = "UPDATE petrol SET value=?, description=? WHERE id=?";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        PostgreSQLService postgre = new PostgreSQLService();

        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setDouble(1,petrolGas.getValue());
            preparedStatement.setString(2, petrolGas.getDescription());
            preparedStatement.setLong(3,petrolGas.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }


    }
    public int getIdPetrolGas() {
        final  String sqlSeletService = "SELECT MAX(ID) AS LastID FROM petrol";
        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        int x=0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSeletService);

            while (resultSet.next()){


                x= resultSet.getInt(LastID);

            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }

//        Service service= new Service();
//        service= services.get(0);
//        int i= (int) service.getId();
        return  x;
    }



}
