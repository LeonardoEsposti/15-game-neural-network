# 🎯 Project Overview

This project is meant to implement a neural network capable of **estimating**, with a certain level of accuracy, the **number of moves** needed to win the classic Game of Fifteen.

Not only does the network aim to minimize the *God's Number*, which is the maximum of 80 moves required for the hardest solvable configuration, but it also mathematically verifies if a configuration is solvable before attempting any solution.

## ⚙️ Technical Details

### 🧮 Mathematical Foundation

The most widely known algorithmic tool used to estimate the remaining number of moves is the **Manhattan Distance**:

$$\sum_{i=1}^{15} (|x_i - x_{target}| + |y_i - y_{target}|)$$

However the above formula does not consider *linear conflicts*, which are situations where two tiles are in their right row or column but in the wrong order, forcing one tile to temporarily occupy another row or column. Therefore, the formula must be **optimized** in such a way that linear conflicts can be solved.

In addition, **not every** configuration has a solution. Indeed, some of them cannot be solved since they violate the *parity condition* necessary for winning the game:
> a configuration is *solvable* if and only if the sum of the number of inversions and the row index of the empty tile (counted from the bottom) is an **even** number.

That's why the neural network must first check the solvability of configurations.

### 🧠 Neural Network Architecture

(inputs, outputs, layers, activation functions, cost function...)
