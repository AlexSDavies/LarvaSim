
files = {
'Naive 1_5'
'Naive 1_50'
'Naive 1_500'
'Naive 1_5000'
};

clear counts;

for f = 1:length(files)

	data = dlmread(['../Input Data/bearing_and_turning/' files{f} '.csv'],',',2,0);

	%%

	numPreTurnDivs = 16;

	numTurnAngleDivs = 36;
	
	preTurnDivSize = 360 / numPreTurnDivs;

	turnAngleDivSize = 360 / numTurnAngleDivs;
	
	% Split data according to bearing at turn initiation
	for i = 1:numPreTurnDivs

		startAngle(i) = -180 + (i-1) * preTurnDivSize;
		endAngle(i) = -180 + i * preTurnDivSize;
		
		a1 = startAngle(i);
		a2 = endAngle(i);
		
		turnAngles{i} = data(find(data(:,1) > a1 & data(:,1) < a2),2);
		
		numTurns(i) = length(turnAngles{i});
		
	end

	numAbsTurns = fliplr(numTurns(1:numPreTurnDivs/2)) + numTurns(numPreTurnDivs/2+1:numPreTurnDivs);

	for i = 1:numPreTurnDivs

		counts(i,:) = hist(turnAngles{i},-180+turnAngleDivSize/2:turnAngleDivSize:180-turnAngleDivSize/2);
		counts(i,:) = counts(i,:)./sum(counts(i,:));
		
	end

	plotHeadCastAngles(counts);
	
	
% 	saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\FullStatLarva\',['HeadCastAngles_' files{f}]);
 
	propAbsTurns = numAbsTurns./sum(numAbsTurns);

	
 	csvwrite(['../Input Data/bearing_and_turning/' files{f} '_ByBearing.csv'],counts);
 	csvwrite(['../Input Data/bearing_and_turning/' files{f} '_TurnBearings.csv'],propAbsTurns);


end

