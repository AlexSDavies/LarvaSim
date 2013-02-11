function statParams = getStatParams(stats)

% If 0, doesn't do any of the stuff that requires curve fitting toolbox
global fitAvailable;

statMeta = getStatMeta();

% Get means for circular distribution
% (i.e. bearings before / after turns)

circleTics = 0:11.25:348.75;

statParams.afterLeftMean = circularHistMean(circleTics,stats.bearingAfterLeftTurns);
statParams.beforeLeftMean = circularHistMean(circleTics,stats.bearingBeforeLeftTurns);

statParams.afterRightMean = circularHistMean(circleTics,stats.bearingAfterRightTurns);
statParams.beforeRightMean = circularHistMean(circleTics,stats.bearingBeforeRightTurns);

statParams.beforeHighMean = circularHistMean(circleTics,stats.bearingBeforeTurnsToHigh);
statParams.beforeLowMean = circularHistMean(circleTics,stats.bearingBeforeTurnsToLow);


if fitAvailable

    % Fit sine curve to left turn probabilities / bearing

    statParams.leftTurnSinFit = fit(statMeta.turnProbBins',stats.leftTurnProb(:)-0.5,'sin1');


    % Fit exponential to turn cumulative probability
    statParams.turnProbExpFit = fit(statMeta.turnCumulativeBins(:),stats.turnCumulativeProb(:),'exp1');

end

