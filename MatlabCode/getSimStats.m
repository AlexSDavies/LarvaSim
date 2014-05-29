function simData = getSimStats(name,timestep)

dataFile = ['data_' name];
turnsFile = ['turns_' name];

meta = getStatMeta();

%% Read data

rawData = dlmread(['../Data/' dataFile], ' ',2,0);

data = simDataToStruct(rawData);

turnIndeces = getTurns(data.headPos,data.midPos,data.tailPos,timestep);

turnStartIndeces = [];
turnEndIndeces = [];

if ~isempty(turnIndeces)
	turnStartIndeces = turnIndeces(:,1);
	turnEndIndeces = turnIndeces(:,2);
end

simData.numTurns = length(turnIndeces);

turnStartData = simDataToStruct(rawData(turnStartIndeces,:));
turnEndData = simDataToStruct(rawData(turnEndIndeces,:));

%% BEARINGS NOW IN RANGE 0 - 2*PI
data.bearing(data.bearing < 0) = data.bearing(data.bearing < 0) + 2*pi;
turnStartData.bearing(turnStartData.bearing < 0) = turnStartData.bearing(turnStartData.bearing < 0) + 2*pi;
turnEndData.bearing(turnEndData.bearing < 0) = turnEndData.bearing(turnEndData.bearing < 0) + 2*pi;

simData.data = data;

% n is a count of how many runs this was taken from, so just 1 here
simData.n = 1;

%% Get turn info

% -1 if turn leads to lower odour, +1 if higher
% turnLowHigh = sign(turnEndData.odourVal - turnStartData.odourVal);
preTurnBearing = turnStartData.bearing;
preTurnBearing(preTurnBearing > pi) = pi - preTurnBearing(preTurnBearing > pi);
postTurnBearing = turnEndData.bearing;
postTurnBearing(postTurnBearing > pi) = pi - postTurnBearing(postTurnBearing > pi);
% turnLowHigh = sign(postTurnBearing - preTurnBearing);
% turnLowHigh(preTurnBearing < 0) = -turnLowHigh(preTurnBearing < 0);


% Changes in bearing 
bearingDiff = turnEndData.bearing - turnStartData.bearing;
bearingDiff(bearingDiff > pi) = bearingDiff(bearingDiff > pi) - 2*pi;
bearingDiff(bearingDiff < -pi) = 2*pi + bearingDiff(bearingDiff < -pi);

% -1 if left, 1 if right
turnLeftRight = sign(bearingDiff);

turnLowHigh = turnLeftRight;
turnLowHigh(preTurnBearing > 0) = -turnLowHigh(preTurnBearing > 0);


% List of head casts preceding the turn
% 1 for cast to higher odour
% -1 for cast to lower odour

headCasts = {};

for i = 1:simData.numTurns
    
    % Get interval we're looking for head casts over
    endIndex = turnIndeces(i);
    startIndex = endIndex - 5/timestep;
	
	if(i > 1)
		prevTurnEnd = turnEndIndeces(i-1);
		if prevTurnEnd > startIndex
			startIndex = prevTurnEnd;
		end
	end
	
    startIndex = max(startIndex,1);

    preTurnHeadAngles = data.headAngle(startIndex:endIndex);

    preTurnOdourVal = data.odourVal(startIndex:endIndex);
    
    headCastIndeces = getCrossings(preTurnHeadAngles,0.5236);
    
	headCastDir = +(preTurnHeadAngles(headCastIndeces == 1) > 0);
	headCastDir(headCastDir == 0) = -1;
	
	% Bearing less than 180, so -ve turn is good
 	preTurnBearing = data.bearing(endIndex);
 	if preTurnBearing < pi && preTurnBearing > 0
 		headCastVals = -headCastDir;
 	% Bearing greater than 180, so +ve turn is good
 	elseif preTurnBearing > pi && preTurnBearing <2*pi
 			headCastVals = headCastDir;
 	else
 		disp('ohgodwhy');
 	end
	
    headCasts{i} = headCastVals;
	
end


%% Evren head cast groups

headCastTimes = abs(data.headAngle) > deg2rad(30);
headCastDiff = diff([headCastTimes; 0]);
headCastStartInd = find(headCastDiff == 1) + 1;
headCastEndInd = find(headCastDiff == -1);

preHCGbearing = [];
postHCGbearing = [];

if ~isempty(headCastStartInd)
	 
	% Find HCGs
	j = 1;
	headCastGroup{j} = [1];

	for i = 2:length(headCastStartInd)
		% If not moved since last head cast
		if norm(data.midPos(headCastStartInd(i-1),:) - data.midPos(headCastStartInd(i),:)) < 2
			headCastGroup{j} = [headCastGroup{j} i];
		else
			j = j+1;
			headCastGroup{j} = [i];
		end

	end

	% Get start and end time of HCGs
	hcgStarts = [];
	hcgEnds = [];
	for i = 1:length(headCastGroup)
		startInd = headCastGroup{i}(1);
		hcgStarts = [hcgStarts headCastStartInd(startInd)];
		endInd = headCastGroup{i}(end);
		hcgEnds = [hcgEnds headCastEndInd(endInd)];
	end

	for i = 1:length(headCastGroup)

		%TODO: Angular mean
		startInd = max(hcgStarts(i)-6/timestep,1);
		preHeadCastBearings = data.bearing(startInd:hcgStarts(i));
		preHCGbearing = [preHCGbearing angularMean(preHeadCastBearings)];

		endInd = min(hcgEnds(i)+6/timestep,length(data.bearing));
		postHeadCastBearings = data.bearing(hcgEnds(i):endInd);
		postHCGbearing = [postHCGbearing angularMean(postHeadCastBearings)];

	end

end

simData.preHCGbearing = normaliseAngle(preHCGbearing);
simData.postHCGangleChange = normaliseAngle(postHCGbearing - preHCGbearing);



%% Bearing / turn probability

x = 11.25:22.5:168.75;
absBearing = turnStartData.bearing;
absBearing(absBearing > pi) = 2*pi - absBearing(absBearing > pi);
numpoints = hist(rad2deg(absBearing),x);

if(sum(numpoints) > 0)
	simData.turnCumulativeProb = cumsum(numpoints)/sum(numpoints);
else
	simData.turnCumulativeProb = zeros(size(x));
end

%% Left / right pre / post turn bearing

circleTics = 0:11.25:348.75;

leftTurnIndeces = find(turnLeftRight == -1);
rightTurnIndeces = find(turnLeftRight == 1);

simData.bearingBeforeLeftTurns = hist(rad2deg(turnStartData.bearing(leftTurnIndeces)),circleTics);
simData.bearingAfterLeftTurns = hist(rad2deg(turnEndData.bearing(leftTurnIndeces)),circleTics);

simData.bearingBeforeRightTurns = hist(rad2deg(turnStartData.bearing(rightTurnIndeces)),circleTics);
simData.bearingAfterRightTurns = hist(rad2deg(turnEndData.bearing(rightTurnIndeces)),circleTics);


%% Turn to low / high bearing

turnLowIndeces = (turnLowHigh == -1);
turnHighIndeces = (turnLowHigh == 1);

simData.bearingBeforeTurnsToLow = hist(rad2deg(turnStartData.bearing(turnLowIndeces)),circleTics);
simData.bearingBeforeTurnsToHigh = hist(rad2deg(turnStartData.bearing(turnHighIndeces)),circleTics);


%% Scatter bearing / |delta angle|

% deltaAngles = data(turnEndIndeces,3) - data(turnStartIndeces,3);
% deltaAngles(deltaAngles > pi) = 2*pi - deltaAngles(deltaAngles > pi);
% 
% simData.deltaAngles(deltaAngles < -pi) = 2*pi + deltaAngles(deltaAngles < -pi);


%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;
probs = zeros(1,12);


for i = 1:numIntervals
    
    angle = interval*(i-1);
    
    totalCount = sum((turnStartData.bearing >= angle) & (turnStartData.bearing < angle+interval));
    leftCount = sum((turnStartData.bearing >= angle) & (turnStartData.bearing < angle+interval) & (bearingDiff < 0));
	rightCount = sum((turnStartData.bearing >= angle) & (turnStartData.bearing < angle+interval) & (bearingDiff > 0));
	
	% disp([num2str(rad2deg(angle)) '-' num2str(rad2deg(angle+interval)) ': ' num2str(leftCount) ' ' num2str(rightCount)]);
	
    if (totalCount == 0)
        probs(i) = NaN;
    else
        probs(i) = leftCount/totalCount;
    end
    
end

% Convert to -pi - pi range to match larva data
probs = [probs(7:12) probs(1:6)];

simData.leftTurnProb = probs;


%% Head casts

numCasts = cellfun(@length,headCasts);


castCounts = [];


for i = 1:max(numCasts)
   castCounts(i) = sum(numCasts == i); 
end

simData.castNumRatio = castCounts./sum(castCounts);

% Casts H, L
oneCastCounts(1) = sum(cellfun(@(x) isequal(x,[1]),headCasts));
oneCastCounts(2) = sum(cellfun(@(x) isequal(x,[-1]),headCasts));

simData.oneCastRatios = oneCastCounts./sum(oneCastCounts);

% Casts HH, LH, HL, LL
twoCastCounts(1) = sum(cellfun(@(x) isequal(x,[1 1]'),headCasts));
twoCastCounts(2) = sum(cellfun(@(x) isequal(x,[-1 1]'),headCasts));
twoCastCounts(3) = sum(cellfun(@(x) isequal(x,[1 -1]'),headCasts));
twoCastCounts(4) = sum(cellfun(@(x) isequal(x,[-1 -1]'),headCasts));

simData.twoCastRatios = twoCastCounts./sum(twoCastCounts);


% Casts HHH, HLH, LHH, LLH, HHL, HLL, LHL, LLL
threeCastCounts = zeros(8,1);
threeCastCounts(1) = sum(cellfun(@(x) isequal(x,[1 1 1]'),headCasts));
threeCastCounts(2) = sum(cellfun(@(x) isequal(x,[1 -1 1]'),headCasts));
threeCastCounts(3) = sum(cellfun(@(x) isequal(x,[-1 1 1]'),headCasts));
threeCastCounts(4) = sum(cellfun(@(x) isequal(x,[-1 -1 1]'),headCasts));
threeCastCounts(5) = sum(cellfun(@(x) isequal(x,[1 1 -1]'),headCasts));
threeCastCounts(6) = sum(cellfun(@(x) isequal(x,[1 -1 -1]'),headCasts));
threeCastCounts(7) = sum(cellfun(@(x) isequal(x,[-1 1 -1]'),headCasts));
threeCastCounts(8) = sum(cellfun(@(x) isequal(x,[-1 -1 -1]'),headCasts));

simData.threeCastRatios = threeCastCounts./sum(threeCastCounts);


%% Distance from 0,0
distance = sqrt(sum(data.midPos.^2,2));
distanceHist = hist(distance,meta.distanceTics);
distanceHist = distanceHist./sum(distanceHist);

simData.distanceHist = distanceHist;



%% Weathervane

allReorientation = (data.angle(2:end) - data.angle(1:end-1))./0.1;

runIndeces = find(abs(allReorientation) < deg2rad(15));

runBearing = data.bearing(runIndeces);
runReorientation = allReorientation(runIndeces);

% runBearingsLarva(runBearingsLarva > pi) = -2*pi + runBearingsLarva(runBearingsLarva > pi);
runBearing = normaliseAngle(runBearing);

a = [-180:10:170];

for i = 1:length(a)
	
	ang = a(i);
	
	ind = find(runBearing > deg2rad(ang) & runBearing < deg2rad(ang+10));
	
	reorientationAtBearing(i) = rad2deg(mean(runReorientation(ind)));
	
end

simData.reorientationAtBearing = reorientationAtBearing;

% simData.runBearing = runBearing;
% simData.runReorientation = runReorientation;



% 
% % Head cast termination angles
% moved = zeros(1,length(data.midPos));
% endOfCast = zeros(1,length(data.midPos));
% for i = 2:length(data.midPos)
% 	moved(i) = ((data.midPos(i,1) == data.midPos(i-1,1)) && (data.midPos(i,2) == data.midPos(i-1,2)));
% 	if (moved(i)==1 && moved(i-1)==0)
% 		endOfCast(i) = 1;
% 	end
% end
% 
% headAngleTerm = data.headAngle(endOfCast==1);
% bearingCastTerm = data.bearing(endOfCast==1);
% 
% headAngleTermLeft = headAngleTerm(bearingCastTerm > pi);
% headAngleTermRight = headAngleTerm(bearingCastTerm < pi);
% 
% simData.castTerminationHeadAngle = headAngleTerm;
% 
% simData.castTerminationLeft = headAngleTermLeft;
% simData.castTerminationRight = headAngleTermRight;
