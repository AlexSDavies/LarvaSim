function displayStats(stats)

close all;

larvaStats = getLarvaStats();
metaStats = getStatMeta();

% Turn cumulative prob

figure
turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,stats.turnCumulativeProb);
xlabel('Absolute bearing'); ylabel('Cumulative turn probability');

%% Left / right pre / post turn bearing

figure;

subplot(1,2,1);
hold on;
rosePlus(count2items(stats.bearingBeforeLeftTurns,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingAfterLeftTurns,-pi,pi),'r',0.7);
ylabel({'Bearing before/after (pale/dark)','turns to right'});

subplot(1,2,2);
hold on; 
rosePlus(count2items(stats.bearingBeforeRightTurns,-pi,pi),'b',0.3);
rosePlus(count2items(stats.bearingAfterRightTurns,-pi,pi),'b',0.7);
ylabel({'Bearing before/after (pale/dark)','turns to left'});


%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;

figure;

hold on; axis([-pi,pi,0,1]);
bar(-pi+interval/2:interval:pi-interval/2,stats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');
set(gca,'XTick',[-pi -pi/2  0 pi/2 pi],'XTickLabel',{'-180','-90','0','90','180'});
xlabel('Bearing'); ylabel('Probability left turn');

%% Head turns

figure; 

subplot(3,1,1);  title('Simulation')
h = pie(fliplr(stats.oneCastRatios),fliplr(metaStats.oneCastLabels));

subplot(3,1,2);
h = pie(fliplr(stats.twoCastRatios),fliplr(metaStats.twoCastLabels));

subplot(3,1,3);
h = pie(fliplr(stats.threeCastRatios),fliplr(metaStats.threeCastLabels));

% Num head casts
figure;
for n = 1:5
	count(n) = sum(stats.numCasts == n)/sum(stats.numCasts);
end
plot(1:5,count,'--.');
set(gca,'xtick',[1:5]);
xlim([0,5]);
xlabel('Number of head casts');
ylabel('Frequency');

% Bearing before turns to low / high

figure;

hold on; axis square;
rosePlus(count2items(stats.bearingBeforeTurnsToHigh,-pi,pi),'r',0.3);
rosePlus(count2items(stats.bearingBeforeTurnsToLow,-pi,pi),'r',0.7);
ylabel({'Bearing before turns to low (dark)','and high (light)'});











