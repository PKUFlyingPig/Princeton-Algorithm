# Princeton Algorithm

This repository contains my solution for the coursera course Algorithm I & II

## Useful Link

[Coursera course website](https://www.coursera.org/learn/algorithms-part1/home/welcome)

[Project 1: percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

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

  

