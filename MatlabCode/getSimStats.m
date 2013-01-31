function simData = getSimStats(dataFile,turnsFile,timestep)


%% Read data

rawData = dlmread(['../Data/' dataFile], ' ',1,0);



data = simDataToStruct(rawData);

turnIndeces = dlmread(['../Data/' turnsFile],' ',0,0);
turnStartIndeces = turnIndeces(:,1);
turnEndIndeces = turnIndeces(:,2);
turnCount = length(turnIndeces);

turnStartData = simDataToStruct(rawData(turnStartIndeces,:));
turnEndData = simDataToStruct(rawData(turnEndIndeces,:));

%% Need to clean up what range angles are supposed to be

data.bearing(data.bearing < 0) = data.bearing(data.bearing < 0) + 2*pi;

turnStartData.bearing(turnStartData.bearing < 0) = turnStartData.bearing(turnStartData.bearing < 0) + 2*pi;
turnEndData.bearing(turnEndData.bearing < 0) = turnEndData.bearing(turnEndData.bearing < 0) + 2*pi;

simData.rawData = rawData;


%% Get turn info

% -1 if turn leads to lower odour, +1 if higher
turnLowHigh = sign(turnEndData.perception - turnStartData.perception);

% Changes in bearing 
bearingDiff = turnEndData.bearing - turnStartData.bearing;
bearingDiff(bearingDiff > pi) = 2*pi - bearingDiff(bearingDiff > pi);
bearingDiff(bearingDiff < -pi) = 2*pi + bearingDiff(bearingDiff < -pi);

% -1 if left, 1 if right
turnLeftRight = sign(bearingDiff);

% List of head casts preceding the turn
% for i = 1:turnCount
%     
%     % Get interval we're looking for head casts over
%     endIndex = turnIndeces(i);
%     startIndex = endIndex - 5/timestep;
% 
%     preTurnHeadAngles = data.headAngle(startIndex:endIndex);
%     
%     headCasts(i) = {getCrossings(preTurnHeadAngles,0.5236)};
%     
% end


%% Bearing / turn probability

x = 11.25:22.5:168.75;
numpoints = hist(abs(rad2deg(turnStartData.bearing)),x);

simData.turnCumulativeProb = cumsum(numpoints)/sum(numpoints);



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
% 
% deltaAngles = data(turnEndIndeces,3) - data(turnStartIndeces,3);
% deltaAngles(deltaAngles > pi) = 2*pi - deltaAngles(deltaAngles > pi);
% 
% simData.deltaAngles(deltaAngles < -pi) = 2*pi + deltaAngles(deltaAngles < -pi);


%% Prob left turn

%% This needs fixed for 0<angle<pi

numIntervals = 12;
interval = (2*pi)/numIntervals;
probs = zeros(7,1);

for i = 1:numIntervals
    angle = -pi+interval*(i-1);
    
    totalCount = sum((turnStartData.bearing > angle) & (turnStartData.bearing < angle+interval));
    leftCount = sum((turnStartData.bearing > angle) & (turnStartData.bearing < angle+interval) & (bearingDiff < 0));
    
    probs(i) = leftCount/totalCount;
    
end

simData.leftTurnProb = probs;


%% Head turns








