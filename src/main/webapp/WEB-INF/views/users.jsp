<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Datapine Users</title>
</head>
<body>

<h2>Datapine Users</h2>
   <table>
    <tr>
        <td>Name</td>
        <td>${name}</td>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr>
      <td><c:out value="${user.email}" /></td>
    </tr>
  </c:forEach> 
	</table> 
</body>
</html>