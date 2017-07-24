<html>
<head></head>
<%@ page import="
	io.konig.nlp.search.NaturalLanguageIndex,
	io.konig.nlp.search.ConceptList,
	io.konig.nlp.search.NaturalLanguageConcept" 
%>
<%
	String schemeId = request.getParameter("schemeId");
	String searchString = request.getParameter("searchString");
	String type = request.getParameter("type");
	if ("ANY".equals(type)) {
		type = null;
	}
	
	NaturalLanguageIndex indexService = new NaturalLanguageIndex();
	ConceptList conceptList = indexService.search(schemeId, type, searchString);
%>
<body>
<% if (conceptList.isEmpty()) { %>
No concepts were found matching your search criteria.
<% } else { %>
	<table>
<%
	for (NaturalLanguageConcept concept : conceptList) {%>
		<tr>
			<td><%= concept.getType()%>:<%= concept.getIdentifier()%></td>
		</tr><%
	} %>
	</table>
<%} %>	
<p>
Try another <a href="search">Search</a>
</p>
</body>
</html>