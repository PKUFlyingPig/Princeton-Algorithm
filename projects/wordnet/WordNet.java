/* *****************************************************************************
 *  Name: FlyingPig
 *  Date: 2021.3.10.
 *  Description: Word Net project
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final ArrayList<Set<String>> synsets;
    private final Map<String, Set<Integer>> nounToSynset;
    private final ArrayList<String> oriSynsets;
    private final Digraph wordNet;
    private final int n; // number of synsets
    private final SAP sapCounter;
    // constructor takes the name of the two input files
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException("argument for constructor can not be null");
        }
        synsets = new ArrayList<>();
        nounToSynset = new HashMap<>();
        oriSynsets = new ArrayList<>();
        readSynsets(synsetsFile);
        n = synsets.size();
        wordNet = new Digraph(n);
        readHypernyms(hypernymsFile);
        hasCycle();
        hasRoots();
        sapCounter = new SAP(wordNet);
    }

    private void hasCycle() {
        DirectedCycle cycle = new DirectedCycle(wordNet);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException("word net should not have a cycle in it");
        }
    }

    private void hasRoots() {
        int roots = 0;
        for (int i = 0; i < n; i++) {
            if (wordNet.outdegree(i) == 0) {
                roots++;
                if (roots >= 2) {
                    throw new IllegalArgumentException("word net should not have more than one root");
                }
            }
        }
    }

    // read in synsets
    private void readSynsets(String synsetsFile) {
        In in = new In(synsetsFile);
        String[] fields;
        while (!in.isEmpty()) {
            fields = in.readLine().split(",");
            oriSynsets.add(fields[1]);
            Set<String> tmp = new HashSet<>();
            for (String s : fields[1].split(" ")) {
                tmp.add(s);
                if (!nounToSynset.containsKey(s)) {
                    Set<Integer> newNoun = new HashSet<>();
                    newNoun.add(Integer.parseInt(fields[0]));
                    nounToSynset.put(s, newNoun);
                } else {
                    Set<Integer> oldNoun = nounToSynset.get(s);
                    oldNoun.add(Integer.parseInt(fields[0]));
                }
            }
            synsets.add(tmp);
        }
    }

    // read in hypernyms
    private void readHypernyms(String hypernymsFile) {
        In in = new In(hypernymsFile);
        String[] fields;
        while (!in.isEmpty()) {
            fields = in.readLine().split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                wordNet.addEdge(v, w);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word can not be null");
        }
        return nounToSynset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("noun is not in the word net");
        }
        return sapCounter.length(nounToSynset.get(nounA), nounToSynset.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("noun is not in the word net");
        }
        return oriSynsets.get(sapCounter.ancestor(nounToSynset.get(nounA), nounToSynset.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        return;
    }
}
