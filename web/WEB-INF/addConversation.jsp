<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/main.css' />" rel="stylesheet" type="text/css">
    <%--    DATE TIME PICKER--%>
    <link href="<c:url value='/style/css/datetimepicker.css' />" rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="<c:url value='/style/js/jquery-3.4.1.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/style/js/moment-with-locales.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/style/js/datetimepicker.js' />" type="text/javascript"></script>
    <%--    END DATE TIME PICKER --%>
    <fmt:setLocale value="${ empty sessionScope.lang ? 'en_US' : sessionScope.lang}" scope="session"/>
    <fmt:bundle basename="i18n">
    <title><fmt:message key="title.add.conversation"/></title>
</head>
<body>

<jsp:include page="fragment/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col col-lg-4">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title" style="font-family: monospace;font-variant: all-small-caps;"><fmt:message key="form.heading" /></h4>

                    <form method="post" id="add-conference"
                          action="${pageContext.request.contextPath}/${sessionScope.role}/add-conversation">

                        <input type="hidden" name="conversationId" value="${conversationId}">

                        <div class="form-group">
                            <label for="conversationTopicEN"><fmt:message key="add.conversation.form.topic.en"/></label>
                            <input type="text" id="conversationTopicEN" name="conversationTopicEN"
                                   class="form-control" value="${conversationTopicEN}">
                            <div class="text-danger"> ${incorrect_conversationTopicEN}</div>
                        </div>
                        <div class="form-group">
                            <label for="conversationTopicUA"><fmt:message key="add.conversation.form.topic.ua"/></label>
                            <input type="text" id="conversationTopicUA" name="conversationTopicUA"
                                   class="form-control" value="${conversationTopicUA}">
                            <div class="text-danger"> ${incorrect_conversationTopicUA}</div>
                        </div>
                        <div class="form-group">
                            <label for="datetime"><fmt:message key="add.conversation.form.date"/></label>
                            <div id="datetime"></div>
                            <input type="hidden" id="datetime" name="datetime" class="form-control" value="" />
                            <div class="text-danger"> ${incorrect_datetime}</div>
                                <%----%>
                            <div id="conference-date-time-textarea"></div>
                            <input type="hidden" name="conference-date-time-textarea" id="datetime" disabled/>

                        </div>

                        <div class="form-group">
                            <label for="speaker"><fmt:message key="add.conversation.form.speaker" /></label>
                            <select id="speaker" name="speaker" class="custom-select form-control" >
                                <c:if test="${sessionScope.role == 'moderator'}">
                                    <c:forEach items="${requestScope.speakers}" var="speaker">
                                        <option <c:if test="${speaker.id == speakerId}">selected</c:if>
                                                value="${speaker.id}" name="${speaker.id}">
                                                ${speaker.firstName} ${speaker.lastName}
                                        </option>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${sessionScope.role == 'speaker'}">
                                    <option value="${requestScope.speakers.id}" name="${requestScope.speakers.id}">
                                            ${requestScope.speakers.firstName} ${requestScope.speakers.lastName}
                                    </option>
                                </c:if>
                            </select>
                            <div class="text-danger">${incorrect_speaker}</div>
                        </div>


                        <div class="form-group">
                            <input type="hidden" name="submitted" value="true">
                            <input type="hidden" name="conferenceId" value="${conferenceId}">
                            <input type="hidden" name="conversationId" value="${conversationId}">
                            <button type="submit" class="btn btn-success btn-block">
                                <fmt:message key="add.conversation.form.save"/>
                            </button>
                        </div>

                    </form>
                </article>
            </div>
        </div>
    </div>

</div>

</fmt:bundle>

<!-- DATETIME -->
<script type="text/javascript">
    setInterval(function () {
        document.getElementById("datetime").value = document.getElementById("conference-date-time-textarea").innerHTML;
    }, 5);
</script>
<script type="text/javascript">
    $(document).ready( function () {
        $('#datetime').dateTimePicker();
    })
</script>
<!-- END DATETIME -->

</body>
</html>
