import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// this program find the longest words in a file
// than calculate total amount of compound words

public class FindLongestCompoundWord {

	public static void main(String[] args) throws FileNotFoundException {
		
		// file with compound words
		File file = new File("words.txt");

		// Trie
		Trie trie = new Trie();
		LinkedList<Pair<String>> queue = new LinkedList<Pair<String>>();
		
		// used to calculate the number of compound words
		HashSet<String> compoundWords = new HashSet<String>();

		@SuppressWarnings("resource")
		Scanner s = new Scanner(file);

		String word;				// a word
		List<Integer> sufIndices;	// indices of suffixes of a word
		
		// read words from the file
		// fill up the queue with words which have suffixes, who are
		// candidates to be compound words
		// insert each word in trie
		while (s.hasNext()) {
			word = s.next();		
			sufIndices = trie.getSuffixesStartIndices(word);
		
			for (int i : sufIndices) {
				if (i >= word.length())		// if index is out of bound
					break;					// it means suffixes of the word has
											// been added to the queue if there is any
				queue.add(new Pair<String>(word, word.substring(i)));
			}
	
			trie.insert(word);
		}
		
		Pair<String> p;				// a pair of word and its remaining suffix
		int maxLength = 0;			// longest compound word length
		String longest = "";		// longest compound word
		String sec_longest = "";	// second longest compound word

		while (!queue.isEmpty()) {
			p = queue.removeFirst();
			word = p.second();
			
			sufIndices = trie.getSuffixesStartIndices(word); // return starting indices of all suffixes of a word
			
			// if no suffixes found, which means no prefixes found
			// discard the pair and check the next pair
			if (sufIndices.isEmpty()) {
				continue;
			}

			for (int i : sufIndices) {
				if (i > word.length()) { // sanity check 
					break;
				}
				
				if (i == word.length()) { // no suffix, means it is a compound word
					// check if the compound word is the longest
					// if it is update both longest and second longest
					// words records
					if (p.first().length() > maxLength) {
						//sec_maxLength = maxLength;
						sec_longest = longest;
						maxLength = p.first().length();
						longest = p.first();
					}
			
					compoundWords.add(p.first());	// the word is compound word
					
				} else {
					queue.add(new Pair<String>(p.first(), word.substring(i)));
				}
			}
		}
		
		// print out the results
		System.out.println("Longest Compound Word:        " + longest);
		System.out.println("Second Longest Compound Word: " + sec_longest);
		System.out.println("Number of Compound Words: " + compoundWords.size());
	}
}

