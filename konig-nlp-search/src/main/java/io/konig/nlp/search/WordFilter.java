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


import java.util.HashSet;
import java.util.Set;

public class WordFilter {
	
	private static final Set<String> SKIP = new HashSet<>();
	static {
		
		// Prepositions
		
		SKIP.add("aboard");
		SKIP.add("about");
		SKIP.add("above");
		SKIP.add("absent");
		SKIP.add("across");
		SKIP.add("cross");
		SKIP.add("after");
		SKIP.add("against");
		SKIP.add("along");
		SKIP.add("alongside");
		SKIP.add("amid");
		SKIP.add("amidst");
		SKIP.add("among");
		SKIP.add("amongst");
		SKIP.add("apropos");
		SKIP.add("around");
		SKIP.add("as");
		SKIP.add("astride");
		SKIP.add("at");
		SKIP.add("atop");
		SKIP.add("ontop");
		SKIP.add("bar");
		SKIP.add("before");
		SKIP.add("afore");
		SKIP.add("behind");
		SKIP.add("below");
		SKIP.add("beneath");
		SKIP.add("beside");
		SKIP.add("besides");
		SKIP.add("between");
		SKIP.add("beyond");
		SKIP.add("but");
		SKIP.add("by");
		SKIP.add("circa");
		SKIP.add("come");
		SKIP.add("despite");
		SKIP.add("down");
		SKIP.add("during");
		SKIP.add("except");
		SKIP.add("for");
		SKIP.add("from");
		SKIP.add("in");
		SKIP.add("inside");
		SKIP.add("into");
		SKIP.add("less");
		SKIP.add("like");
		SKIP.add("minus");
		SKIP.add("near");
		SKIP.add("nearer");
		SKIP.add("nearest");
		SKIP.add("notwithstanding");
		SKIP.add("of");
		SKIP.add("off");
		SKIP.add("on");
		SKIP.add("onto");
		SKIP.add("opposite");
		SKIP.add("out");
		SKIP.add("outen");
		SKIP.add("outside");
		SKIP.add("over");
		SKIP.add("pace");
		SKIP.add("past");
		SKIP.add("per");
		SKIP.add("post");
		SKIP.add("pre");
		SKIP.add("pro");
		SKIP.add("qua");
		SKIP.add("sans");
		SKIP.add("save");
		SKIP.add("short");
		SKIP.add("since");
		SKIP.add("than");
		SKIP.add("through");
		SKIP.add("thru");
		SKIP.add("throughout");
		SKIP.add("thruout");
		SKIP.add("to");
		SKIP.add("toward");
		SKIP.add("towards");
		SKIP.add("under");
		SKIP.add("underneath");
		SKIP.add("unlike");
		SKIP.add("until");
		SKIP.add("till");
		SKIP.add("up");
		SKIP.add("upon");
		SKIP.add("upside");
		SKIP.add("versus");
		SKIP.add("via");
		SKIP.add("vice");
		SKIP.add("with");
		SKIP.add("within");
		SKIP.add("without");
		SKIP.add("worth");	
		
		// Pronouns
		
		SKIP.add("i");
		SKIP.add("we");
		SKIP.add("me");
		SKIP.add("us");
		SKIP.add("you");
		SKIP.add("she");
		SKIP.add("her");
		SKIP.add("he");
		SKIP.add("him");
		SKIP.add("it");
		SKIP.add("they");
		SKIP.add("them");
		SKIP.add("that");
		SKIP.add("which");
		SKIP.add("who");
		SKIP.add("whom");
		SKIP.add("whose");
		SKIP.add("whichever");
		SKIP.add("whoever");
		SKIP.add("whomever");
		SKIP.add("this");
		SKIP.add("these");
		SKIP.add("those");
		SKIP.add("anybody");
		SKIP.add("anyone");
		SKIP.add("anything");
		SKIP.add("each");
		SKIP.add("either");
		SKIP.add("everybody");
		SKIP.add("everyone");
		SKIP.add("everything");
		SKIP.add("neither");
		SKIP.add("nobody");
		SKIP.add("nothing");
		SKIP.add("one");
		SKIP.add("somebody");
		SKIP.add("someone");
		SKIP.add("something");
		SKIP.add("both");
		SKIP.add("few");
		SKIP.add("many");
		SKIP.add("several");
		SKIP.add("all");
		SKIP.add("any");
		SKIP.add("most");
		SKIP.add("some");
		SKIP.add("none");
		SKIP.add("myself");
		SKIP.add("ourselves");
		SKIP.add("yourself");
		SKIP.add("yourselves");
		SKIP.add("himself");
		SKIP.add("herself");
		SKIP.add("itself");
		SKIP.add("themselves");
		SKIP.add("who");
		SKIP.add("what");
		SKIP.add("which");
		SKIP.add("whom");
		SKIP.add("whose");
		SKIP.add("my");
		SKIP.add("your");
		SKIP.add("his");
		SKIP.add("her");
		SKIP.add("its");
		SKIP.add("our");
		SKIP.add("your");
		SKIP.add("their");
		SKIP.add("mine");
		SKIP.add("yours");
		SKIP.add("his");
		SKIP.add("hers");
		SKIP.add("ours");
		SKIP.add("yours");
		SKIP.add("theirs");
		
		// Articles
		
		SKIP.add("the");
		SKIP.add("an");
		SKIP.add("a");
		
		// Auxiliary Verbs
		
		SKIP.add("be");
		SKIP.add("can");
		SKIP.add("could");
		SKIP.add("dare");
		SKIP.add("do");
		SKIP.add("don"); // (don't)
		SKIP.add("have");
		SKIP.add("may");
		SKIP.add("might");
		SKIP.add("must");
		SKIP.add("need");
		SKIP.add("ought");
		SKIP.add("shall");
		SKIP.add("should");
		SKIP.add("will");
		SKIP.add("would");
		
		// Conjunctions
		SKIP.add("and");
		SKIP.add("or");
		SKIP.add("but");
		SKIP.add("nor");
		SKIP.add("so");
		SKIP.add("for");
		SKIP.add("yet");
		SKIP.add("although");
		SKIP.add("as");
		SKIP.add("if");
		SKIP.add("because");
		SKIP.add("before");
		SKIP.add("even");
		SKIP.add("though");
		SKIP.add("once");
		SKIP.add("since");
		SKIP.add("so");
		SKIP.add("that");
		SKIP.add("though");
		SKIP.add("till");
		SKIP.add("unless");
		SKIP.add("until");
		SKIP.add("what");
		SKIP.add("when");
		SKIP.add("whenever");
		SKIP.add("wherever");
		SKIP.add("whether");
		SKIP.add("while");
		
	}
	
	public static boolean accept(String word) {
		
		return 
			word.length()>1 &&
			Character.isAlphabetic(word.charAt(0)) &&
			!SKIP.contains(word);
	}

}
