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


import java.io.File;
import java.io.IOException;

import io.konig.yaml.Yaml;
import io.konig.yaml.YamlParseException;

public class StemPrinter {
	
	public static void main(String[] args) throws YamlParseException, IOException {
		File file = new File("src/test/resources/SchemeYamlTest/scheme.yaml");
		NaturalLanguageScheme scheme = Yaml.read(NaturalLanguageScheme.class, file);
		String schemeId = scheme.getIdentifier();
		
		NaturalLanguageIndex index = new NaturalLanguageIndex();
		
		for (NaturalLanguageConcept concept : scheme.getConcepts()) {
			for (String label : concept.getAltLabel()) {
				ConceptStemSet set = index.toStemSet(schemeId, concept);
				
				System.out.println(concept.getIdentifier());
				for (String stem : set) {
					System.out.print("   ");
					System.out.println(stem);
				}
			}
		}
	}

}
