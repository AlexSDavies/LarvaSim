function plotStats(stats,directory)

%% 


barProps = struct(				...							
	'Facecolor', [0.4 0.4 0.4], ...	
	'Linewidth', 1.5			...
	);

errorProps = struct(			...							
	'Linewidth', 1.0,			...
	'Linestyle', 'none',		...
	'Color',	 [0 0 0.8]		...
	);

pieProps = struct(				...							
	'LineWidth', 1				...
	);

pieTextProps = struct(				...							
	'FontSize', 7					...
	);

%%

meta = getStatMeta();

%%

save = 1;
if nargin < 2
	save = 0;
end

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

turnCumulativeTics = 11.25:22.5:168.75;
h = bar(turnCumulativeTics,stats.turnCumulativeProb);
set(h,barProps);
if plotVars
	hold on;
	h = errorbar(turnCumulativeTics,stats.turnCumulativeProb,sqrt(variances.turnCumulativeProb/variances.n));
	set(h,errorProps);
end
hold on;
%title('Cumulative turn probability');
ylim([0 1.1]);
xlabel('Absolute bearing'); ylabel('Cumulative probability');

if (save); saveeps(directory,'TurnProb'); end;

%% Prob left turn

numIntervals = 12;
interval = (2*pi)/numIntervals;
turnProbTics = -pi+interval/2:interval:pi-interval/2;

f = figure('Toolbar','none');
%title('Probability of left turn');
hold on; axis([-pi,pi,0,1]);
h = bar(turnProbTics,stats.leftTurnProb,interval);
set(h,barProps);
plot([-pi,pi],[0.5,0.5],'--k');
if plotVars
	hold on;
	h = errorbar(turnProbTics,stats.leftTurnProb,sqrt(variances.leftTurnProb/variances.n));
	set(h,errorProps);
end
set(gca,'XTick',[-pi -pi/2  0 pi/2 pi],'XTickLabel',{'-180','-90','0','90','180'});
xlabel('Bearing'); ylabel('Probability left turn');

if (save) saveeps(directory,'LeftTurnProb'); end;

%% Left / right pre / post turn bearing


f = figure('Toolbar','none');
% title('Before/after right turns');
hold on; axis square;
rosePlus(count2items(round(stats.bearingBeforeLeftTurns*n),-pi,pi),'r',0.3);
rosePlus(count2items(round(stats.bearingAfterLeftTurns*n),-pi,pi),'r',0.7);
ylims = ylim(); text(0.9*ylims(1),-0.05*ylims(1),'0°');
set(gcf,'Position',[100 100 382 381]);

if (save) saveeps(directory,'BearingRightTurns'); end;

f = figure('Toolbar','none');
% title('Before/after left turns');
hold on; axis square;
rosePlus(count2items(round(stats.bearingBeforeRightTurns*n),-pi,pi),'r',0.3);
rosePlus(count2items(round(stats.bearingAfterRightTurns*n),-pi,pi),'r',0.7);
ylims = ylim(); text(0.9*ylims(1),-0.05*ylims(1),'0°');
set(gcf,'Position',[100 100 382 381]);

if (save) saveeps(directory,'BearingLeftTurns'); end;

%% Bearing turns to low / high

f = figure('Toolbar','none');
hold on; axis square;
% title('Before turns to high/low (light/dark)')
rosePlus(count2items(round(stats.bearingBeforeTurnsToLow*n),-pi,pi),'b',0.7);
rosePlus(count2items(round(stats.bearingBeforeTurnsToHigh*n),-pi,pi),'b',0.3);
ylims = ylim(); text(0.9*ylims(1),-0.05*ylims(1),'0°');
set(gcf,'Position',[100 100 382 381]);

if (save) saveeps(directory,'BearingHighLowTurns'); end;

%% Head turns

f = figure('Toolbar','none');
subplot(3,1,1);
h = pie(stats.oneCastRatios,metaStats.oneCastLabels);
set(h(1:2:length(h)),pieProps); set(h(2:2:length(h)),pieTextProps);
adjustPieText(h);

subplot(3,1,2);
h = pie(stats.twoCastRatios,metaStats.twoCastLabels);
set(h(1:2:length(h)),pieProps); set(h(2:2:length(h)),pieTextProps);
adjustPieText(h);

subplot(3,1,3);
h = pie(stats.threeCastRatios,metaStats.threeCastLabels);
set(h(1:2:length(h)),pieProps); set(h(2:2:length(h)),pieTextProps);
adjustPieText(h);

set(gcf,'Position',[100 100 252 420]);

if (save) saveeps(directory,'HeadCastDirs'); end;

%%
f = figure('Toolbar','none');
h = bar([stats.castNumRatio 0 0 0 0]);
set(h,barProps);
ylim([0 1]);
xlabel('Number of casts'); ylabel('Proportion of turns');
% title('Number of pre-turn casts');

if (save) saveeps(directory,'NumCasts'); end;


%%

if isfield(stats,'distanceHist')

f = figure;
distanceTics = meta.distanceTics;
h = bar(distanceTics,stats.distanceHist,1);
set(h,barProps);
if plotVars
	% hold on; errorbar(distanceTics,stats.distanceHist,sqrt(variances.distanceHist/variances.n),'r','LineStyle','none');
end
ylim([0 1]);
xlabel('Distance to odour source (mm)'); ylabel('Proportion of time');
	
if (save) saveeps(directory,'OdourSourceDist'); end;

end

%%

if isfield(stats, 'data');

f = figure;
positionHeatMap(stats.data.midPos,[-32.25,32.25],[-50.65,50.65],2);

if (save) saveeps(directory,'Heatmap'); end;

end


%%

if isfield(stats,'samplePaths')
	
	for i = 1:length(stats.samplePaths)
		
		path = stats.samplePaths{i};

		f = figure;
		plot(path(1:10:end,1),path(1:10:end,2),'-b','LineWidth',1);
		hold on;
		
		plot([-32.25 -32.25 32.25 32.25 -32.25],[-50.65 50.65 50.65 -50.65 -50.65],'-k','LineWidth',3);
		
		xlim([-32.25,32.25]);
		ylim([-50.65,50.65]);
		axis equal; axis off;
		
		odourPeak = [46.5 -45.5].*0.1;
		plot(odourPeak(2),odourPeak(1),'o','markerFaceColor','r')
		
		if (save) saveeps(directory,['SamplePath' num2str(i)]); end;
		
	end
	
end



