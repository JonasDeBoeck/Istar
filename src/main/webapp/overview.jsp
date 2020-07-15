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

<main id="overview">

    <%@ include file="components/addStar.jspf" %>

    <article class="">
        <p></p>
        <c:if test="${!errors.isEmpty()}">
            <c:forEach var="error" items="${errors}">
                <p>
                        <c:out value="${error}"/>
                </p>
            </c:forEach>
        </c:if>

        <c:if test="${!stars.isEmpty()}">
        <c:forEach var="star" items="${stars}">

        <section class="starComment">
            <img src="static/media/profile.png">
            <div class="mainComment">
                <h3><c:out value="${star.receiver.firstName}"/> <c:out value="${star.receiver.lastName}"/> <span>received a star from <c:out value="${star.sender.firstName}"/> <c:out value="${star.sender.lastName}"/> on <c:out value="${star.date}"/> </span></h3> <!-- has received a star by ${star.sender.firstName} ${star.sender.lastName}</h3> -->
                <p>
                    <c:out value="${star.message}"/>
                </p>
                <div class="tagText">
                    <c:set var="contains" value="false"/>
                    <c:set var="starId" value="${star.id}"/>
                    <c:forEach var="id" items="${likers}">
                        <c:if test="${id eq starId}">
                            <c:set var="contains" value="true"></c:set>
                        </c:if>
                    </c:forEach>
                    <p class="tags">tags: ${star.getTagList()}
                    <form action="Controller?command=Like&sId=<c:out value="${star.id}"/>&username=<c:out value="${user.userName}"/>" method="post"><input <c:if test="${contains eq 'true'}"> style="float: bottom;color: red"</c:if> type="submit" value="&#x2764; <c:out value="${star.likes.size()}"/>" class="heartButton"></form></p>
                </div>
            </div>
            <form method="post" action="Controller?command=Comment&starId=${star.id}">
                <input class="commentInputField" type="text" name="comment">
                <input class="commentSubmitButton" type="submit" value="comment">
            </form>
            <div class="comments">
            <c:forEach items="${star.comments}" var="comment">
                <div class="comment">
                    <h4><c:out value="${comment.sender.firstName}"/>  <c:out value="${comment.sender.lastName}"/> <span>commented</span></h4>
                    <p><c:out value="${comment.message}"/></p>
                </div>
            </c:forEach>
            </div>
        </section>
        </c:forEach>
        </c:if>

    </article>
</main>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>

