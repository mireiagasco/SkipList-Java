# SkipList implementation with java, generics and iterators
The SkipList is an alternative implementation of an ordered LinkedList that aims to enhance its `search()` and `insert()` methods (the costs are logarithmic for the SkipList and linear for the LinkedList).  This structure consists of a group of sublists, where every has, ideally, half the elements of its predecessor.  Knowing that all elements are always ordered, this allows the search method to start in the higher levels of the list, those that have less elements, and going down as it gets closer to the searched element.

![alt text](https://github.com/mireiagasco/SkipList-Java/blob/main/SkipList.png)


## Basic Structure
The SkipList is build up from `Nodes`, which contain the data that has to be stored in the list and four references to other nodes from the list:
* `info`: data contained in the node.
* `above`: reference to the same node in the superior level of the SkipList.
* `below`: reference to the same node in the inferior level of the SkipList.
* `next`: reference to the next node in the sublist.
* `previous`: reference to the previous node in the sublist.

Then we have the SkipList itself, that consists of two references (to the `head` and `tail` of the list) and two integers that indicate the list length and height:
* `head`: reference to the top left node of the list.
* `tail`: reference to the top right node of the list.
* `numElem`: length of the list.
* `listHeight`: height of the list.

## Implemented Methods
The methods implemented for this SkipList are:
* `Constructor`: creates an empty SkipList.
* `insert()`: inserts the element passed as an argument in the correct position (the list is always ordered).
* `search()`: looks for an element in the list. Returns true if it exists and false if it does not.
* `delete()`: deletes the node with the data indicated as an argument from the list.  Throws the exception `ElementNotFound` if the element is not on the list.
* `size()`: returns the size of the list (number of elements contained).

## Implemented Exceptions
The exceptions used are:
* `ElementNotFound`: in case the element passed to the `delete()` method is not in the list.
