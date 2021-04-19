# Princeton Algorithm

This repository contains my solution for the coursera course Algorithm I & II

## Useful Link

[Coursera course website : part1](https://www.coursera.org/learn/algorithms-part1/home/welcome)

[Coursera course website : part2](https://www.coursera.org/learn/algorithms-part2/home/welcome)

[Project 1: percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

[Project 2 : Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php) 

[Project 3 : colinear](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

[Project 4 : 8puzzle](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)

[Project 5 : kd-trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)

[Project 6 : WordNet](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)

[Project 7 : Seam-Carving](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

[Project 8 : Baseball Elimenation](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)

[Project 9 : Boggle](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)

[Project 10 : Burrows-Wheeler](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)

## Tips for projects

- project 1 : percolation

  Pay attention to the definition of the full site. If you add two virtual sites for optimization, you should may encounter the bug below :

  ```
  for a 3x3 grid, do the following instructions in turn:
  open(1, 3), open(2, 3), open(3, 3), open(3, 1)
  then the grid should look like this: (# for blocked, * for open)
  # # *
  # # *
  * # * 
  Obviously the site (3, 1) should not be full, because there is
  not a path from it to one top open site. But if you add two
  virtual sites, then the site (3, 1) will be connected with the
  bottom virtual site which is connected with the top virtual
  site through the straight line path on the right.
  ```

  To solve this, my solution is use two union-find, one with two virtual sites, and another with just one top virtual site. 

- Project 2 : Queues

  - Deque (linked-list based): to avoid handling the special case for adding into an empty deque, I add a guard node for every deque, this simplifies the code gracefully.
  - RandomizedDeque (resizing array based) : to achieve const amortized time for deque, you can first randomly pick one item then switch it with the last element in the array. Don't confuse between size (the number of elements in the deque) and capacity (the number of elements the deque **can** contains).

- Project 3 : collinear points

  - to avoid duplicated segment, I choose the strategy below : After sorting the points in terms of one specific point x, the first element must be the x itself. To add a new segment into your solution, you only need to ensure that x is the smallest point.

- Project 5 : kd trees
  
  - Nearest() : if the two subtrees both need to be checked, first check the subtree which the query point lies in, and remember that after you check one subtree, you may now prune the other subtree.

### Wanna Learn More ?

Check out [this repository](https://github.com/PKUFlyingPig/Self-learning-Computer-Science) which contains all my self-learning materials : )