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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


/**
 * A utility that builds the document search index for a given NaturalLanguageScheme
 * @author Greg
 *
 */
public class NaturalLanguageIndex {
	
//	private static final String TYPE = "type";
//	private static final String ALT_LABEL = "altLabel";
	
	private static final String CONCEPT = "Concept";
	private static final String CONCEPT_TYPE = "ConceptType";
	private static final String SCHEME = "Scheme";
	private static final String LABEL = "label";
	private static final String STEM = "stem";
	private static final String TOKEN_DELIM = " \t\r\n!\"'#$%&()*,-./:;<=>?@[\\]^_`{|}~";
	
//	private List<String> listIndexes() {
//		List<String> result = new ArrayList<>();
//		SearchService service = SearchServiceFactory.getSearchService();
//		GetResponse<Index> response = service.getIndexes(GetIndexesRequest.newBuilder());
//		for (Index index : response) {
//			result.add(index.getName());
//		}
//		
//		return result;
//	}
	
	public List<String> listSchemes() {

		List<String> result = new ArrayList<>();

		DatastoreService service = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(SCHEME).setKeysOnly();
		
		Iterable<Entity> sequence = service.prepare(query).asIterable();
		for (Entity e : sequence) {
			String schemeId = e.getKey().getName();
			result.add(schemeId);
		}
		
		return result;
	}
	
	
	
	/**
	 * Put a scheme in this index.
	 * @param scheme
	 */
	public void put(NaturalLanguageScheme scheme) {
		
//		putFullTextSearch(scheme);
		putKeywordSearch(scheme);
	}
	
	private void putKeywordSearch(NaturalLanguageScheme scheme) {
		
		String schemeId = scheme.getIdentifier();
		Key schemeKey = schemeKey(schemeId);
		
		List<Entity> entityList = new ArrayList<>();
		entityList.add( new Entity(schemeKey) );
		for (NaturalLanguageConcept concept : scheme.getConcepts()) {
			ConceptStemSet stemSet = toStemSet(schemeId, concept);
			
			LabelList labelList = concept.getAltLabel();
			if (labelList.size() == 1) {
				stemSet.setLabel(labelList.get(0));
			}
			
			Entity e = toEntity(schemeKey, stemSet);
			entityList.add(e);
		}
		
		DatastoreService service = DatastoreServiceFactory.getDatastoreService();
		service.put(entityList);
	}

	private Key schemeKey(String schemeId) {
		return KeyFactory.createKey(SCHEME, schemeId);
	}
	
	private Key typeKey(Key schemeKey, String type) {
		return KeyFactory.createKey(schemeKey, CONCEPT_TYPE, type);
	}

	private Entity toEntity(Key schemeKey, ConceptStemSet stemSet) {
		List<String> list = new ArrayList<>(stemSet);
		Collections.sort(list);
		Key typeKey = typeKey(schemeKey, stemSet.getConceptType());
		Key key = conceptKey(typeKey, stemSet.getConceptId());
		
		Entity e = new Entity(key);
		if (stemSet.getLabel() != null) {
			e.setProperty(LABEL, stemSet.getLabel());
		}
		e.setProperty(STEM, list);
		
		return e;
	}
	
	private Key conceptKey(Key typeKey, String conceptId) {
		return KeyFactory.createKey(typeKey, CONCEPT, conceptId);
	}

	private List<String> stems(String text) {
		List<String> result = new ArrayList<>();
		addStems(result, text);
		return result;
		
	}
	
	public List<String> toStemList(String text) {
		ArrayList<String> list = new ArrayList<>();
		addStems(list, text);
		return list;
	}
	


	private void addUnfilteredStems(Collection<String> sink, String text) {
		StringTokenizer tokens = new StringTokenizer(text.toLowerCase(), TOKEN_DELIM);
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			String stem = Stemmer.stem(token);
			sink.add(stem);
		}
		
	}
	
	private void addStems(Collection<String> sink, String text) {

		StringTokenizer tokens = new StringTokenizer(text.toLowerCase(), TOKEN_DELIM);
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if (WordFilter.accept(token)) {
				String stem = Stemmer.stem(token);
				sink.add(stem);
			}
		}
		
	}



	public ConceptStemSet toStemSet(String schemeId, NaturalLanguageConcept concept) {
		ConceptStemSet stems = new ConceptStemSet(schemeId, concept.getType(), concept.getIdentifier());
		
		for (String label : concept.getAltLabel()) {
			List<String> stemList = toStemList(label);
			if (stemList.isEmpty()) {
				addUnfilteredStems(stems, label);
			} else {
				stems.addAll(stemList);
			}
		}
		
		return stems;
	}



//	private void putFullTextSearch(NaturalLanguageScheme scheme) {
//
//		SearchService service = SearchServiceFactory.getSearchService();
//		
//		Index index = service.getIndex(
//			IndexSpec.newBuilder().setName(scheme.getIdentifier()).build());
//		
//		List<Document> docList = new ArrayList<>();
//		for (NaturalLanguageConcept concept : scheme.getConcepts()) {
//			
//			Document.Builder builder = Document.newBuilder();
//			
//			builder.setId(concept.getIdentifier());
//			builder.addFacet(Facet.withAtom(TYPE, concept.getType()));
//			if (concept.getAltLabel() != null) {
//				for (String value : concept.getAltLabel()) {
//					builder.addField(Field.newBuilder().setName(ALT_LABEL).setText(value));
//				}
//			}
//			
//			docList.add(builder.build());
//		}
//		index.putAsync(docList);
//		
//	}

	public ConceptList search(String schemeId, String type, String text) {

		ConceptList list = new ConceptList();
		Key ancestorKey = schemeKey(schemeId);
		if (type != null) {
			ancestorKey = typeKey(ancestorKey, type);
		}
		
		NaturalLanguageConcept concept = findByLabel(ancestorKey, text);
		
		if (concept != null) {
			list.add(concept);
		} else {

			DatastoreService service = DatastoreServiceFactory.getDatastoreService();
			Map<Key, Integer> map = new HashMap<>();
			List<String> stemList = stems(text);
			if (stemList.isEmpty()) {
				stemList = new ArrayList<>();
				addUnfilteredStems(stemList, text);
			}
			for (String stem : stemList) {

				Query query = new Query(CONCEPT).setKeysOnly().setAncestor(ancestorKey);
				Filter filter =new FilterPredicate(STEM, FilterOperator.EQUAL, stem);
				query.setFilter(filter);
				
				Iterable<Entity> sequence = service.prepare(query).asIterable();
				for (Entity e : sequence) {
					Key key = e.getKey();
					Integer count = map.get(key);
					if (count == null) {
						map.put(key, 1);
					} else {
						map.put(key, count+1);
					}
				}
			}
			
			List<Entry<Key, Integer>> entryList = new ArrayList<>(map.entrySet());
			Collections.sort(entryList, new Comparator<Entry<Key, Integer>>() {

				@Override
				public int compare(Entry<Key, Integer> a, Entry<Key, Integer> b) {
					if (a == b) {
						return 0;
					}
					int result = b.getValue() - a.getValue();
					if (result == 0) {
						String x = a.getKey().getName();
						String y = b.getKey().getName();
						result = x.compareTo(y);
					}
					return result;
				}
			});
			
			for (Entry<Key, Integer> entry : entryList) {
				list.add(toConcept(entry.getKey()));
			}
		}
		
		
		return list;
	}




	private NaturalLanguageConcept findByLabel(Key ancestorKey, String text) {
		
		Query query = new Query(CONCEPT).setKeysOnly().setAncestor(ancestorKey);
		Filter filter = new FilterPredicate(LABEL, FilterOperator.EQUAL, text.toLowerCase().trim());
		query.setFilter(filter);

		DatastoreService service = DatastoreServiceFactory.getDatastoreService();
		
		Iterator<Entity> sequence = service.prepare(query).asIterator();
		
		if (sequence.hasNext()) {
			Entity e = sequence.next();
			return toConcept(e.getKey());
		}
	
	
		return null;
	}



	private NaturalLanguageConcept toConcept(Key key) {
		Key typeKey = key.getParent();
		NaturalLanguageConcept concept = new NaturalLanguageConcept();
		concept.setIdentifier(key.getName());
		concept.setType(typeKey.getName());
		return concept;
	}



//	public ConceptList search(String schemeId, String type, String text) {
//		
//		SearchService service = SearchServiceFactory.getSearchService();
//		
//		Index index = service.getIndex(IndexSpec.newBuilder().setName(schemeId));
//		
//		QueryOptions options = QueryOptions.newBuilder()
//			.setLimit(25)
//			.setFieldsToReturn()
//			.build();
//		
//		Query.Builder builder = Query.newBuilder().setOptions(options);
//		
//		if (type != null) {
//			FacetRefinement refinement = FacetRefinement.withValue(TYPE, type);
//			builder.addFacetRefinement(refinement);
//		}
//
//		Query query = builder.build(text);
//		Results<ScoredDocument> results = index.search(query);
//		
//		ConceptList list = new ConceptList();
//		for (ScoredDocument doc : results) {
//			list.add(toConcept(doc));
//		}
//		
//		return list;
//	}

//	private NaturalLanguageConcept toConcept(ScoredDocument doc) {
//		
//		NaturalLanguageConcept concept = new NaturalLanguageConcept();
//		concept.setIdentifier(doc.getId());
//		LabelList altLabel = new LabelList();
//		concept.setAltLabel(altLabel);
//		Iterable<Field> altLabelSequence = doc.getFields(ALT_LABEL);
//		if (altLabelSequence != null) {
//			for (Field field : altLabelSequence) {
//				altLabel.add(field.getText());
//			}
//		}
//		Iterable<Facet> facetSequence = doc.getFacets(TYPE);
//		if (facetSequence != null) {
//			Iterator<Facet> facets = facetSequence.iterator();
//			if (facets.hasNext()) {
//				Facet facet = facets.next();
//				concept.setType(facet.getAtom());
//			}
//		}
//		
//		return concept;
//	}

}
