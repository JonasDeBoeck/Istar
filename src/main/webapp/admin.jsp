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


<main id="admin">
    <%@ include file="components/addStar.jspf" %>

    <form novalidate="novalidate" method="post" action="Controller?command=AdminSearchUser">
        <c:if test="${errorsSearch != null}">
            <c:forEach var="error" items="${errorsSearch}">
                <p class="errorMessage"><c:out value="${error}"/></p>
            </c:forEach>
        </c:if>
        <c:if test="${!confirm.trim().isEmpty()}">
            <p class="errorMessage"><c:out value="${confirm}"/></p>
        </c:if>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="usernameSearch" name="username" value="<c:out value='${usernameSearchPrevious}'/> ">
        </div>
        <button type="submit" class="btn btn-primary">Look up user</button>
        <c:if test="${userFound !=null}">
            <table>
                <c:if test="${errorsRoleChange != null}">
                    <c:forEach var="error" items="${errorsRoleChange}">
                        <p class="errorMessage"><c:out value="${error}"/></p>
                    </c:forEach>
                </c:if>
                <tr>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Stars</th>
                    <th>Birthday</th>
                    <th>Change Role</th>
                    <th>Send mail?</th>
                </tr>
                <tr>
                    <td><c:out value="${userFound.userName}"/></td>
                    <td><c:out value="${userFound.firstName}"/></td>
                    <td><c:out value="${userFound.lastName}"/></td>
                    <td><c:out value="${userFound.email}"/></td>
                    <td><c:out value="${userFound.role}"/></td>
                    <td><c:out value="${userFound.stars}"/></td>
                    <td><c:out value="${userFound.birthdate}"/></td>
                    <td><div class="dropdown show">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Role
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="Controller?command=ChangeRole&role=USER&user=<c:out value="${userFound.userName}"/>">User</a>
                            <a class="dropdown-item" href="Controller?command=ChangeRole&role=SUPER&user=<c:out value="${userFound.userName}"/>">Super</a>
                            <a class="dropdown-item" href="Controller?command=ChangeRole&role=ADMIN&user=<c:out value="${userFound.userName}"/>">Admin</a>
                        </div>
                    </div> </td>
                    <td><div class="dropdown show">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Role
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="Controller?command=MailReceive&user=<c:out value="${userFound.userName}"/>&answer=yes">yes</a>
                            <a class="dropdown-item" href="Controller?command=MailReceive&user=<c:out value="${userFound.userName}"/>&answer=no">no</a>
                        </div>
                    </div>
                    </td>
                </tr>
            </table>

        </c:if>
    </form>
    <form novalidate="novalidate" method="post" action="Controller?command=AddUser">
        <h1>Add user:</h1>
        <c:if test="${errors != null}">
            <c:forEach var="error" items="${errors}">
                <p class="errorMessage"><c:out value="${error}"/></p>
            </c:forEach>
        </c:if>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" value="<c:out value='${usernamePrevious}'/> ">
        </div>

        <div class="form-group">
            <label for="lastname">Last name:</label>
            <input type="text" class="form-control" id="lastname" name="lastname" value="<c:out value='${lastnamePrevious}'/> ">
        </div>

        <div class="form-group">
            <label for="firstname">First name:</label>
            <input type="text" class="form-control" id="firstname" name="firstname" value="<c:out value='${firstnamePrevious}'/> ">
        </div>

        <div class="form-group">
            <label for="email">Email address</label>
            <input type="text" class="form-control" id="email" name="email" value="<c:out value='${emailPrevious}'/> ">
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>

        <div><!-- gender radio -->
            <div class="form-check">
                <input class="form-check-input" type="radio" name="gender" id="genderOther" value="Other" checked>
                <label class="form-check-label" for="genderOther">
                    Other
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="gender" id="genderMale" value="Male">
                <label class="form-check-label" for="genderMale">
                    Male
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="Female">
                <label class="form-check-label" for="genderFemale">
                    Female
                </label>
            </div>
        </div>


        <div class="form-group">
            <label for="role">Role:</label>
            <input type="text" class="form-control" id="role" name="role" value="<c:out value='${rolePrevious}'/> ">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>

        <div class="form-group">
            <label for="birthdate">Birthdate:</label>
            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<c:out value='${birthdayPrevious}'/> ">
        </div>

        <button type="submit" class="btn btn-primary">Add user</button>
    </form>

</main>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
