<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://conferences.com/functions" prefix="f" %>

<html>
<head>
    <meta charset="UTF-8" >
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/main.css' />" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${ empty sessionScope.lang ? 'en_US' : sessionScope.lang}" scope="session"/>
    <fmt:bundle basename="i18n">
    <title><fmt:message key="title.conversations"/></title>
</head>
<body>

<jsp:include page="fragment/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <h2 class="text-center" style="font-family: monospace;font-variant: all-small-caps;"><fmt:message key="conversations.heading"/></h2>
    </div>
    <div class="row justify-content-center">

        <c:if test="${(sessionScope.role == 'moderator' || sessionScope.role == 'speaker') && isAddConversationButtonDisable == null}">
            <form action="${pageContext.request.contextPath}/${sessionScope.role}/add-conversation" method="post">
                <input type="hidden" name="conferenceId" value="${conferenceId}">
                <button class="btn btn-info" type="submit">
                    <fmt:message key="conversations.add.conversation"/>
                </button>
            </form>
        </c:if>

        <table class="table table-bordered table-striped text-center" style="background-color: darkseagreen;">
            <thead class="thead-dark">
            <tr>
                <th class="th_color"><fmt:message key="conversations.conversation.topic"/></th>
                <th class="th_color"><fmt:message key="conversations.conversation.datetime"/></th>
                <th class="th_color"><fmt:message key="conversations.conversation.speaker.firstName"/></th>
                <th class="th_color"><fmt:message key="conversations.conversation.speaker.lastName"/></th>
                <th class="th_color"><fmt:message key="conversations.conversation.speaker.email"/></th>
                <c:if test="${sessionScope.role == 'moderator' || sessionScope.role == 'speaker'}">
                    <th class="th_color"><fmt:message key="button.action"/></th>
                </c:if>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${requestScope.conversations}" var="conversation" varStatus="status">
                <tr>
                    <td class="align-middle">${conversation.topic}</td>
                    <td class="align-middle">${f:formatLocalDateTime(conversation.dateTime, 'dd.MM.yyyy HH:mm')}</td>
                    <td class="align-middle">${requestScope.speakers.get(status.index).firstName}</td>
                    <td class="align-middle">${requestScope.speakers.get(status.index).lastName}</td>
                    <td class="align-middle">${requestScope.speakers.get(status.index).email}</td>
                    <c:if test="${sessionScope.role == 'moderator' || (sessionScope.role == 'speaker' && conversation.speakerId == userId)}">
                        <td class="align-middle">
                            <form action="${pageContext.request.contextPath}/${sessionScope.role}/update-conversation"
                                  method="post">
                                <input type="hidden" name="conferenceId" value="${conferenceId}">
                                <input type="hidden" name="conversationId" value="${conversation.id}">
                                <button class="btn btn-info" type="submit">
                                    <fmt:message key="button.update"/>
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/${sessionScope.role}/delete-conversation"
                                  method="post" class="mb-0">
                                <input type="hidden" name="conferenceId" value="${conferenceId}">
                                <input type="hidden" name="conversationId" value="${conversation.id}">
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

    <c:if test="${!requestScope.conversations.isEmpty()}">
        <div class="row">
            <nav>
                <ul class="pagination">
                    <c:if test="${paginationAttributes.currentPage != 1}">
                        <li class="page-item">
                            <a class="page-link" href="/${sessionScope.role}/conferences/${conferenceId}/conversations?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage-1}"><fmt:message key="pagination.previous" /></a>
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
                                    <a class="page-link" href="/${sessionScope.role}/conferences/${conferenceId}/conversations?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${paginationAttributes.currentPage lt paginationAttributes.nOfPages}">
                        <li class="page-item">
                            <a class="page-link" href="/${sessionScope.role}/conferences/${conferenceId}/conversations?records-per-page=${paginationAttributes.recordsPerPage}&current-page=${paginationAttributes.currentPage+1}"><fmt:message key="pagination.next" /></a>
                        </li>
                    </c:if>
                </ul>
            </nav>

            <div>
                <form action="${pageContext.request.contextPath}/${sessionScope.role}/conferences/${conferenceId}/conversations"
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
    </c:if>

</div>

</fmt:bundle>
</body>
</html>
