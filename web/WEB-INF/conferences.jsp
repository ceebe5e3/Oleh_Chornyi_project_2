<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://conferences.com/functions" prefix="f" %>

<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/main.css' />" rel="stylesheet" type="text/css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <fmt:setLocale value="${ empty sessionScope.lang ? 'en_US' : sessionScope.lang}" scope="session"/>
    <fmt:bundle basename="i18n">
    <title><fmt:message key="title.conferences"/></title>
</head>
<body>

<jsp:include page="fragment/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <h2 class="text-center" style="font-family: monospace;font-variant: all-small-caps;"><fmt:message key="conferences.heading" /></h2>
    </div>

    <div class="row justify-content-center">

        <c:if test="${sessionScope.role == 'moderator' && requestScope.conferencesLink != 'past-conferences'}">
            <form action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}/add-conference" method="post">
                <input type="hidden" name="current-page" value="${paginationAttributes.currentPage}">
                <input type="hidden" name="records-per-page" value="${paginationAttributes.recordsPerPage}">
                <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                <input type="hidden" name="isGetForm" value="true">
                <button class="btn btn-info" type="submit">
                    <fmt:message key="conferences.add.conference"/>
                </button>
            </form>

            <form action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}/mail" method="post">
                <input type="hidden" name="userId" value="${user.id}">
                <button class="btn btn-info" type="submit">
                    <fmt:message key="admin.mail"/>
                </button>
            </form>

        </c:if>

        <table class="table table-bordered table-striped text-center" style="background-color: darkseagreen;">
            <thead class="thead-dark">
            <tr>
                <th class="th_color"><fmt:message key="conferences.conference.name"/></th>
                <th class="th_color"><fmt:message key="conferences.conference.datetime"/></th>
                <th class="th_color"><fmt:message key="conferences.conference.location"/></th>
                <c:if test="${sessionScope.role != 'visitor'}">
                    <th class="th_color"><fmt:message key="conferences.conference.registration"/></th>
                </c:if>
                <c:if test="${sessionScope.role == 'moderator'}">
                    <th><fmt:message key="button.action"/></th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.conferences}" var="conference" varStatus="status">
                <tr>
                    <td class="align-middle">
                        <form action="${pageContext.request.contextPath}/${sessionScope.role}/conferences/${conference.id}/conversations"
                              method="post" class="mb-0">
                            <input type="hidden" name="conferenceId" value="${conference.id}">
                            <button class="btn btn-dark" type="submit">${conference.name}</button>
                        </form>
                    </td>
                    <td class="align-middle">${f:formatLocalDateTime(conference.dateTime, 'dd.MM.yyyy HH:mm')}</td>
                    <td class="align-middle">${conference.location}</td>
                    <c:if test="${sessionScope.role != 'visitor'}">
                        <td class="align-middle">
                            <c:choose>
                                <c:when test="${requestScope.isRegister[status.index]}">
                                    <form action="${pageContext.request.contextPath}/${sessionScope.role}/register-unregister"
                                          method="post" class="mb-0">
                                        <input type="hidden" name="conferenceId" value="${conference.id}">
                                        <input type="hidden" name="current-page" value="${paginationAttributes.currentPage}">
                                        <input type="hidden" name="records-per-page" value="${paginationAttributes.recordsPerPage}">
                                        <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                                        <input type="hidden" name="command" value="unregister">
                                        <button type="submit" <c:if test="${conference.dateTime < now}">disabled</c:if>
                                                class="btn btn-warning">
                                            <fmt:message key="conferences.unregister" />
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/${sessionScope.role}/register-unregister"
                                          method="post" class="mb-0">
                                        <input type="hidden" name="conferenceId" value="${conference.id}">
                                        <input type="hidden" name="current-page" value="${paginationAttributes.currentPage}">
                                        <input type="hidden" name="records-per-page" value="${paginationAttributes.recordsPerPage}">
                                        <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                                        <input type="hidden" name="command" value="register">
                                        <button type="submit" class="btn btn-primary"
                                                <c:if test="${conference.dateTime < now}">disabled</c:if>>
                                            <fmt:message key="conferences.register" />
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>
                    <c:if test="${sessionScope.role == 'moderator'}">
                        <td class="align-middle">
                            <form action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}/update-conference?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage}"
                                  method="post">
                                <input type="hidden" name="conferenceId" value="${conference.id}">
                                <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                                <button class="btn btn-info" type="submit">
                                    <fmt:message key="button.update"/>
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}/delete-conference?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage}"
                                  method="post" class="mb-0">
                                <input type="hidden" name="conferenceId" value="${conference.id}">
                                <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                                <button class="btn btn-danger" type="submit">
                                    <fmt:message key="button.delete"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="row">
        <nav>
            <ul class="pagination">
                <c:if test="${paginationAttributes.currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link" href="/${sessionScope.role}/${requestScope.conferencesLink}?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage-1}"><fmt:message key="pagination.previous" /></a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${paginationAttributes.nOfPages}" var="i">
                    <c:choose>
                        <c:when test="${paginationAttributes.currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link">${i}<span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="/${sessionScope.role}/${requestScope.conferencesLink}?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${paginationAttributes.currentPage lt paginationAttributes.nOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="/${sessionScope.role}/${requestScope.conferencesLink}?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage+1}"><fmt:message key="pagination.next" /></a>
                    </li>
                </c:if>
            </ul>
        </nav>

        <div>
            <form action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}"
                  class="form-inline" onchange="submit()">
                <input type="hidden" name="current-page" value="${paginationAttributes.currentPage}">
                <label class="ml-2" for="records"><fmt:message key="pagination.records.per.page" /></label>
                <select class="custom-select ml-1" id="records" name="records-per-page">
                    <option value="5" <c:if test="${paginationAttributes.recordsPerPage == 5}">selected</c:if>>5</option>
                    <option value="10" <c:if test="${paginationAttributes.recordsPerPage == 10}">selected</c:if>>10</option>
                    <option value="15" <c:if test="${paginationAttributes.recordsPerPage == 15}">selected</c:if>>15</option>
                </select>
            </form>
        </div>

    </div>

</div>

</fmt:bundle>

<script type="text/javascript">
    $(".table th").click(function() {
        $(this).find(".sort").toggleClass("asc desc");
    });
</script>
</body>
</html>


