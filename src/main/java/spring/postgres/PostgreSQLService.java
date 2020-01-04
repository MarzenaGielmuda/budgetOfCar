package spring.postgres;

import com.sun.corba.se.pept.transport.ConnectionCache;
import org.w3c.dom.ls.LSOutput;
import spring.type.Book;
import spring.type.Service;

import java.text.DateFormat;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

//        int id = postgre.getIdSql()+1;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,service.getId());
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

    public int getIdSql(){

        final String sqlInsertService = " SELECT MAX(ID) AS LastID FROM service";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        return Integer.parseInt(sqlInsertService);
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

//    @Override
//    public Book getBook(long id) {
//
//        final  String sqlSeletBook = "SELECT * FROM books WHERE book_id = ?;";
////        final  String sqlSeletBook = "SELECT * FROM books WHERE book_id = ;"+ id;
//
//        Connection connection = initializeDataBaseConnection();
//        PreparedStatement preparedStatement = null;
//
//        try {
//            preparedStatement = connection.prepareStatement(sqlSeletBook);
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()){
//                Book book = new Book();
//                book.setId(resultSet.getInt(ID));
//                book.setTitle(resultSet.getString(TITLE));
//                book.setAuthor(resultSet.getString(AUTHOR));
//                book.setPagesSum(resultSet.getInt(PAGE_SUM));
//                book.setYearOfPublished(resultSet.getInt(YEAR_OF_PUBLISHED));
//                book.setPublishingHouse(resultSet.getString(PUBLISHING_HOUSE));
//
//               return book;
//
//            }
//        } catch (SQLException e) {
//            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
//            throw new RuntimeException("Error during invoke SQL query");
//        }finally {
//            closeDataBaseResources(connection,preparedStatement);
//        }
//        return null;
//    }
//
//

//
//    @Override
//    public void removeBook(long bookIdToDelete) {
//
//        final  String sqlSelectBook = "DELETE FROM books WHERE  book_id= ?;";
//
//        Connection connection = initializeDataBaseConnection();
//        PreparedStatement preparedStatement = null;
//
//        try {
//            preparedStatement = connection.prepareStatement(sqlSelectBook);
//            preparedStatement.setLong(1, bookIdToDelete);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
//            throw new RuntimeException("Error during invoke SQL query");
//        }finally {
//            closeDataBaseResources(connection, preparedStatement);
//        }
//
//
//    }

}
