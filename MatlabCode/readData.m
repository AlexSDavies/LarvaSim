timestep = 0.1;

%% Read data

num = 1;

dataName = ['data' num2str(num)];
turnName = ['turns' num2str(num)];

f = fopen(dataName);
headings = fgetl(f);
fclose(f);

data = dlmread(dataName,' ',1,0);

time = data(:,1);


turnIndeces = dlmread(turnName,' ',0,0);
turnStartIndeces = turnIndeces(:,1);
turnEndIndeces = turnIndeces(:,2);

turnStarts = data(turnStartIndeces,:);
turnEnds = data(turnEndIndeces,:);
turnTimes = turnStarts(:,1);

turnCount = length(turnIndeces);

%% Get turn info

turnPreBearing = data(turnStartIndeces,4);
turnPostBearing = data(turnEndIndeces,4);

% -1 if turn leads to lower odour, +1 if higher
turnLowHigh = sign(data(turnEndIndeces,2) - data(turnStartIndeces,2));

% Changes in bearing 
bearingDiff = data(turnEndIndeces,6) - data(turnStartIndeces,6);
bearingDiff(bearingDiff > pi) = 2*pi - bearingDiff(bearingDiff > pi);
bearingDiff(bearingDiff < -pi) = 2*pi + bearingDiff(bearingDiff < -pi);

% -1 if left, 1 if right
turnLeftRight = sign(bearingDiff);

% List of head casts preceding the turn
for i = 1:turnCount
    
    % Get interval we're looking for head casts over
    endIndex = turnIndeces(i);
    startIndex = endIndex - 5/timestep;

    preTurnHeadAngles = data(startIndex:endIndex,4);
    
    crossings(i) = {getCrossings(preTurnHeadAngles,0.5236)};
    
end


%% Bearing / turn probability

% subplot(2,1,1)
% hist(abs(rad2deg(data(:,6))),[5:20:175])
% subplot(2,1,2)
% x = [10:20:170];
x = turnCumulativeTics;
numpoints = hist(abs(rad2deg(turnStarts(:,6))),x);
% hist(abs(rad2deg(turnStarts(:,6))),x);
c_elements = cumsum(numpoints)/sum(numpoints);

subplot(1,2,1);
bar(x,turnCumulativeProb);
title('Larva data');
subplot(1,2,2); 
bar(x,c_elements);
title('Sim data');

%% Left / right pre / post turn bearing

leftTurnIndeces = find(turnLeftRight == -1);
rightTurnIndeces = find(turnLeftRight == 1);

bearingsPre = data(turnStartIndeces,6);
bearingsPost = data(turnEndIndeces,6);


subplot(2,2,1); hold on; axis square;
rosePlus(count2items(bearingBeforeLeftTurns,2*pi),'r',0.3);
rosePlus(count2items(bearingAfterLeftTurns,2*pi),'r',0.7);
title('Larva data')

subplot(2,2,3); hold on; axis square;
rosePlus(count2items(bearingBeforeRightTurns,2*pi),'b',0.3);
rosePlus(count2items(bearingAfterRightTurns,2*pi),'b',0.7);


subplot(2,2,2); hold on; axis square;
rosePlus(bearingsPre(leftTurnIndeces),'r',0.3);
rosePlus(bearingsPost(leftTurnIndeces),'r',0.7);
title('Sim data')

subplot(2,2,4); hold on; axis square;
rosePlus(bearingsPre(rightTurnIndeces),'b',0.3);
rosePlus(bearingsPost(rightTurnIndeces),'b',0.7);



%% Scatter bearing / |delta angle|

deltaAngles = data(turnEndIndeces,3) - data(turnStartIndeces,3);
deltaAngles(deltaAngles > pi) = 2*pi - deltaAngles(deltaAngles > pi);
deltaAngles(deltaAngles < -pi) = 2*pi + deltaAngles(deltaAngles < -pi);

figure; axis square; hold on;
scatter(abs(turnStarts(:,6)),abs(deltaAngles),'.k');
plot([0 pi],[0 pi],'-k');


%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;
probs = zeros(7,1);

for i = 1:numIntervals
    angle = -pi+interval*(i-1)
    
    totalCount = sum((turnStarts(:,6) > angle) & (turnStarts(:,6) < angle+interval));
    leftCount = sum((turnStarts(:,6) > angle) & (turnStarts(:,6) < angle+interval) & (bearingDiff < 0));
    
    probs(i) = leftCount/totalCount;
    
end

subplot(1,2,1); hold on; axis([-pi,pi,0,1]); title('Larva data');
bar(-pi+interval/2:interval:pi-interval/2,leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');

subplot(1,2,2); hold on; axis([-pi,pi,0,1]); title('Sim data');
bar(-pi+interval/2:interval:pi-interval/2,probs,interval);
plot([-pi,pi],[0.5,0.5],'--k');



%% Head turns

subplot(3,2,1); title('Larva data');
h = pie(oneCastRatios,oneCastLabels);

subplot(3,2,3);
h = pie(twoCastRatios,twoCastLabels);

subplot(3,2,5);
h = pie(threeCastRatios,threeCastLabels);














