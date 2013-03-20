function allData = compareStats(name, numFiles, xAxisParameter)

% If 0, doesn't do any of the stuff that requires curve fitting toolbox
global fitAvailable;

larvaStats = getLarvaStats();
larvaParams = getStatParams(larvaStats);

metaStats = getStatMeta();

% Process simulator data files
for fileNum = 1:numFiles

   disp(['Processing file ' num2str(fileNum)]);
    
   fileTail = ['_' name '_' num2str(fileNum)];
   
   dataName = ['data' fileTail];
   turnName = ['turns' fileTail];
   paramName = ['parameters' fileTail]; 
   
   parameters(fileNum) = readParameters(paramName);
   
   simStats(fileNum) = getSimStats(dataName,turnName,0.1);
   simParams(fileNum) = getStatParams(simStats(fileNum));

end

% Gets the parameter to be used as x-axis val
xVals = [parameters.(xAxisParameter)];


%% Plot figures

close all;
set(0,'DefaultFigureWindowStyle','docked');
cols = hsv(numFiles);

for i = 1:numFiles
    lineNames{i} = [xAxisParameter ' = ' num2str(xVals(i))];
end


%% Number of turns
figure;
plot(xVals,[simStats.numTurns]);
xlabel(xAxisParameter);
ylabel('Number of turns');


%% Bearing before / after turns
figure; hold on;

plot([xVals(1) xVals(end)],[larvaParams.beforeLeftMean larvaParams.beforeLeftMean],'--m');
h1 = plot(xVals,[simParams.beforeLeftMean],'.m');

plot([xVals(1) xVals(end)],[larvaParams.afterLeftMean larvaParams.afterLeftMean],'--r');
h2 = plot(xVals,[simParams.afterLeftMean],'.r');

plot([xVals(1) xVals(end)],[larvaParams.beforeRightMean larvaParams.beforeRightMean],'--g');
h3 = plot(xVals,[simParams.beforeRightMean],'.g');

plot([xVals(1) xVals(end)],[larvaParams.afterRightMean larvaParams.afterRightMean],'--b');
h4 = plot(xVals,[simParams.afterRightMean],'.b');

legend([h1 h2 h3 h4],'Before left', 'After left', 'Before right', 'After right');

xlabel(xAxisParameter);
ylabel('Mean bearing before turn');


%% Bearing before turns to high / low

figure; hold on;

plot([xVals(1) xVals(end)],[larvaParams.beforeHighMean larvaParams.beforeHighMean],'--m');
h1 = plot(xVals,[simParams.beforeHighMean],'.m');

plot([xVals(1) xVals(end)],[larvaParams.beforeLowMean larvaParams.beforeLowMean],'--r');
h2 = plot(xVals,[simParams.beforeLowMean],'.r');

legend([h1 h2],'Before high', 'Before low');

xlabel(xAxisParameter);
ylabel('Mean bearing before turn');


%% Probability left turn (curves and fit parameters)

if fitAvailable

    figure; hold on;

    plot(metaStats.turnProbBins,larvaStats.leftTurnProb-0.5,'ko');
    plot(larvaParams.leftTurnSinFit,'-k');

    handles = [];
    for i = 1:numFiles
         % h = plot(simParams(i).leftTurnSinFit);
		 % set(h,'col',cols(i,:));
         h = plot(metaStats.turnProbBins,simStats(i).leftTurnProb-0.5,'-*','col',cols(i,:));
         handles = [handles h];
     end
    legend(handles, lineNames);
    xlabel('Bearing');
    ylabel('Probability left turn');
    
    figure; hold on;

    % Horrible voodoo to get array of cFit parameters for each simulation run
    % (You'd hope you could just do [simParams.leftTurnSinFits.a1] etc.
    % see http://www.mathworks.co.uk/matlabcentral/newsreader/view_thread/273817)
    a1Vals = arrayfun(@(x) x.leftTurnSinFit.a1, simParams);
    b1Vals = arrayfun(@(x) x.leftTurnSinFit.b1, simParams);
    c1Vals = arrayfun(@(x) x.leftTurnSinFit.c1, simParams);


    plot([xVals(1) xVals(end)],[larvaParams.leftTurnSinFit.a1 larvaParams.leftTurnSinFit.a1],'--m');
    h1 = plot(xVals,a1Vals,'.m');

    plot([xVals(1) xVals(end)],[larvaParams.leftTurnSinFit.b1 larvaParams.leftTurnSinFit.b1],'--r');
    h2 = plot(xVals,b1Vals,'.r');

    plot([xVals(1) xVals(end)],[larvaParams.leftTurnSinFit.c1 larvaParams.leftTurnSinFit.c1],'--b');
    h3 = plot(xVals,c1Vals,'.b');

    legend([h1 h2 h3], {'a - height (a*sin(b*x + c))','b - stretch','c - phase shift'});

    xlabel(xAxisParameter);
    ylabel('Left turn probability sine fit values');

end


%%  Turn / bearing distribution (exponential fit)

figure; hold on;

plot(metaStats.turnCumulativeBins,larvaStats.turnCumulativeProb,'k-o'); 

handles = [];
for i = 1:numFiles
   h = plot(metaStats.turnCumulativeBins,simStats(i).turnCumulativeProb,'*','col',cols(i,:)); 
   handles = [handles h];
end

xlabel('Bearing');
ylabel('Cumulative turn probability');
legend(handles, lineNames);


if fitAvailable

    figure; hold on;

    % Voodoo (see above for explanation)
    aVals = arrayfun(@(x) x.turnProbExpFit.a, simParams);
    bVals = arrayfun(@(x) x.turnProbExpFit.b, simParams);

    plot([xVals(1) xVals(end)],[larvaParams.turnProbExpFit.a larvaParams.turnProbExpFit.a],'--m');
    h1 = plot(xVals,aVals,'.m');

    plot([xVals(1) xVals(end)],[larvaParams.turnProbExpFit.b larvaParams.turnProbExpFit.b],'--r');
    h2 = plot(xVals,bVals,'.r');

    legend([h1 h2], {'a - scale (a*exp(b*x))','b - growth'});

    xlabel(xAxisParameter);
    ylabel('Turn cumulative prob exponential fit values');

end

%% Ratio of head cast numbers

figure; hold on;

plot(larvaStats.castNumRatio,'-k','linewidth',2);

handles = [];
for i = 1:numFiles
	h = plot(simStats(i).castNumRatio,'col',cols(i,:));
	handles = [handles h];
end
legend(handles, lineNames);


xlabel('Number of head casts')
ylabel('Ratio');


%% Number of head casts to high / low

% One cast
oneCast = reshape([simStats.oneCastRatios]',2,numFiles);

figure; hold on; title('One casts')

plot([xVals(1) xVals(end)],[larvaStats.oneCastRatios(1) larvaStats.oneCastRatios(1)],'--m');
h1 = plot(xVals,oneCast(1,:),'.m');

plot([xVals(1) xVals(end)],[larvaStats.oneCastRatios(2) larvaStats.oneCastRatios(2)],'--b');
h2 = plot(xVals,oneCast(2,:),'.b');

xlabel(xAxisParameter);
ylabel('Ratio of head casts to high / low');

legend([h1 h2],metaStats.oneCastLabels);


% Two casts
twoCast = reshape([simStats.twoCastRatios]',4,numFiles);

figure; hold on; title('Two casts')

handles = [];
cols4 = hsv(4);

for i = 1:4
	plot([xVals(1) xVals(end)],[larvaStats.twoCastRatios(i) larvaStats.twoCastRatios(i)],'--','color',cols4(i,:));
	h = plot(xVals,twoCast(i,:),'.','color',cols4(i,:));
	handles = [handles h];
end

xlabel(xAxisParameter);
ylabel('Ratio of head casts to high / low');

legend(handles,metaStats.twoCastLabels);


% Three casts
threeCast = reshape([simStats.threeCastRatios]',8,numFiles);

figure; hold on; title('Three casts')

handles = [];
cols8 = hsv(8);

for i = 1:8
	plot([xVals(1) xVals(end)],[larvaStats.threeCastRatios(i) larvaStats.threeCastRatios(i)],'--','color',cols8(i,:));
	h = plot(xVals,threeCast(i,:),'.','color',cols8(i,:));
	handles = [handles h];
end

xlabel(xAxisParameter);
ylabel('Ratio of head casts to high / low');

legend(handles,metaStats.threeCastLabels);


% Collate stats and parameters to return
allData.params = simParams;
allData.stats = simStats;




