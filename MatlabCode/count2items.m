% Converts bin counts to a number of values in that interval
% so that these can be used in histogram function
function items = count2items(counts,minVal,maxVal)

range = maxVal-minVal;

items = [];

n = length(counts);

for i = 1:n
   items = [items minVal+(range/n)*(i-0.5)*ones(1,counts(i))]; 
end