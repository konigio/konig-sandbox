
<%@ page import="
	io.konig.nlp.search.NaturalLanguageIndex,
	io.konig.nlp.search.ConceptList,
	java.io.InputStream,
	io.konig.nlp.search.NaturalLanguageScheme,
	org.apache.commons.fileupload.FileItemIterator,
	org.apache.commons.fileupload.FileItemStream,
	org.apache.commons.fileupload.FileUploadException,
	org.apache.commons.fileupload.servlet.ServletFileUpload,
	io.konig.yaml.Yaml"
%>
<html>
<head></head>
<%
	ServletFileUpload upload = new ServletFileUpload();
	FileItemIterator iter = upload.getItemIterator(request);
	FileItemStream item = iter.next();
	InputStream stream = item.openStream();
	
	NaturalLanguageScheme scheme = Yaml.read(NaturalLanguageScheme.class, stream);
	NaturalLanguageIndex index = new NaturalLanguageIndex();

	index.put(scheme);
%>
<body>
OK!
<p>
Go to <a href="search">Search</a>
</p>
</body>
</html>