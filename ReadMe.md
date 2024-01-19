# PlayIT - Group 09 - Hearts

---
## How to run the game:
To run the program, open the command line/terminal on your machine, and navigate to the folder where the
jar file is located with:

``cd <path/to/folder/with/jar>``

Then, run the command:

``java -jar PlayIT-Hearts-Group-09.jar``

---

## How to run experiments and the ANN:

To run the experiments, open the repository in your preferred IDE and navigate to the file ``src/main/java/com/group09/playit/experiments/Experiment.java``. Here, you can execute the ``main()`` method. Specify the parameters as you like. The results will be saved in the folder ``src/main/java/com/group09/playit/experiments/results``.

To run experiments with the ANN (for example by using the MCTSAgentANN), you have to start a webserver that serves an endpoint to the trained ANN first. To do so, navigate to the folder ``src/main/java/com/group09/playit/ann`` and run the command ``python ANN-Flask.py``. This will start a webserver on port 8080. Now, you can run the experiments as described above. 

For the server to run properly, you need to have the following python packages installed: ``flask``, ``waitress``, ``keras``, ``numpy`` and ``tensorflow``. To install them with ``pip``, run the command ``pip install <package>``.

---
## Rules of the game:
- Welcome to our Game of Hearts
- The objective of the game is to be the player with the lowest score. Once a player’s score is higher than the losing score, the game is over, and the player with the lowest score wins.
- You play in rounds. All cards are dealt in the beginning of each round. The player who has the 2 of clubs begins. 
- Each player must follow the suit of the first card played, if possible.
- If not, then the player may discard any card of any suit. The highest card of the suit led wins the trick, and the winner of that trick leads next.
- Each time you win a trick, the trick is collected and placed face down in front of you. You add points to your score if there are hearts (1 point each) or the queen of spades (13 points) present.
- You cannot lead a trick with hearts until a heart has been discarded (hearts is broken). The queen can be led any time. 
- If you have a card of the leading suit, you must play it. You can set the losing score to your liking.
- For multiplayer: Each player must confirm they are playing before taking a turn. Turn the laptop facing away from the other players. 
- Have fun!!

---

Open `index.html` inside `/doc` to open the java-documentation.

Group 09, 19.01.2024
