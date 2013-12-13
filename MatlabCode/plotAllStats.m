function plotAllStats(stats)

plotVars = 0;
if isfield(stats,'variances')
	plotVars = 1;
	variances = stats.variances;
end

% Check if data came from muiltiple runs
n = 1;
if isfield(stats,'n')
	n = stats.n;
end

% close all;
metaStats = getStatMeta();

%% Turn cumulative prob

f = figure('Toolbar','none');

subplot(3,2,1);
turnCumulativeTics = 11.25:22.5:168.75;
bar(turnCumulativeTics,stats.turnCumulativeProb);
if plotVars
	hold on; errorbar(turnCumulativeTics,stats.turnCumulativeProb,sqrt(variances.turnCumulativeProb/variances.n),'r','LineStyle','none');
end
hold on;
title('Cumulative turn probability'); xlabel('Absolute bearing'); ylabel('Cumulative probability');

%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;
turnProbTics = -pi+interval/2:interval:pi-interval/2;

subplot(3,2,2); title('Probability of left turn');
hold on; axis([-pi,pi,0,1]);
bar(turnProbTics,stats.leftTurnProb,interval);
plot([-pi,pi],[0.5,0.5],'--k');
if plotVars
	hold on; errorbar(turnProbTics,stats.leftTurnProb,sqrt(variances.leftTurnProb/variances.n),'r','LineStyle','none');
end
set(gca,'XTick',[-pi -pi/2  0 pi/2 pi],'XTickLabel',{'-180','-90','0','90','180'});
xlabel('Bearing'); ylabel('Probability left turn');

%% Left / right pre / post turn bearing


subplot(3,4,5); title('Before/after right turns');
hold on; axis square;
rosePlus(count2items(round(stats.bearingBeforeLeftTurns*n),-pi,pi),'r',0.3);
rosePlus(count2items(round(stats.bearingAfterLeftTurns*n),-pi,pi),'r',0.7);


subplot(3,4,6); title('Before/after left turns');
hold on; axis square;
rosePlus(count2items(round(stats.bearingBeforeRightTurns*n),-pi,pi),'r',0.3);
rosePlus(count2items(round(stats.bearingAfterRightTurns*n),-pi,pi),'r',0.7);

%% Bearing turns to low / high

subplot(3,4,9); hold on; axis square; title('Before turns to high/low (light/dark)')
rosePlus(count2items(round(stats.bearingBeforeTurnsToLow*n),-pi,pi),'b',0.7);
rosePlus(count2items(round(stats.bearingBeforeTurnsToHigh*n),-pi,pi),'b',0.3);



%% Head turns


subplot(3,4,7);
h = pie(stats.oneCastRatios,metaStats.oneCastLabels);
title('Head cast directions');

subplot(3,4,8);
h = pie(stats.twoCastRatios,metaStats.twoCastLabels);

subplot(3,4,11);
h = pie(stats.threeCastRatios,metaStats.threeCastLabels);



%%
figure;
bar(stats.castNumRatio);

set(gcf,'Position',[680 318 892 780]);






