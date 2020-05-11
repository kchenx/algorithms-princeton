Union-Find Interview Questions
==============================

Social network connectivity
---------------------------

Given a social network containing *n* members and a log file containing *m* timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be *m* log *n* or better and use extra space proportional to *n*.

**Solution.** Use the union-find data structure, modeling each member as a node and each friendship as a Union. Add a `count` member initialized to *n* that represents the number of connected components. Iterate through the list of times at which members `x` and `y` became friends. Perform a union operation `Union(x, y)` and decrement `count` if and only if `x` and `y` are in different components (i.e., `Find(x) != Find(y))`. Stop when `count == 1` and return the index of the current time in the log file. 

The algorithm is correct because when we `Union` two nodes in different components, the number of components decreases by one. When the algorithm terminates, there is only one component left and thus all members are connected. This is the earliest time because even if we performed a `Union()` when `x` and `y` are in the same component, the number of connected components would not decrememnt since they were already connected.

The runtime is *O*(*m* log *n*) because we iterate through *m* timestamps, performing two finds and possibly one union per timestamp, and each operation takes log *n* time. We also take *O*(*n*) time to construct the union-find data structure, but we know that *n* = *O*(*m*) since we need at least *m* - 1 timestamps to connect the *n* members. The extra space used is *O*(*n*) since the union-find data structure uses a node for each of the *n* members.


Union-find with specific canonical element
------------------------------------------

Add a method `FindLargest()` to the union-find data type so that `FindLargest(i)` returns the largest element in the connected component containing *i*. The operations, `Union()`, `Find()`, and `FindLargest()` should all take logarithmic time or better.

For example, if one of the connected components is \{1, 2, 6, 9\}, then the `FindLargest()` method should return 99 for each of the four elements in the connected components.

**Solution.** Use the union-find data structure, with the following modification: we also keep an extra array `largest[n]` indexed by each component's representative element that stores the largest number in the component. Initially, `largest[i] = i` for all components (since they are singleton nodes). When we perform Union operations, we update this array accordingly; the normal code for `Union(x, y)` will already find `rootx = Find(x)` and `rooty = Find(y)`. Without loss of generality, say we attach `rooty` to `rootx`. Then we set `largest[rootx] = max(largest[rootx], largest[rooty])`. At any time, `FindLargest(i)` returns `largest[Find(i)]`.

The algorithm is correct because when we connect two components, the largest element of the combined component will be the maximum of each of the two original components, and we track this correctly in the array by each component's representative element.

We only add constant-time operations to `Union()` and do not modify `Find()`, so they still take *O*(*n*) time. `FindLargest()` calls `Find()` and uses a constant-time array access, so it also runs in *O*(*n*) time.


Successor with delete
---------------------

Given a set of *n* integers *S* = \{0, 1, ... , *n* - 1\} and a sequence of requests of the following form:
* Remove *x* from *S*
* Find the successor of *x*: the smallest *y* in *S* such that *y* ≥ *x*.
Design a data type so that all operations (except construction) take logarithmic time or better in the worst case.

**Solution.** Use the solution to the previous question. When removing an element `x`, do `Union(x, x + 1)`. Then `Successor(i)` returns `FindLargest(i)` (or an error if `i` > *n* - 1). To address the edge case of deleting element *n* - 1, add a dummy node `n`. Then if `FindLargest(i) == n`, then return an error instead.

The algorithm relies on the correctness of `FindLargest()` and maintains the invariants that each component has exactly one present (not deleted) element, which is also the largest element, and all elements in a component must be consecutive. This is true at the beginning because all components are singleton nodes. When we delete an element `x`, we `Union` it into the same component as `x + 1` and thus maintain all invariants: we are adding a smaller deleted element, so there is still exactly one present element, which is also the largest one, and all elements are still consecutive. Thus, to find the successor of `i`, we can just find the largest element in the component containing `i`. This is guaranteed to be the successor because all other consecutive elements between `i` and the found successor are deleted. The edge case of deleting element *n* - 1 still holds because we will `Union` it with a dummy node `n`. If `FindLargest(i) == n`, then that means there are no remaining successors in the set, so we can return an error.

The runtimes of `Union()`, `Find()`, and `FindLargest()` are all *O*(*n*) as previously. The runtime of `Successor()` calls `FindLargest()`, so it also runs in *O*(*n*) time.
