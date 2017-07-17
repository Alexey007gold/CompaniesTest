<%--
  Created by IntelliJ IDEA.
  User: Alexe
  Date: 7/13/2017
  Time: 6:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Companies</title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>

  <div id='edit' style="display: none">
    <div id="header">
      <span id="edit_label">Edit</span><input type="button" value="x" onclick="parentElement.parentElement.style.display = 'none'">
    </div>
    <div id="content">
      <div><span>Company Name: </span><input title="Company name" type="text" id="name"></div><br>
      <div><span>Parent Company Name:</span><input title="Parent company name" type="text" id="parent"></div><br>
      <div><span>Estimated Earnings: </span><input title="Estimated earnings" type="number" id="earning"></div> <br>
      <div id="buttons">
        <input type="button" value="Save" onclick="onSave()">
        <input type="button" value="Cancel" onclick="parentElement.parentElement.parentElement.style.display = 'none'">
      </div>
    </div>
  </div>
    <input type="button" value="Add company" onclick="openEditWindow()">
    <div id="tree">
      <jsp:include page="company.jsp"/>
    </div>
  </body>
  <script src="script.js"></script>
</html>
