package com.ccentre.findlongestcompoundword;

import com.sun.org.apache.xml.internal.utils.Trie;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

// this program find the longest words in a file
// than calculate total amount of compound words
public class FindLongestCompoundWord {
	static HashMap<String, Integer> hmap;
	static Trie trie;
	static String original_word;
	static HashSet<String> concat_words;

	// decomposition word to prefix and suffix
	// with recursion
	static void find_prefix(String word) {
		for (int i=0; i<word.length(); i++){
			String pref = word.substring(0, i+1);
			String suffix = word.substring(i+1);

			if (trie.get(pref)!=null) {
				if (trie.get(suffix)!=null) {
					concat_words.add(original_word);
					break;
				}
				find_prefix(suffix);
			}
		}
	}

	public static void find(File file)  throws FileNotFoundException{

		//measuring time of calculate
		long start = System.currentTimeMillis();

		Scanner s = new Scanner(file);

		String word;				// a word
		hmap = new HashMap<String, Integer>(); //initial
		trie = new Trie();
		concat_words = new HashSet<>();

		// read words from the file
		// fill up the hashmap with words
		// insert each word in trie
		while (s.hasNext()) {
			word = s.next();
			hmap.put(word, word.length());
			trie.put(word, word);
		}

		//sort the hash by word length
		hmap = hmap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		String longest = "";		// longest compound word
		String sec_longest = "";	// second longest compound word

		//process all words from bigger to smaller
		for(Map.Entry m:hmap.entrySet()){
			original_word = m.getKey().toString();
			word = m.getKey().toString();
			find_prefix(word);
			if (!concat_words.isEmpty()) {
				if (concat_words.size()==2) {
					String[] res = concat_words.toArray(new String[concat_words.size()]);
					longest = res[0];
					sec_longest = res[1];
				}
			}
		}

		//print spent time  for calculate
		System.out.println("Time for calculate - " + (System.currentTimeMillis()-start) + " ms");

		// print out the results
		System.out.println("Longest Compound Word:        " + longest);
		System.out.println("Second Longest Compound Word: " + sec_longest);
		System.out.println("Number of Compound Words: " + concat_words.size());
	}
}

