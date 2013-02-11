function plotStats(stats)

close all;

metaStats = getStatMeta();

% Turn cumulative prob

figure;
turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,stats.turnCumulativeProb);


%% Left / right pre / post turn bearing

figure;
 hold on; axis square;
rosePlus(count2items(stats.bearingBeforeLeftTurns,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingAfterLeftTurns,-pi,pi),'r',0.7);

figure;
 hold on; axis square;
rosePlus(count2items(stats.bearingBeforeRightTurns,-pi,pi),'b',0.3);
rosePlus(count2items(stats.bearingAfterRightTurns,-pi,pi),'b',0.7);




%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;


figure;
hold on; axis([-pi,pi,0,1]);
bar(-pi+interval/2:interval:pi-interval/2,stats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');


%% Head turns

subplot(3,2,1); title('Larva data');
h = pie(stats.oneCastRatios,metaStats.oneCastLabels);

subplot(3,2,3);
h = pie(stats.twoCastRatios,metaStats.twoCastLabels);

subplot(3,2,5);
h = pie(stats.threeCastRatios,metaStats.threeCastLabels);


figure; hold on; axis square;
rosePlus(count2items(stats.bearingBeforeTurnsToLow,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingBeforeTurnsToHigh,-pi,pi),'r',0.7);











