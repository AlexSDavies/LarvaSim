
numCasts = [];

for i = 1:20
	
	simStats = getSimStats(['noStimuli' num2str(i)],0.1);
	
	numTurns(i) = simStats.numTurns;
	
	numCasts = [numCasts simStats.numCasts];
	
end

