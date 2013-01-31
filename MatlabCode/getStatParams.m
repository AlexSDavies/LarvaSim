function statParams = getStatParams(stats)


% Get means for circular distribution
% (i.e. bearings before / after turns)

circleTics = 0:11.25:348.75;

statParams.afterLeftMean = circularHistMean(circleTics,stats.bearingAfterLeftTurns);
statParams.beforeLeftMean = circularHistMean(circleTics,stats.bearingBeforeLeftTurns);
statParams.afterRightMean = circularHistMean(circleTics,stats.bearingAfterRightTurns);
statParams.beforeRightMean = circularHistMean(circleTics,stats.bearingBeforeRightTurns);

statParams.beforeHighMean = circularHistMean(circleTics,stats.bearingBeforeTurnsToHigh);
statParams.beforeLowMean = circularHistMean(circleTics,stats.bearingBeforeTurnsToLow);


% Fit sine curve to left turn probabilities / bearing
% 
% interval2 = 2*pi/12;
% circleTics2 = -pi+interval2/2:interval2:pi-interval2/2
% 
% statParams.leftTurnSinFit = fit([circleTics2,stats.leftTurnProb-0.5],'sin1');
% 
% 
% % Fit exponential to turn cumulative probability
% 
% turnCumulativeTics = 11.25:22.5:168.75;
% 
% fit([turnCumulativeTics,stats.turnCumulativeProb],'exp1');


