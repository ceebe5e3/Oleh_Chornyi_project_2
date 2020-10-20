<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title><fmt:message key="title.add.conference"/></title>
</head>
<body>

<jsp:include page="../fragment/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col col-lg-4">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title"><fmt:message key="form.heading" /></h4>

                    <form method="post" id="add-conference"
                          action="${pageContext.request.contextPath}/${sessionScope.role}/${requestScope.conferencesLink}/add-conference">

                        <div class="form-group">
                            <label for="conferenceNameEN"><fmt:message key="add.conference.form.name.en"/></label>
                            <input type="text" id="conferenceNameEN" name="conferenceNameEN"
                                   class="form-control" value="${conferenceNameEN}">
                            <div class="text-danger"> ${incorrect_conferenceNameEN}</div>
                        </div>
                        <div class="form-group">
                            <label for="conferenceNameUA"><fmt:message key="add.conference.form.name.ua"/></label>
                            <input type="text" id="conferenceNameUA" name="conferenceNameUA"
                                   class="form-control" value="${conferenceNameUA}">
                            <div class="text-danger"> ${incorrect_conferenceNameUA}</div>
                        </div>
                        <div class="form-group">
                            <label for="datetime"><fmt:message key="add.conference.form.date"/></label>
                            <div id="datetime"></div>
                            <input type="hidden" id="datetime" name="datetime" class="form-control" value="" />
                            <div class="text-danger"> ${incorrect_datetime}</div>
                                <%----%>
                            <div id="conference-date-time-textarea"></div>
                            <input type="hidden" name="conference-date-time-textarea" id="datetime" disabled/>

                        </div>
                        <div class="form-group">
                            <label for="locationEN"><fmt:message key="add.conference.form.location.en"/></label>
                            <input type="text" id="locationEN" name="locationEN" class="form-control" value="${locationEN}">
                            <div class="text-danger"> ${incorrect_locationEN}</div>
                        </div>
                        <div class="form-group">
                            <label for="locationUA"><fmt:message key="add.conference.form.location.ua"/></label>
                            <input type="text" id="locationUA" name="locationUA" class="form-control" value="${locationUA}">
                            <div class="text-danger"> ${incorrect_locationUA}</div>
                        </div>

                        <div class="form-group">
                            <input type="hidden" name="conferenceId" value="${conferenceId}">
                            <input type="hidden" name="current-page" value="${paginationAttributes.currentPage}">
                            <input type="hidden" name="records-per-page" value="${paginationAttributes.recordsPerPage}">
                            <input type="hidden" name="conferencesLink" value="${requestScope.conferencesLink}">
                            <button type="submit" class="btn btn-success btn-block">
                                <fmt:message key="add.conference.form.save"/>
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
