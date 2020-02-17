#Bandit Arms 

An application of the simulations experimented in Bandit Arm Algorithm for Website Optimisation in Kotlin.
The E-Book : https://www.amazon.com/Bandit-Algorithms-Website-Optimization-Developing/dp/1449341330

##Requirements 
For the project
- Java 8+
- Maven 3.X

For analysis
- awk
- gnuplot

## Run

Run this and follow the instructions
```
$ mvn clean install && java -jar target/cli-bandit.jar --help
Usage: bandit [OPTIONS]

Options:
  --simulations INT  The Number of simulation you want to run
  --trials INT       The number of trials for a simulation
  --arms FLOAT       The Bernoulli probabilities of your arms (multiple values
                     separated by ",")
  --algorithm TEXT   Algorithm Type. Values : UCB, Softmax, EpsilonGreedy
  -t, -e FLOAT       Threshold of non-annealed algorithms
  --annealed         Annealed version of algorithm. (only for UCB, Softmax).
                     Cancels temperature
  --file-name TEXT   The output file name
  -h, --help         Show this message and exit  
```

## Analyse the results 

Display average reward for the best arm

```
$ awk -F'\t' \
   '{ count[$2]+=1; curr[$2]+=$4; } END{ for(idx in curr) { print idx"\t"curr[idx]/count[idx] } }' /tmp/ucb.tsv \
   | sort -n -t'\t' -k1,1 \
   | gnuplot -p -e "set yrange [0:*]; plot '<cat' using 1:2"
```

Display average cumulative reward for the best arm

```
$ awk -F'\t' \
     '{ count[$2]+=1; curr[$2]+=$5; } END{ for(idx in curr) { print idx"\t"curr[idx]/count[idx] } }' /tmp/ucb.tsv \
     | sort -n -t'\t' -k1,1 \
     | gnuplot -p -e "set yrange [0:*]; plot '<cat' using 1:2"
```
