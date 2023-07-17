<%@page import = "java.util.ArrayList"%>
<%@page import = "com.accio.SearchResult"%>
<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "style.css">
</head>
<body>
<h1>Search Engine</h1>
    <form action = "search">
        <input type = "text" name = "keyword"></input>
        <button type = "submit">Search</button>
    </form>
    <form action = "history">
        <button type = "submit">History</button>
    </form>
    <table border = 2 class = "resultTable">
        <tr>
            <th>Title</th>
            <th>Link</th>
        </tr>
        <%
            ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
            for(SearchResult result: results){
        %>
        <tr>
            <td><%out.println(result.getTitle());%></td>
            <td><a href = "<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>