<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Datapine Items</title>
</head>
<body>

<h2>Datapine Items</h2>
   <table>
    <tr>
        <td><b>Messages</b></td>
    </tr>
    <c:forEach items="${items}" var="item">
    <tr>
      <td><c:out value="${item.message}" /></td>
    </tr>
  </c:forEach> 
	</table> 
</body>
</html>