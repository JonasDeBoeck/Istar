<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/styleMain3.css">
    <link rel="icon" href="static/media/icon.png">
</head>
<body>

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>


<main id="UserInfo">
    <%@ include file="components/addStar.jspf" %>

    <article class="userMainInfo">
        <img src="static/media/profile.png">

        <section>
            <h1><c:out value="${user.lastName}"/> <c:out value="${user.firstName}"/></h1>
            <h4><c:out value="${user.formatDate()}"/></h4>
            <h4><c:out value="${amountStars}"/>&#9733;</h4>
            <p></p>
        </section>
    </article>

    <article class="userTables">

        <div>
            <h2>received stars:</h2>
            <table class="table table-striped ">
                <thead class="thead-blue">
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Tags</th>
                    <th scope="col">Description</th>
                    <th scope="col">From</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="star" items="${given}">
                    <tr>
                        <td><c:out value="${star.date}"/></td>
                        <td><c:out value="${star.getTagList()}"/></td>
                        <td><c:out value="${star.message}"/></td>
                        <td><c:out value="${star.sender.firstName}"/> <c:out value="${star.sender.lastName}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div>
            <h2>sent stars:</h2>
            <table class="table table-striped ">
                <thead class="thead-blue">
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Tags</th>
                    <th scope="col">Description</th>
                    <th scope="col">To</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="star" items="${sent}">
                    <tr>
                        <td><c:out value="${star.date}"/></td>
                        <td><c:out value="${star.getTagList()}"/></td>
                        <td><c:out value="${star.message}"/></td>
                        <td><c:out value="${star.receiver.firstName}"/> <c:out value="${star.receiver.lastName}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


    </article>

</main>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>