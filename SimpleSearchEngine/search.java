package com.accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;

@WebServlet("/search")
public class search extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Getting keyword from frontend
        String keyword= request.getParameter("keyword");
        //Setting up connection to database
        Connection connection = DatabaseConnection.getConnection();

        try {
            //Store the query of user
            PreparedStatement preparedStatement= connection.prepareStatement("Insert into history values(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/Simple_Search_Engine/search?keyword="+keyword);
            preparedStatement.executeUpdate();

            //Getting results after running ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select pageTitle,  pageLink, (length(lower(pageText))-length(replace(lower(pageText), '"+keyword.toLowerCase()+"', '')))/length('"+keyword.toLowerCase()+"') as occurrence from pages order by occurrence desc limit 30;");
            ArrayList<SearchResult> results= new ArrayList<SearchResult>();
            //Transferring values from resultSet to results arraylist
            while(resultSet.next()){
                SearchResult searchResult= new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            //Displaying result arraylist in console
            for(SearchResult result:results){
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }
            request.setAttribute("results", results);
            request.getRequestDispatcher("search.jsp").forward(request, response);
            response.setContentType("text/html");
            out.println("<h3>This is the keyword you have entered "+keyword+"</h3>");
        } catch (SQLException | ServletException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
