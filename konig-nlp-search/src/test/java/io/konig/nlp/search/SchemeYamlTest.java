package io.konig.nlp.search;

/*
 * #%L
 * Konig Content System, Google App Engine implementation
 * %%
 * Copyright (C) 2015 - 2017 Gregory McFall
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import io.konig.yaml.Yaml;

public class SchemeYamlTest {

	@Test
	public void test() throws Exception {
		

		File file = new File("src/test/resources/SchemeYamlTest/scheme.yaml");
	    NaturalLanguageScheme scheme = Yaml.read(NaturalLanguageScheme.class, file);
	    assertEquals(scheme.getIdentifier(), "Test");
	    
	    ConceptList concepts = scheme.getConcepts();
	    assertEquals(concepts.size(), 12);
	    
	    NaturalLanguageConcept concept = concepts.get(0);
	    assertEquals("access", concept.getIdentifier());
	    assertEquals("Intent", concept.getType());
	    
	    LabelList labels = concept.getAltLabel();
	    assertEquals(76, labels.size());
	    assertEquals("I juan to study", labels.get(0));
	}

}
