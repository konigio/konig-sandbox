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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.konig.yaml.Yaml;

public class ConceptAnalyzerTest {

	public static void main(String[] args) throws Exception {
		File file = new File("src/test/resources/SchemeYamlTest/scheme.yaml");
		NaturalLanguageScheme scheme = Yaml.read(NaturalLanguageScheme.class, file);
		
		ConceptAnalyzer analyzer = new ConceptAnalyzer();
		List<ConceptIntersection> list = analyzer.stemIntersection(scheme);
		
		Collections.sort(list);
		
		System.out.println("======== OVERLAP ========");
		System.out.println();
		for (ConceptIntersection pair : list) {
			if (pair.getIntersection().size()>0) {
				String text = MessageFormat.format("{0} {1}:{2} {3}:{4} --", 
						pair.getIntersection().size(),
						pair.getA().getType(), pair.getA().getIdentifier(), 
						pair.getB().getType(), pair.getB().getIdentifier());
				System.out.print(text);
				for (String stem : pair.getIntersection()) {
					System.out.print(' ');
					System.out.print(stem);
				}
				System.out.println();
			}
		}

		System.out.println();
		System.out.println("======== Stemless Labels ========");
		NaturalLanguageIndex index = new NaturalLanguageIndex();
		
		for (NaturalLanguageConcept concept : scheme.getConcepts()) {
			boolean printConcept = true;
			for (String label : concept.getAltLabel()) {
				List<String> stemList = index.toStemList(label);
				if (stemList.isEmpty()) {
					if (printConcept) {
						printConcept = false;
						System.out.println();
						System.out.print(concept.getType());
						System.out.print(':');
						System.out.println(concept.getIdentifier());
					}
					System.out.print("   ");
					System.out.println(label);
				}
			}
		}
	}

}
