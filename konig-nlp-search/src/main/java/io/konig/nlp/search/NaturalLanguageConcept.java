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


/**
 * A skos:Concept that has a preferred label and an optional list of alternate labels. 
 * Each alternate label is a different way to reference the concept in a natural language.
 * 
 * @author Greg McFall
 *
 */
public class NaturalLanguageConcept {
	
	private String identifier;
	private LabelList altLabel;
	private String type;
	
	

	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public LabelList getAltLabel() {
		return altLabel;
	}
	
	public void setAltLabel(LabelList altLabel) {
		this.altLabel = altLabel;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
