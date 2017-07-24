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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConceptAnalyzer {

	public List<ConceptIntersection> stemIntersection(NaturalLanguageScheme scheme) {
		NaturalLanguageIndex index = new NaturalLanguageIndex();
		List<ConceptIntersection> list = new ArrayList<>();
		String schemeId = scheme.getIdentifier();
		List<NaturalLanguageConcept> concepts = scheme.getConcepts();
		for (int i=0; i<concepts.size(); i++) {
			NaturalLanguageConcept a = concepts.get(i);
			Set<String> intersection = new HashSet<>();
			for (int j=0; j<i; j++) {
				NaturalLanguageConcept b = concepts.get(j);
				ConceptStemSet x = index.toStemSet(schemeId, a);
				ConceptStemSet y = index.toStemSet(schemeId, b);
				int count = 0;
				for (String stem : y) {
					if (x.contains(stem)) {
						intersection.add(stem);
					}
				}
				
				list.add(new ConceptIntersection(a, b, intersection));
			}
		}
		return list;
	}
	
	public List<StemlessLabel> findStemlessLabels(NaturalLanguageScheme scheme) {
		List<StemlessLabel> list = new ArrayList<>();
		NaturalLanguageIndex index = new NaturalLanguageIndex();
		for (NaturalLanguageConcept concept : scheme.getConcepts()) {
			for (String label : concept.getAltLabel()) {
				List<String> stemList = index.toStemList(label);
				if (stemList.isEmpty()) {
					list.add(new StemlessLabel(concept, label));
				}
			}
		}
		return list;
	}
	
}
