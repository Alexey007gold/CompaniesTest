<%--
  Created by IntelliJ IDEA.
  User: Alexe
  Date: 7/13/2017
  Time: 10:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="companies" scope="request" type="java.util.List<model.Company>"/>
<ul>
<c:forEach var="companies" items="${companies}">
    <li>
        <span class="company">
            <span class="name">${companies.name}</span> |
            <span class="earning">${companies.estimatedEarning}</span>K$
        </span>&nbsp&nbsp&nbsp&nbsp
        <span class="butt" onclick="openEditWindow(parentElement.firstElementChild)">Edit</span>&nbsp&nbsp
        <span class="butt" onclick="onDeleteClicked(parentElement.firstElementChild)">Delete</span>&nbsp&nbsp
        <span class="butt" onclick="onAddChildClicked('${companies.name}')">Add child</span>&nbsp&nbsp
        <c:set var="companies" value="${companies.children}" scope="request"/>
        <jsp:include page="company.jsp"/>
    </li>
</c:forEach>
</ul>
