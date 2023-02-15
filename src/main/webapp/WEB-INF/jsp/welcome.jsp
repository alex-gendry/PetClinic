<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<img src="<spring:url value="/static/images/pets.png" htmlEscape="true" />" align="right" style="position:relative;right:30px;">
<h2><fmt:message key="welcome"/></h2>

<ul>
  <li><a href="<spring:url value="/owners/search" htmlEscape="true" />">Find owner</a></li>
  <li><a href="<spring:url value="/vets" htmlEscape="true" />">Display all veterinarians</a></li>
  <li><a href="<spring:url value="/owners/4" htmlEscape="true" />">Display George's pets</a></li>
  <li><a href="<spring:url value="/xss?name=AttackHere" htmlEscape="false" />">Request Param (XSS)</a></li>
  <li><a href="<spring:url value="/owners/5/pets/6/edit" htmlEscape="true" />">Pet Edit Form: Redirect (XSS)</a></li>
  <li><a href="<spring:url value="/owners/4/pets/5/edit" htmlEscape="true" />">Pet Edit Form: CallPublic Method (XSS)</a></li>
  <li><a href="<spring:url value="/owners/4/pets/5/edit/concatenateMethodUrl?petId=4&petName=AttackHere" htmlEscape="false" />">Pet Edit Form: concatenateMethodUrl (XSS)</a></li>
  <li><a href="<spring:url value="/owners/4/edit" htmlEscape="true"/>">Edit Owner Form: (XSS)</a></li>
</ul>

<p>&nbsp;</p>
<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
