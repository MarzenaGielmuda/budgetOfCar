package spring.postgres;

import com.sun.corba.se.pept.transport.ConnectionCache;
import org.w3c.dom.ls.LSOutput;
import spring.type.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookStorageImpl implements BookStorage {


    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ex){
            System.err.println("Server can't find postgresql Driver class: \n" + ex);
        }
    }

    private static String POSTGRESQL_JDBC_URL = "jdbc:postgresql://localhost:5432/bookshelf_db"; //notesdb- to jest nasza baza - możemy nazwę zmieniać wg uznania
    private static String POSTGRESQL_DATABASE_USER ="postgres";
    private static String POSTGRESQL_DATABASE_PASS ="Astra123!";
    private final String ID = "book_id";
    private final String TITLE = "title";
    private final String AUTHOR = "author";
    private final String PAGE_SUM = "pages_sum";
    private final String YEAR_OF_PUBLISHED = "year_of_published";
    private final String PUBLISHING_HOUSE = "publishing_house";


    @Override
    public List<Book> getAllBooks() {


        final  String sqlSelectAllBooks = "SELECT * FROM books;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Book> books = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllBooks);

            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt(ID));
                book.setTitle(resultSet.getString(TITLE));
                book.setAuthor(resultSet.getString(AUTHOR));
                book.setPagesSum(resultSet.getInt(PAGE_SUM));
                book.setYearOfPublished(resultSet.getInt(YEAR_OF_PUBLISHED));
                book.setPublishingHouse(resultSet.getString(PUBLISHING_HOUSE));

                books.add(book);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  books;
    }


//    book_id, title, author,pages_sum,year_of_published,publishing_house
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

    @Override
    public Book getBook(long id) {

        final  String sqlSeletBook = "SELECT * FROM books WHERE book_id = ?;";
//        final  String sqlSeletBook = "SELECT * FROM books WHERE book_id = ;"+ id;

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSeletBook);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt(ID));
                book.setTitle(resultSet.getString(TITLE));
                book.setAuthor(resultSet.getString(AUTHOR));
                book.setPagesSum(resultSet.getInt(PAGE_SUM));
                book.setYearOfPublished(resultSet.getInt(YEAR_OF_PUBLISHED));
                book.setPublishingHouse(resultSet.getString(PUBLISHING_HOUSE));

               return book;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }
        return null;
    }




        @Override
    public void addBook(Book book) {

        final String sqlInsertBook = "INSERT INTO books (book_id, title, author,pages_sum,year_of_published,publishing_house)" +
                "VALUES" +
                "   (?,?, ?, ?, ?,?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;


            try {
                preparedStatement = connection.prepareStatement(sqlInsertBook);

                preparedStatement.setLong(1,book.getId());
                preparedStatement.setString(2,book.getTitle());
                preparedStatement.setString(3,book.getAuthor());
                preparedStatement.setInt(4,book.getPagesSum());
                preparedStatement.setInt(5,book.getYearOfPublished());
                preparedStatement.setString(6,book.getPublishingHouse());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.err.println("Error during invoke SQL query :\n" + e.getMessage());
                throw new RuntimeException("Error during invoke SQL query");
            }finally {
                closeDataBaseResources(connection, preparedStatement);
            }

        }

    @Override
    public void removeBook(long bookIdToDelete) {

        final  String sqlSelectBook = "DELETE FROM books WHERE  book_id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectBook);
            preparedStatement.setLong(1, bookIdToDelete);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }


    }

}
