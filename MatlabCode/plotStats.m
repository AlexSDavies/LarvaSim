function plotStats(stats)


% Turn cumulative prob

subplot(5,1,1);

turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,stats.turnCumulativeProb);


%% Left / right pre / post turn bearing

subplot(5,1,2); hold on; axis square;
rosePlus(count2items(stats.bearingBeforeLeftTurns,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingAfterLeftTurns,-pi,pi),'r',0.7);

subplot(5,1,3); hold on; axis square;
rosePlus(count2items(stats.bearingBeforeRightTurns,-pi,pi),'b',0.3);
rosePlus(count2items(stats.bearingAfterRightTurns,-pi,pi),'b',0.7);




%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;


subplot(5,1,4); hold on; axis([-pi,pi,0,1]);
bar(-pi+interval/2:interval:pi-interval/2,stats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');


%% Head turns

% subplot(3,2,1); title('Larva data');
% h = pie(oneCastRatios,oneCastLabels);
% 
% subplot(3,2,3);
% h = pie(twoCastRatios,twoCastLabels);
% 
% subplot(3,2,5);
% h = pie(threeCastRatios,threeCastLabels);














