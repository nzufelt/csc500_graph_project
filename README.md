# CSC500 Spring 2021 Project 2
### Propagation across graphs

This is the starter code for the "networks" project. I will add more explanation of what we have in the future, but for now, there are three classes:

* `BasicGraph`, which houses an `ArrayList` of `BasicNode` objects and can `propagate` an effect across them.
* `BasicNode`, which is one element of the graph and has `neighbors` (other `BasicNode` objects) and holds onto a `NodeEffect`.
* `NodeEffect`, which can determine if a `BasicNode` is `affected`, and determines whether or not to `affect` another node.
