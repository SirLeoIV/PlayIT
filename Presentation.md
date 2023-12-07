## Presentation:

### 1. Introduction
* Research questions:
    * Can the MCTS agent consistently outperform baseline agents with different complexity levels?
    * What is the computational complexity of the Monte Carlo Tree Search algorithm in the context of playing Hearts? How does this complexity scale with the size of the game tree and other relevant factors?

### 2. What makes HEARTS special? 
* Partially Observable
* Imperfect Information
* Multi-Agent
* Stochastic
* Sequential / Turn based
* Zero-Sum 
* Large State-Space
* Large Action-Space (ca be limited)
* Long-term dependencies

### 3. Implementation (briefly)
* How do we define our State-Space?
  * History of the game:
    * Cards played by each player
    * -> cards each player cannot have
    * tricks won by each player
  * cards in hand
* How do we define our Action-Space?
   * Limit the actions of the opponents with the knowledge of the cards they cannot have
* How do we build our tree?
   * Each node holds a state
   * Each node has one parent
   * Each node has a list of children
   * Each layer represents a players turn
   * Every iteration either:
     * Rollout + Backpropagation
     * Expand a Leaf + Rollout the leaf + Backpropagation

### 4. Experiments
* Agents Tournament
* MCTS vs. Everyone
* MCTS_Smart vs Smart
* Depth.....

### 5. Results
* 

### 6. Conclusion/Answer to research questions

### 7. Outlook
