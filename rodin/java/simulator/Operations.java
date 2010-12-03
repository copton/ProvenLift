package simulator;

import java.util.HashMap;
import java.util.HashSet;


public class Operations {
// HELPER METHODS
    
    // start‥end
	public static HashSet<Integer> interval(int start, int end) {
		HashSet<Integer> result = new HashSet<Integer>();
		for (int i = start; i <= end; i++)
			result.add(i);
		return result;
	}
    
	// relation[keys]
	public static <S,T extends Object> HashSet<T> imageOf(HashSet<S> keys, HashMap<S,HashSet<T>> relation) {
		HashSet<T> result = new HashSet<T>();
		for (S key : keys){
			HashSet<T> set = relation.get(key);
			if (set != null)
				result.addAll(set);
		}
		return result;
	}
	
	// element ∈ set
	public static <T extends Object> boolean isIn(T element, HashSet<T> set) {
		return set.contains(element);
	}

	// a ↦ b
	public static <S,T extends Object> HashMap<S,HashSet<T>> mapsto(S a, T b) {
		HashMap<S,HashSet<T>> map = new HashMap<S,HashSet<T>>();
		HashSet<T> set = new HashSet<T>();
		set.add(b);
		map.put(a,set);
		return map;
	}

	// function(key)
	public static <S,T extends Object> T valueOf(S key, HashMap<S,HashSet<T>> function) {
		return function.get(key).iterator().next();
	}

	// {a,b} with a,b being relations
	public static <S,T extends Object> HashMap<S,HashSet<T>> setext(HashMap<S,HashSet<T>> a, HashMap<S,HashSet<T>> b) {
		HashMap<S,HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : a.keySet())
			result.put(key, (HashSet<T>)a.get(key).clone());
		for (S key : b.keySet()) {
			if (result.containsKey(key)) {
				result.get(key).addAll(b.get(key));
			}
			else
				result.put(key, (HashSet<T>)b.get(key).clone());
		}
		return result;
	}

	// {a,b} with a,b being sets 
	public static <S extends Object> HashSet<S> setext(HashSet<S> a, HashSet<S> b) {
		HashSet<S> result = new HashSet<S>();
		result.addAll(a);
		result.addAll(b);
		return result;
	}
	
	// {a,b} with a,b being elements 
	public static <S extends Object> HashSet<S> setext(S a, S b) {
		HashSet<S> result = new HashSet<S>();
		result.add(a);
		result.add(b);
		return result;
	}
	
	// {a} with a being a relation
	public static <S,T extends Object> HashMap<S, HashSet<T>> setext(HashMap<S, HashSet<T>> a) {
		return a;
	}
	
	// {a} with a being a set
	public static <S extends Object> HashSet<S> setext(HashSet<S> a) {
		return a;
	}
	
	// {a} with a being a element
	public static <S extends Object> HashSet<S> setext(S a) {
		HashSet<S> result = new HashSet<S>();
		result.add(a);
		return result;
	}
	
	// a ◁- b
	public static <S,T extends Object> HashMap<S,HashSet<T>> ovr(HashMap<S,HashSet<T>> a, HashMap<S,HashSet<T>> b) {
		HashMap<S,HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : a.keySet())
			result.put(key, (HashSet<T>)a.get(key).clone());
		for (S key : b.keySet())
			result.put(key, (HashSet<T>)b.get(key).clone());
		return result;
	}

	// a \ b
	public static <S extends Object> HashSet<S> setminus(HashSet<S> a, HashSet<S> b) {
		HashSet<S> result = new HashSet<S>(a);
		result.removeAll(b);
		return result;
	}
	
	// a ∪ b with a,b being relations
	public static <S,T extends Object> HashMap<S, HashSet<T>> bunion(HashMap<S, HashSet<T>> a, HashMap<S, HashSet<T>> b) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : a.keySet())
			result.put(key, (HashSet<T>)a.get(key).clone());
		for (S key : b.keySet())
			if (result.containsKey(key))
				result.get(key).addAll(b.get(key));
			else
				result.put(key, (HashSet<T>)b.get(key).clone());
		return result;
	}
	
	// a ∪ b with a,b being sets
	public static <S extends Object> HashSet<S> bunion(HashSet<S> a, HashSet<S> b) {
		HashSet<S> result = new HashSet<S>(a);
		result.addAll(b);
		return result;
	}
	
	// a ∩ b with a,b being relations
	public static <S,T extends Object> HashMap<S, HashSet<T>> binter(HashMap<S, HashSet<T>> a, HashMap<S, HashSet<T>> b) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : a.keySet())
			if (b.containsKey(key)) {
				HashSet<T> set = (HashSet<T>)a.get(key).clone();
				set.retainAll(b.get(key));
				if (!set.isEmpty())
					result.put(key, set);
			}
		return result;
	}
	
	// a ∩ b with a,b being sets
	public static <S extends Object> HashSet<S> binter(HashSet<S> a, HashSet<S> b) {
		HashSet<S> result = new HashSet<S>(a);
		result.retainAll(b);
		return result;
	}
	
	public static <S,T extends Object> boolean equal(S a, T b) {
		if (a.getClass().equals(b.getClass()))
			return equalSS(a,b);
		else if (a instanceof String)
			return equalSString(b, (String)a);
		else if (b instanceof String)
			return equalSString(a, (String)b);
		else
			return false;
		
	}
	
	// a = b with a being elements
	public static <S extends Object> boolean equalSS(S a, S b) {
		return a.equals(b);
	}
	
	// a = BOOL, a = ∅
	public static <S extends Object> boolean equalSString(S a, String b) {
		if (b.equals("BOOL") && a instanceof HashSet<?>)
			return ((HashSet)a).contains(true) && ((HashSet)a).contains(false);
		if (b.equals("EMPTYSET")) {
			if (a instanceof HashSet<?>)
				return ((HashSet)a).isEmpty();
			else if (a instanceof HashMap<?, ?>)
				return ((HashMap)a).isEmpty();
		}
		return false; 
	}
	
	// a × b
	public static <S,T extends Object> HashMap<S, HashSet<T>> cprod(HashSet<S> a, HashSet<T> b) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : a)
			result.put(key, (HashSet<T>)b.clone());
		return result;
	}
	
	// min(set)
	public static int min(HashSet<Integer> set) {
		int min = 0;
		boolean init = false;
		for (int i : set) {
			if (!init) {
				min = i;
				init = true;
			}
			if (i < min)
				min = i;
		}
		return min;
	}
	
	// max(set)
	public static int max(HashSet<Integer> set) {
		int max = 0;
		boolean init = false;
		for (int i : set) {
			if (!init) {
				max = i;
				init = true;
			}
			if (i > max)
				max = i;
		}
		return max;
	}
	
	// dom(relation)
	public static <S,T extends Object> HashSet<S> dom(HashMap<S, HashSet<T>> relation) {
		return new HashSet<S>(relation.keySet());
	}
	
	// ran(relation)
	public static <S,T extends Object> HashSet<T> ran(HashMap<S, HashSet<T>> relation) {
		HashSet<T> result = new HashSet<T>();
		for (HashSet<T> set : relation.values())
			result.addAll(set);
		return result;
	}
	
	// domres ◁ relation
	public static <S,T extends Object> HashMap<S, HashSet<T>> domres(HashSet<S> domres, HashMap<S, HashSet<T>> relation) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : relation.keySet())
			if (domres.contains(key))
				result.put(key, (HashSet<T>)relation.get(key).clone());
		return result;
	}
	
	// domsub ⩤ relation
	public static <S,T extends Object> HashMap<S, HashSet<T>> domsub(HashSet<S> domsub, HashMap<S, HashSet<T>> relation) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : relation.keySet())
			if (!domsub.contains(key))
				result.put(key, (HashSet<T>)relation.get(key).clone());
		return result;
	}
	
	// relation ▷ ranres
	public static <S,T extends Object> HashMap<S, HashSet<T>> ranres(HashMap<S, HashSet<T>> relation, HashSet<T> ranres) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : relation.keySet())
			for (T value: relation.get(key))
				if (ranres.contains(value)) {
					if (!result.containsKey(key))
						result.put(key, new HashSet<T>());
					result.get(key).add(value);
				}
		return result;
	}

	// relation ⩥ ransub
	public static <S,T extends Object> HashMap<S, HashSet<T>> ransub(HashMap<S, HashSet<T>> relation, HashSet<T> ransub) {
		HashMap<S, HashSet<T>> result = new HashMap<S, HashSet<T>>();
		for (S key : relation.keySet())
			for (T value: relation.get(key))
				if (!ransub.contains(value)) {
					if (!result.containsKey(key))
						result.put(key, new HashSet<T>());
					result.get(key).add(value);
				}
		return result;
	}
	
	// a ⊂ b with a,b being relations
	public static <S,T extends Object> boolean isSubsetOf(HashMap<S, HashSet<T>> a, HashMap<S, HashSet<T>> b) {
		boolean equalityCheck = a.size() < b.size();
		// domain of a greater than domain of b
		if (a.size() > b.size())
			return false;
		for (S key : a.keySet()) {
			// an element contained in a but not b
			if (!b.containsKey(key))
				return false;
			HashSet<T> aa = a.get(key);
			HashSet<T> bb = b.get(key);
			equalityCheck = equalityCheck || aa.size() < bb.size();
			if (aa.size() > bb.size())
				return false;
			if (!bb.containsAll(aa))
				return false;			
		}
		return equalityCheck;
	}
	
	// a ⊂ b with a,b being sets
	public static <S extends Object> boolean isSubsetOf(HashSet<S> a, HashSet<S> b) {
		// cardinality of a greater or equal than cardinality of b
		if (a.size() >= b.size())
			return false;
		if (!b.containsAll(a))
			return false;			
		return true;
	}
	
	// a ⊂ BasicSet
	public static <S extends Object> boolean isSubsetOf(HashSet<S> a, String b) {
		if (b.equals("BOOL"))
			return a.size() <= 1;
		else if (b.equals("INTEGER"))
			return true;
		else if (b.equals("NATURAL")) {
			for (int value : (HashSet<Integer>)a)
				if (value < 0)
					return false;
			return true;
		}
		else if (b.equals("NATURAL1")) {
			for (int value : (HashSet<Integer>)a)
				if (value <= 0)
					return false;
			return true;
		}
		else
			return false;
	}
	
	// BasicSet ⊂ b
	public static <S extends Object> boolean isSubsetOf(String a, HashSet<S> b) {
		if (a.equals("EMPTYSET"))
			return !b.isEmpty();
		else
			return false;
	}
	
	// BasicSet ⊂ BasicSet
	public static <S extends Object> boolean isSubsetOf(String a, String b) {
		if (a.equals("NATURAL") && b.equals("INTEGER"))
			return true;
		else if (a.equals("NATURAL1") && (b.equals("NATURAL") || b.equals("INTEGER")))
			return true;
		else if (a.equals("EMPTYSET") && !b.equals("EMPTYSET"))
			return true;
		else
			return false;
	}
		
	// a ⊆ b with a,b being relations
	public static <S,T extends Object> boolean isSubsetOrEqual(HashMap<S, HashSet<T>> a, HashMap<S, HashSet<T>> b) {
		// domain of a greater than domain of b
		if (a.size() > b.size())
			return false;
		for (S key : a.keySet()) {
			// an element contained in a but not b
			if (!b.containsKey(key))
				return false;
			HashSet<T> aa = a.get(key);
			HashSet<T> bb = b.get(key);
			if (aa.size() > bb.size())
				return false;
			if (!bb.containsAll(aa))
				return false;			
		}
		return true;
	}
	
	// a ⊆ b with a,b being sets
	public static <S extends Object> boolean isSubsetOrEqual(HashSet<S> a, HashSet<S> b) {
		// cardinality of a greater than cardinality of b
		if (a.size() > b.size())
			return false;
		if (!b.containsAll(a))
			return false;			
		return true;
	}
	
	// a ⊆ BasicSet
	public static <S extends Object> boolean isSubsetOrEqual(HashSet<S> a, String b) {
		if (b.equals("BOOL"))
			return true;
		else if (b.equals("INTEGER"))
			return true;
		else if (b.equals("NATURAL")) {
			for (int value : (HashSet<Integer>)a)
				if (value < 0)
					return false;
			return true;
		}
		else if (b.equals("NATURAL1")) {
			for (int value : (HashSet<Integer>)a)
				if (value <= 0)
					return false;
			return true;
		}
		else if (b.equals("EMPTYSET"))
			return a.isEmpty();
		else
			return false;
	}
	
	// BasicSet ⊆ b
	public static <S extends Object> boolean isSubsetOrEqual(String a, HashSet<S> b) {
		if (a.equals("BOOL"))
			return b.size() == 2; 
		else if (a.equals("EMPTYSET"))
			return true;
		else
			return false;
	}
	
	// BasicSet ⊆ BasicSet
	public static <S extends Object> boolean isSubsetOrEqual(String a, String b) {
		if (a.equals(b))
			return true;
		else if (a.equals("EMPTYSET"))
			return true;
		else if (a.equals("NATURAL") && b.equals("INTEGER"))
			return true;
		else if (a.equals("NATURAL1") && (b.equals("INTEGER") || b.equals("NATURAL")))
			return true;
		else
			return false;
	}
        
}
