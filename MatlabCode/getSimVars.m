function [simStats simVars] = getMultiStats(name,n)

	for i = 1:n
		multiStats(i) = getSimStats([name int2str(i)],0.1);
	end

	simStats.numTurns = mean([multiStats.numTurns]);
	simVars.numTurns = var([multiStats.numTurns]);
		