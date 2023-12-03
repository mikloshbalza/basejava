<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <br>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
            <tr>
                <td><h2><a name="type.name">${type.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td><h3><%=((TextSection) section).getText()%>
                        </h3></td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td><%=((TextSection) section).getText()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td>
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection)section).getList()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
