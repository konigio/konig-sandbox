<html>
<head></head>
<%@ page import="
	io.konig.nlp.search.NaturalLanguageIndex,
	java.util.List" 
%>
<%
	NaturalLanguageIndex indexService = new NaturalLanguageIndex();
	List<String> indexList = indexService.listSchemes();
%>
<body>
Search
<p>
<form method="get" enctype="application/x-www-form-urlencoded" action="searchResults">
<table>	
  <tr>
  	<th>Scheme Id:</th>
  	<td>
  		<select name="schemeId"><%
	 for (String indexId : indexList) {%>
	 			<option value="<%= indexId%>"><%= indexId%></option><%
	 }%>
			</select>
  </tr>
  <tr>
  	<th>Type</th>
  	<td>
  		<select name="type">
  			<option value="ANY"></option>
  			<option value="Intent">Intent</option>
  			<option value="Section">Section</option>
  		</select>
  	</td>
  </tr>
  <tr>
  	<th>Search String:</th>
  	<td><input type="text" name="searchString" size="40"></td>
  </tr>
</table>
<p>
	<input type="submit">
</p>
</form>
</p>
</body>
</html>