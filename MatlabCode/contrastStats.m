function contrastStats(stats)

close all;

larvaStats = getLarvaStats();
metaStats = getStatMeta();

% Turn cumulative prob

figure;

subplot(1,2,1); title('Sim');
turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,stats.turnCumulativeProb);

subplot(1,2,2); title('Larva');
turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,larvaStats.turnCumulativeProb);

%% Left / right pre / post turn bearing

figure; title('Bearing before / after left turns');

subplot(1,2,1); title('Sim');
hold on; axis square; 
rosePlus(count2items(stats.bearingBeforeLeftTurns,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingAfterLeftTurns,-pi,pi),'r',0.7);

subplot(1,2,2); title('Larva');
hold on; axis square; 
rosePlus(count2items(larvaStats.bearingBeforeLeftTurns,-pi,pi),'r',0.3);
rosePlus(count2items(larvaStats.bearingAfterLeftTurns,-pi,pi),'r',0.7);



figure; title('Bearing before / after right turns');

subplot(1,2,1); title('Sim');
hold on; axis square; 
rosePlus(count2items(stats.bearingBeforeRightTurns,-pi,pi),'b',0.3);
rosePlus(count2items(stats.bearingAfterRightTurns,-pi,pi),'b',0.7);

subplot(1,2,2); title('Larva');
hold on; axis square; 
rosePlus(count2items(larvaStats.bearingBeforeRightTurns,-pi,pi),'b',0.3);
rosePlus(count2items(larvaStats.bearingAfterRightTurns,-pi,pi),'b',0.7);


%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;

figure;

subplot(1,2,1); title('Sim');
hold on; axis([-pi,pi,0,1]);
bar(-pi+interval/2:interval:pi-interval/2,stats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');

subplot(1,2,2); title('Larva');
hold on; axis([-pi,pi,0,1]);
bar(-pi+interval/2:interval:pi-interval/2,larvaStats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');

%% Head turns

figure; 

subplot(3,2,1);  title('Sim')
h = pie(stats.oneCastRatios,metaStats.oneCastLabels);

subplot(3,2,3);
h = pie(stats.twoCastRatios,metaStats.twoCastLabels);

subplot(3,2,5);
h = pie(stats.threeCastRatios,metaStats.threeCastLabels);


subplot(3,2,2);  title('Larva')
h = pie(larvaStats.oneCastRatios,metaStats.oneCastLabels);

subplot(3,2,4);
h = pie(larvaStats.twoCastRatios,metaStats.twoCastLabels);

subplot(3,2,6);
h = pie(larvaStats.threeCastRatios,metaStats.threeCastLabels);


% Bearing before turns to low / high

figure;

subplot(1,2,1); title('Sim');
hold on; axis square;
rosePlus(count2items(stats.bearingBeforeTurnsToLow,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingBeforeTurnsToHigh,-pi,pi),'r',0.7);

subplot(1,2,2); title('Larva');
hold on; axis square;
rosePlus(count2items(larvaStats.bearingBeforeTurnsToLow,-pi,pi),'r',0.3);
rosePlus(count2items(larvaStats.bearingBeforeTurnsToHigh,-pi,pi),'r',0.7);













