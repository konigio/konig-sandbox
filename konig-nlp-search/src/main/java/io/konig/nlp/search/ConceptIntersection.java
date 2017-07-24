package io.konig.nlp.search;

import java.util.Set;

public class ConceptIntersection implements Comparable<ConceptIntersection>{

	private NaturalLanguageConcept a;
	private NaturalLanguageConcept b;
	private Set<String> intersection;
	
	public ConceptIntersection(NaturalLanguageConcept a, NaturalLanguageConcept b, Set<String> intersection) {
		this.a = a;
		this.b = b;
		this.intersection = intersection;
	}

	public NaturalLanguageConcept getA() {
		return a;
	}

	public NaturalLanguageConcept getB() {
		return b;
	}

	public Set<String> getIntersection() {
		return intersection;
	}

	@Override
	public int compareTo(ConceptIntersection other) {

		int result = intersection.size() - other.intersection.size();
		if (result == 0) {
			String x = a.getType();
			String y = other.a.getType();
			
			result = x.compareTo(y);
			if (result == 0) {
				x = a.getIdentifier();
				y = other.a.getIdentifier();
				result = x.compareTo(y);
				if (result == 0) {
					x = b.getType();
					y = other.b.getType();
					result = x.compareTo(y);
					if (result == 0) {
						x = b.getIdentifier();
						y = other.b.getIdentifier();
						result = x.compareTo(y);
					}
				}
			}
		}
		return result;
	}
	
	
	
}
