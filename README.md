# SkipList implementation with java, generics and iterators
The SkipList is an alternative implementation of an ordered LinkedList that aims to enhance its `search()` and `insert()` methods (the costs are logarithmic for the SkipList and linear for the LinkedList).  This structure consists of a group of sublists, where every has, ideally, half the elements of its predecessor.  Knowing that all elements are always ordered, this allows the search method to start in the higher levels of the list, those that have less elements, and going down as it gets closer to the searched element.

![alt text](https://github.com/mireiagasco/SkipList-Java/blob/main/SkipList.png)

