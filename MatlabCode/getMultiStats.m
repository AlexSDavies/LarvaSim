function [meanStats variances] = getMultiStats(name,n)

	meta = getStatMeta();

	for i = 1:n
		disp(i)
		multiStats(i) = getSimStats([name int2str(i)],0.1);
		meanDist(i) = mean(sqrt((sum(multiStats(i).data.midPos.^2,2))));
	end

	% Discard any with 0 turns 
	goodLarvaeIndeces = [multiStats.numTurns] > 0;
	
	multiStats = multiStats(goodLarvaeIndeces);
	meanDist = meanDist(goodLarvaeIndeces);
	
	n = length(multiStats);
	
	% Keep several sample paths
	samplePaths = {};
	numPaths = 0; i = 1;
	while(numPaths < 5 && i <= n)
		
		path = multiStats(i).data.midPos;
		if length(path) > 120/0.1;
			numPaths = numPaths + 1;
			samplePaths{numPaths} = path;
		end
		
		i = i+1;
	end
	
	meanStats.samplePaths = samplePaths;
	
	
% 	hist(meanDist,[0:20:160]);
% 	xlabel('Mean distance from source (mm)');
% 	ylabel('Num of larvae');
% 	meanStats.numTurns = mean([multiStats.numTurns]);
% 	variances.numTurns = var([multiStats.numTurns]);
% 	title('Normalisation')

	meanStats.data.midPos = [];
	for i = 1:n
		meanStats.data.midPos = [meanStats.data.midPos; multiStats(i).data.midPos];
		meanStats.data.paths{i} = multiStats(i).data.midPos;
	end
	
	meanStats.n = n;
	variances.n = n;
	
	numTurns = [multiStats.numTurns];
	meanStats.numTurns = mean(numTurns);
	variances.numTurns = var(numTurns);
	
	data = [multiStats.data];
	for i = 1:length(data)
		runTimes(i) = data(i).time(end);
	end
	
	%% Mean / variance of stats 
	
	meanStats.runTimes = mean(runTimes);
	variances.runTimes = var(runTimes);
	
	turnsPerMin = [multiStats.numTurns]./runTimes*60;
	meanStats.turnsPerMin = mean(turnsPerMin);
	variances.turnsPerMin = var(turnsPerMin);
	
	turnCumulativeProb = reshape([multiStats.turnCumulativeProb],8,n)';
	meanStats.turnCumulativeProb = mean(turnCumulativeProb);
	variances.turnCumulativeProb = var(turnCumulativeProb);
	
	bearingBeforeLeftTurns = reshape([multiStats.bearingBeforeLeftTurns],32,n)';
	meanStats.bearingBeforeLeftTurns = mean(bearingBeforeLeftTurns);
	variances.bearningBeforeLeftTurns = var(bearingBeforeLeftTurns);
	
	bearingAfterLeftTurns = reshape([multiStats.bearingAfterLeftTurns],32,n)';
	meanStats.bearingAfterLeftTurns = mean(bearingAfterLeftTurns);
	variances.bearningBeforeLeftTurns = var(bearingAfterLeftTurns);
	
	bearingBeforeRightTurns = reshape([multiStats.bearingBeforeRightTurns],32,n)';
	meanStats.bearingBeforeRightTurns = mean(bearingBeforeRightTurns);
	variances.bearningBeforeRightTurns = var(bearingBeforeRightTurns);
	
	bearingAfterRightTurns = reshape([multiStats.bearingAfterRightTurns],32,n)';
	meanStats.bearingAfterRightTurns = mean(bearingAfterRightTurns);
	variances.bearningAfterRightTurns = var(bearingAfterRightTurns);

	bearingBeforeTurnsToLow = reshape([multiStats.bearingBeforeTurnsToLow],32,n)';
	meanStats.bearingBeforeTurnsToLow = mean(bearingBeforeTurnsToLow);
	variances.bearningBeforeTurnsToLow = var(bearingBeforeTurnsToLow);
	
	bearingBeforeTurnsToHigh = reshape([multiStats.bearingBeforeTurnsToHigh],32,n)';
	meanStats.bearingBeforeTurnsToHigh = mean(bearingBeforeTurnsToHigh);
	variances.bearningBeforeTurnsToHigh = var(bearingBeforeTurnsToHigh);
	
	
	reorientationAtBearing = reshape([multiStats.reorientationAtBearing],36,n)';
	meanStats.reorientationAtBearing = nanmean(reorientationAtBearing);
	variances.reorientationAtBearing = nanvar(reorientationAtBearing);
	
	
	% Remove NaNs to calculate mean
	leftTurnProb = reshape([multiStats.leftTurnProb],12,n)';
	% leftTurnProbNoNans = leftTurnProb;
	% leftTurnProbNoNans(isnan(leftTurnProbNoNans)) = 0;
	% meanStats.leftTurnProb = nanmean(leftTurnProbNoNans);
	% variances.leftTurnProb = var(leftTurnProbNoNans);
	meanStats.leftTurnProb = nanmean(leftTurnProb);
	variances.leftTurnProb = nanvar(leftTurnProb);
	
	
	
	%% Num cast ratios
	allNumCastRatios = {multiStats.castNumRatio};
	maxCasts = max(cellfun('length',allNumCastRatios));
	castNumRatio = zeros(n,maxCasts);
	for i = 1:n
		castNumRatio(i,1:length(multiStats(i).castNumRatio)) = multiStats(i).castNumRatio;
	end
	meanStats.castNumRatio = mean(castNumRatio);
 	variances.castNumRatio = var(castNumRatio);

	oneCastRatios = reshape([multiStats.oneCastRatios],2,n)';
	oneCastRatios = oneCastRatios(~any(isnan(oneCastRatios), 2), :);
	meanStats.oneCastRatios = mean(oneCastRatios);
	variances.oneCastRatios = var(oneCastRatios);
	
	twoCastRatios = reshape([multiStats.twoCastRatios],4,n)';
	twoCastRatios = twoCastRatios(~any(isnan(twoCastRatios), 2), :);
	meanStats.twoCastRatios = mean(twoCastRatios);
	variances.twoCastRatios = var(twoCastRatios);
	
	% Have to remove NaN rows for instances where we have no three turns
	threeCastRatios = reshape([multiStats.threeCastRatios],8,n)';
	threeCastRatios = threeCastRatios(~any(isnan(threeCastRatios), 2), :);
	meanStats.threeCastRatios = mean(threeCastRatios);
	variances.threeCastRatios = var(threeCastRatios);
	
	distanceHist = reshape([multiStats.distanceHist],length(meta.distanceTics),n)';
	meanStats.distanceHist = mean(distanceHist);
	variances.distanceHist = var(distanceHist);
	
	
	meanStats.variances = variances;
		
	
	%% HCG stuff
	
	meanStats.data.preHCGbearing = [];
	meanStats.data.postHCGangleChange = [];
	for i = 1:n
		meanStats.data.preHCGbearing = [meanStats.data.preHCGbearing multiStats(i).preHCGbearing];
		meanStats.data.postHCGangleChange = [meanStats.data.postHCGangleChange multiStats(i).postHCGangleChange];	
	end
	
	
	%% 
	
	