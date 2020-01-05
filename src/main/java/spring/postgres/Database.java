package spring.postgres;



import java.sql.*;

public class Database {


    private static String JDBC_URL = "jdbc:postgresql://localhost:5432/bookshelf_db"; //notesdb- to jest nasza baza - możemy nazwę zmieniać wg uznania
    private static String DATABASE_USER ="postgres";
    private static String DATABASE_PASS ="Astra123!";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "   INSERT INTO books (book_id, title, author,pages_sum,year_of_published,publishing_house)" +
                "VALUES" +
                "   (?,?, ?, ?, ?,?);");

        preparedStatement.setInt(1,8);
        preparedStatement.setString(2,"Kawa na ławę");
        preparedStatement.setString(3,"Kokosza");
        preparedStatement.setInt(4,56);
        preparedStatement.setInt(5,1992);
        preparedStatement.setString(6,"k&M");

        preparedStatement.executeUpdate();



        statement.close();
        connection.close();



    }


}
