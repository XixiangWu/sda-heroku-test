<%@ page import="au.edu.unimelb.cis.swen90007.itsms.session.AppSession" %>
<%@ page import="au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory" %>
<%@ page import="au.edu.unimelb.cis.swen90007.itsms.session.AppSession" %>

<!-- Header -->
<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>View All Appointments</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/shards.min.css?v=3.0.0">
    <link rel="stylesheet" href="css/shards-demo.min.css?v=3.0.0">
</head>

<!-- Navigation Bar -->

<% AppSession wrappedAppSession = AppSession.refreshSession(session);
    if (session.isNew()) { %>
    <%=FrontEndFactory.navBarGenerator("new user")%>
    <%} else {%>
        <%=FrontEndFactory.navBarGenerator(wrappedAppSession.getUser().getUsername())%>
    <% } %>

<!--Table-->
<div id="table" class="container">
    <div class="section-title col-lg-8 col-md-10 ml-auto mr-auto">
        <h3 class="mb-4">View appointments</h3>
        <p>All appointments will be shown here.</p>
    </div>

    <div class="example col-lg-auto col-md-10 ml-auto mr-auto">
        ${table}
    </div>
</div>

<%=FrontEndFactory.scriptGenerator()%>