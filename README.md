
Average Reward per trial
```
awk -F'\t' \
 '{ count[$2]+=1; curr[$2]+=$4; } END{ for(idx in curr) { print idx"\t"curr[idx]/count[idx] } }' /tmp/epsilson-0.1.tsv \
 | sort -n -t'\t' -k1,1
```

Average Cumulative reward per trial

```
awk -F'\t' \
 '{ count[$2]+=1; curr[$2]+=$5; } END{ for(idx in curr) { print idx"\t"curr[idx]/count[idx] } }' /tmp/epsilson-0.1.tsv \
 | sort -n -t'\t' -k1,1
```
